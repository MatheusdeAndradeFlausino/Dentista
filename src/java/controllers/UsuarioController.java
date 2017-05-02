package controllers;

import classes.Clinica;
import classes.ClinicaAcessoUsuario;
import classes.Grupo;
import classes.GrupoUsuario;
import classes.Usuario;
import com.lowagie.text.DocumentException;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import enums.Afirmacao;
import enums.Horario;
import enums.StatusPadrao;
import facades.ClinicaAcessoUsuarioFacade;
import facades.ClinicaFacade;
import facades.GrupoFacade;
import facades.GrupoUsuarioFacade;
import facades.UsuarioFacade;
import filters.UsuarioFilter;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import utils.ChaveMD5;
import utils.Email;
import utils.PreProcessadorPDF;

@Named("usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    private Usuario current;
    //private DataModel items = null;
    @EJB
    private facades.UsuarioFacade ejbFacade;
    @EJB
    private facades.ClinicaFacade ejbClinicaFacade;
    @EJB
    private facades.ClinicaAcessoUsuarioFacade ejbClinicaAcessoUsuarioFacade;
    @EJB
    private facades.GrupoUsuarioFacade ejbGrupoUsuarioFacade;
    @EJB
    private facades.GrupoFacade ejbGrupoFacade;
    //private PaginationHelper pagination;
    //private int selectedItemIndex;
    private boolean isRestricao = false;
    private DualListModel<Clinica> clinicas;
    private DualListModel<Grupo> grupos;
    private String senhaNova;
    private String senhaAtual;
    private List<Usuario> usuarios;
    private UsuarioFilter filtro;

    public UsuarioController() {
    }

    public Usuario getSelected() {
        if (current == null) {
            current = new Usuario();
            //selectedItemIndex = -1;
        }
        return current;
    }

    private UsuarioFacade getFacade() {
        return ejbFacade;
    }

    private ClinicaFacade getClinicaFacade() {
        return ejbClinicaFacade;
    }

    private GrupoFacade getGrupoFacade() {
        return ejbGrupoFacade;
    }

    private GrupoUsuarioFacade getGrupoUsuarioFacade() {
        return ejbGrupoUsuarioFacade;
    }

    private ClinicaAcessoUsuarioFacade getClinicaAcessoUsuarioFacade() {
        return ejbClinicaAcessoUsuarioFacade;
    }

    public void limpar(){
        filtro = new UsuarioFilter();
    }
    
    public void pesquisar(){
        usuarios = getFacade().findByFilter(getFiltro());
    }
    
    public void processarPDF(Object document){
        try {
            PreProcessadorPDF.preProcessPDF(document, ResourceBundle.getBundle("/Bundle").getString("ListaUsuarios"));
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(MaterialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void clinicaSelecionadaFilter(SelectEvent event){
        Clinica clinica = (Clinica)event.getObject();
        this.getFiltro().setClinica(clinica);
    }

    public void grupoSelecionadoFilter(SelectEvent event){
        Grupo grupo = (Grupo)event.getObject();
        this.getFiltro().setGrupo(grupo);
    }
    
    /*public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }*/

    public String prepareList() {
        pesquisar();
        return "List";
    }

    public String prepareView(Usuario usuario) {
        current = usuario;
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Usuario();
        current.setIsRestricaoHorario(Afirmacao.NAO.getValor());
        List<Clinica> c = getClinicaFacade().findAll();
        List<Grupo> g = getGrupoFacade().findAll();
        clinicas = new DualListModel<>(c, new ArrayList<Clinica>());
        grupos = new DualListModel<>(g, new ArrayList<Grupo>());
        //selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            List<Clinica> c = clinicas.getTarget();
            List<Grupo> g = grupos.getTarget();
            String senha = current.getSenha();
            current.setStatus(StatusPadrao.ATIVO.getStatus());
            current.setSenha(ChaveMD5.criptografar(current.getSenha()));
            getFacade().create(current);
            for (Clinica c1 : c) {
                ClinicaAcessoUsuario cau = new ClinicaAcessoUsuario();
                cau.setIdUsuario(current);
                cau.setIdClinica(c1);
                getClinicaAcessoUsuarioFacade().create(cau);
            }
            for (Grupo g1 : g) {
                GrupoUsuario gu = new GrupoUsuario();
                gu.setUsuario(current);
                gu.setGrupo(g1);
                getGrupoUsuarioFacade().create(gu);
            }
            Email email = new Email();
            email.setAssunto("Criação de usuario no sistema Dentist");
            email.setDestinatario(current.getEmail());
            email.setMensagem("Usuário criado com sucesso no sistema Dentist:\nLogin: "
                    + current.getLogin() + "\nSenha: " + senha);
            email.enviarEmail();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEditPassword(Usuario usuario) {
        current = usuario;
        senhaAtual = new String();
        senhaNova = new String();
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "EditPassword";
    }

    public String prepareEdit(Usuario usuario) {
        current = usuario;
        List<Clinica> todasClinicas = getClinicaFacade().findAll();
        List<Grupo> todosGrupos = getGrupoFacade().findAll();

        List<Grupo> gruposUsuario = getGrupoUsuarioFacade().findAllGruposDeUmUsuario(current);
        List<Clinica> clinicasAcessoUsuario = getClinicaAcessoUsuarioFacade().findAllClinicaDeUmUsuario(current);

        for (Clinica cUsuario : clinicasAcessoUsuario) {
            for (Clinica cTodas : todasClinicas) {
                if (cUsuario.equals(cTodas)) {
                    todasClinicas.remove(cTodas);
                    break;
                }
            }
        }

        for (Grupo gUsuario : gruposUsuario) {
            for (Grupo gTodos : todosGrupos) {
                if (gUsuario.equals(gTodos)) {
                    todosGrupos.remove(gTodos);
                    break;
                }
            }
        }

        clinicas = new DualListModel<>(todasClinicas, clinicasAcessoUsuario);
        grupos = new DualListModel<>(todosGrupos, gruposUsuario);
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public boolean getRestricaoHorario() {
        return getSelected().getIsRestricaoHorario().equals(Afirmacao.SIM.getValor());
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy(Usuario usuario) {
        try {
            getFacade().remove(usuario);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        return "List";
    }

   /* public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }*/

    public void transferenciaClinica(TransferEvent transferEvent) {
        List<Clinica> cs = (List<Clinica>) transferEvent.getItems();
        if (transferEvent.isRemove()) {
            for (Clinica clinica : cs) {
                ClinicaAcessoUsuario cau = getClinicaAcessoUsuarioFacade().findByClinicaAndUsuario(clinica, current);
                getClinicaAcessoUsuarioFacade().remove(cau);
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TransferenciaClinicaRemove"));
        } else {
            for (Clinica clinica : cs) {
                ClinicaAcessoUsuario cau = new ClinicaAcessoUsuario();
                cau.setIdClinica(clinica);
                cau.setIdUsuario(current);
                getClinicaAcessoUsuarioFacade().create(cau);
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TransferenciaClinicaCreate"));
        }
    }

    public void transferenciaGrupo(TransferEvent transferEvent) {
        List<Grupo> gs = (List<Grupo>) transferEvent.getItems();
        if (transferEvent.isRemove()) {
            for (Grupo grupo : gs) {
                GrupoUsuario gu = getGrupoUsuarioFacade().findByGrupoAndUsuario(current, grupo);
                getGrupoUsuarioFacade().remove(gu);
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TransferenciaGrupoRemove"));
        } else {
            for (Grupo grupo : gs) {
                GrupoUsuario gu = new GrupoUsuario();
                gu.setGrupo(grupo);
                gu.setUsuario(current);
                getGrupoUsuarioFacade().create(gu);
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TransferenciaGrupoCreate"));
        }
    }

   /* private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }*/

    public void alterarSenha() {
        String senhaCripto = ChaveMD5.criptografar(senhaAtual);
        if (senhaCripto.equals(current.getSenha())) {
            current.setSenha(ChaveMD5.criptografar(senhaNova));
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("senhaAlteradaSuccess"));
        } else {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("SenhasAtualInvalida"));
        }
    }

    public void enviarNovaSenha() {
        try {
            UUID teste = UUID.randomUUID();
            String novaPassword = teste.toString().substring(0, 6);
            System.out.println(novaPassword);
            current.setSenha(ChaveMD5.criptografar(novaPassword));
            getFacade().edit(current);
            Email email = new Email();
            email.setAssunto("Mudança de senha de usuário do sistema Dentist");
            email.setDestinatario(current.getEmail());
            email.setMensagem("Nova senha gerada para o usúario no sistema Dentist:\nLogin: "
                    + current.getLogin() + "\nSenha: " + novaPassword);
            email.enviarEmail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Usuario getUsuario(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public DualListModel<Clinica> getClinicas() {
        return clinicas;
    }

    public void setClinicas(DualListModel<Clinica> clinicas) {
        this.clinicas = clinicas;
    }

    public DualListModel<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(DualListModel<Grupo> grupos) {
        this.grupos = grupos;
    }

    /**
     * @return the isRestricao
     */
    public boolean isIsRestricao() {
        return isRestricao;
    }

    public Afirmacao[] getAfirmacoes() {
        return Afirmacao.values();
    }

    public StatusPadrao[] getStatus() {
        return StatusPadrao.values();
    }

    public Horario[] getHorarios() {
        return Horario.values();
    }

    /**
     * @param isRestricao the isRestricao to set
     */
    public void setIsRestricao(boolean isRestricao) {
        this.isRestricao = isRestricao;
    }

    public String getSenhaNova() {
        return senhaNova;
    }

    public void setSenhaNova(String senhaNova) {
        this.senhaNova = senhaNova;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public List<Usuario> getUsuarios() {
        if(usuarios == null)
            usuarios = new ArrayList<>();
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public UsuarioFilter getFiltro() {
        if(filtro == null)
            filtro = new UsuarioFilter();
        return filtro;
    }

    public void setFiltro(UsuarioFilter filtro) {
        this.filtro = filtro;
    }
    
    @FacesConverter(forClass = Usuario.class)
    public static class UsuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioController controller = (UsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioController");
            return controller.getUsuario(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Usuario) {
                Usuario o = (Usuario) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Usuario.class.getName());
            }
        }

    }

}
