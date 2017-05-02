package controllers;

import classes.Profissional;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import facades.ProfissionalFacade;
import classes.Clinica;
import classes.Contato;
import classes.Endereco;
import classes.Pessoa;
import classes.RegraComissao;
import com.lowagie.text.DocumentException;
import enums.Sexo;
import enums.StatusPadrao;
import filters.ProfissionalFilter;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import utils.PreProcessadorPDF;

@Named("profissionalController")
@SessionScoped
public class ProfissionalController implements Serializable {

    private Profissional current;
    private Pessoa pessoa;
    private Endereco endereco;
    private Clinica clinica;
    private String nomeClinica;
    private Contato contato;
   // private DataModel items = null;
    //private Sexo sexo;
    //private StatusPadrao statusPadrao;
    @EJB
    private facades.ProfissionalFacade ejbFacade;
    @EJB
    private facades.PessoaFacade pessoaFacade;
    @EJB
    private facades.ContatoFacade contatoFacade;
    @EJB
    private facades.EnderecoFacade enderecoFacade;
    private List<Profissional> profissionais;
    private ProfissionalFilter filtro;
    //private PaginationHelper pagination;
    //private int selectedItemIndex;

    public ProfissionalController() {
    }

    public Profissional getSelected() {
        if (current == null) {
            current = new Profissional();
            //selectedItemIndex = -1;
        }
        return current;
    }
    
    public Pessoa getPessoa() {
        if (pessoa == null) {
            pessoa = new Pessoa();
        }
        return pessoa;
    }

    public Endereco getEndereco() {
        if (endereco == null) {
            endereco = new Endereco();
        }
        return endereco;
    }

    public Contato getContato() {
        if (contato == null) {
            contato = new Contato();
        }
        return contato;
    }
    
    public Clinica getClinica() {
        if (clinica == null) {
            clinica = new Clinica();
        }
        return clinica;
    }

    private ProfissionalFacade getFacade() {
        return ejbFacade;
    }
    
     private facades.PessoaFacade getPessoaFacade() {
        return pessoaFacade;
    }

    private facades.EnderecoFacade getEnderecoFacade() {
        return enderecoFacade;
    }

    private facades.ContatoFacade getContatoFacade() {
        return contatoFacade;
    }

   /* public PaginationHelper getPagination() {
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

    public void limpar(){
        filtro = new ProfissionalFilter();
    }
    
    public void pesquisar(){
        profissionais = getFacade().findByFilter(getFiltro());
    }
    
    public String prepareList() {
        profissionais = getFacade().findByFilter(getFiltro());
        return "List";
    }

    public String prepareView(Profissional profissional) {
        current = profissional;
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Profissional();
        contato = new Contato();
        endereco = new Endereco();
        clinica = new Clinica();
        pessoa = new Pessoa();
        //selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getContatoFacade().create(contato);
            getEnderecoFacade().create(endereco);
            pessoa.setIdContato(contato);
            pessoa.setIdEndereco(endereco);
            getPessoaFacade().create(pessoa);
            current.setIdClinica(clinica);
            current.setIdPessoa(pessoa);
            current.setStatus(StatusPadrao.ATIVO.getStatus());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProfissionalCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

    public String prepareEdit(Profissional profissional) {
        current = profissional;
        pessoa = current.getIdPessoa();
        contato = pessoa.getIdContato();
        endereco = pessoa.getIdEndereco();
        clinica = current.getIdClinica();               
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getContatoFacade().edit(contato);
            getEnderecoFacade().edit(endereco);
            pessoa.setIdContato(contato);
            pessoa.setIdEndereco(endereco);            
            getPessoaFacade().edit(pessoa);
            current.setIdPessoa(pessoa);
            current.setIdClinica(clinica);            
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProfissionalUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy(Profissional profissional) {
        try {
            getFacade().remove(profissional);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProfissionalDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        profissionais = getFacade().findByFilter(getFiltro());
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProfissionalDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }*/

   /*private void updateCurrentItem() {
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
    
    public void processarPDF(Object document){
        try {
            PreProcessadorPDF.preProcessPDF(document, ResourceBundle.getBundle("/Bundle").getString("ListaProfissionais"));
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(MaterialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void clinicaSelecionada(SelectEvent event){
        Clinica clinica = (Clinica)event.getObject();
        this.clinica = clinica;
    }
    
    public void clinicaSelecionadaFiltro(SelectEvent event){
        Clinica clinica = (Clinica)event.getObject();
        this.getFiltro().setClinica(clinica);
    }
    
    public void regraComissaoSelecionada(SelectEvent event){
        RegraComissao regraComissao = (RegraComissao)event.getObject();
        getSelected().setIdRegraComissao(regraComissao);
    }
        
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Profissional getProfissional(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    
    public Sexo[] getSexos() {
        return Sexo.values();
    }
    
    public StatusPadrao[] getStatus() {
        return StatusPadrao.values();
    }

   /* public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }
    
    public StatusPadrao getStatusPadrao() {
        return statusPadrao;
    }

    public String getNomeClinica() {
        return nomeClinica;
    }

    public void setNomeClinica(String nomeClinica) {
        this.nomeClinica = nomeClinica;
    }
    
    public void setStatusPadrao(StatusPadrao statusPadrao) {
        this.statusPadrao = statusPadrao;
    }*/

    public List<Profissional> getProfissionais() {
        if(profissionais == null)
            profissionais = new ArrayList<>();
        return profissionais;
    }

    public void setProfissionais(List<Profissional> profissionais) {
        this.profissionais = profissionais;
    }

    public ProfissionalFilter getFiltro() {
        if(filtro == null)
            filtro = new ProfissionalFilter();
        return filtro;
    }

    public void setFiltro(ProfissionalFilter filtro) {
        this.filtro = filtro;
    }
    
    @FacesConverter(forClass = Profissional.class)
    public static class ProfissionalControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProfissionalController controller = (ProfissionalController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "profissionalController");
            return controller.getProfissional(getKey(value));
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
            if (object instanceof Profissional) {
                Profissional o = (Profissional) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Profissional.class.getName());
            }
        }

    }

}
