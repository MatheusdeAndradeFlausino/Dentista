package controllers;

import classes.Clinica;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import facades.ClinicaFacade;
import classes.Contato;
import classes.Empresa;
import classes.Endereco;
import classes.Pessoa;
import com.lowagie.text.DocumentException;
import enums.Afirmacao;
import enums.FusoHorario;
import enums.Horario;
import enums.LancamentoCartaoCredito;
import enums.PagamentoProfissional;
import enums.StatusPadrao;
import filters.ClinicaFilter;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import utils.DatasEHoras;
import utils.PreProcessadorPDF;

@Named("clinicaController")
@SessionScoped
public class ClinicaController implements Serializable {

    private Clinica current;
    private Pessoa pessoa;
    private Endereco endereco;
    private Contato contato;
    private Empresa empresa;
    //private DataModel items = null;
    private StatusPadrao statusPadrao;
    private Horario horarioAbertura;
    private Horario horarioFechamento;
    private Afirmacao numFichaAutomatico;
    private FusoHorario fusoHorario;
    private Date tempoAtendimento;
    private LancamentoCartaoCredito lancamentoCartao;
    private PagamentoProfissional pagamentoProfissional;
    private String CPF = "SIM";

    @EJB
    private facades.ClinicaFacade ejbFacade;
    @EJB
    private facades.PessoaFacade pessoaFacade;
    @EJB
    private facades.ContatoFacade contatoFacade;
    @EJB
    private facades.EnderecoFacade enderecoFacade;
    @EJB
    private facades.EmpresaFacade empresaFacade;
    //private PaginationHelper pagination;
    //private int selectedItemIndex;
    private List<Clinica> clinicas;
    private ClinicaFilter filtro;
    
    public ClinicaController() {
    }

    public Clinica getSelected() {
        if (current == null) {
            current = new Clinica();
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

    public Empresa getEmpresa() {
        if (empresa == null) {
            empresa = new Empresa();
        }
        return empresa;
    }

    private ClinicaFacade getFacade() {
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

    private facades.EmpresaFacade getEmpresaFacade() {
        return empresaFacade;
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

    public void pesquisar(){
        clinicas = getFacade().findByFilter(getFiltro());
    }
    
    public void limpar(){
        filtro = new ClinicaFilter();
    }
    
    public String prepareView(Clinica clinica) {
        current = clinica;
        CPF = current.getIsCpf();
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Clinica();
        contato = new Contato();
        endereco = new Endereco();
        empresa = new Empresa();
        pessoa = new Pessoa();
        tempoAtendimento = null;
        //selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getContatoFacade().create(contato);
            getEnderecoFacade().create(endereco);
            if (!renderizarFormCNPJ()) {
                pessoa.setIdContato(contato);
                pessoa.setIdEndereco(endereco);
                getPessoaFacade().create(pessoa);
                current.setIdPessoa(pessoa);
            } else {
                empresa.setIdContato(contato);
                empresa.setIdEndereco(endereco);
                getEmpresaFacade().create(empresa);
                current.setIdEmpresa(empresa);
            }
            current.setTempoAtendimento(DatasEHoras.extrairHoraEMinutosDaData(tempoAtendimento));
            current.setNumFichaAutomatico(numFichaAutomatico.getValor());
            current.setTipoPagamentoProfissional(pagamentoProfissional.getPagamento());
            current.setTipoLancamentoCartaoCredito(lancamentoCartao.getTipo());
            current.setTimezone(fusoHorario.getFuso());
            current.setIsCpf(CPF);
            current.setHoraAbertura(horarioAbertura.getValorRelogio());
            current.setHoraFechamento(horarioFechamento.getValorRelogio());
            current.setStatus(StatusPadrao.ATIVO.getStatus());
            getFacade().create(current);

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ClinicaCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit(Clinica clinica) {
        current = clinica;
        if (current.getIsCpf().equals("SIM")) {
            pessoa = current.getIdPessoa();
            contato = pessoa.getIdContato();
            endereco = pessoa.getIdEndereco();
            empresa = new Empresa();
        } else {
            empresa = current.getIdEmpresa();
            contato = empresa.getIdContato();
            endereco = empresa.getIdEndereco();
            pessoa = new Pessoa();
        }
        fusoHorario = FusoHorario.getReferencia(current.getTimezone());
        lancamentoCartao = LancamentoCartaoCredito.getReferencia(current.getTipoLancamentoCartaoCredito());
        pagamentoProfissional = PagamentoProfissional.getReferencia(current.getTipoPagamentoProfissional());
        numFichaAutomatico = Afirmacao.getReferencia(current.getNumFichaAutomatico());
        horarioAbertura = Horario.getReferencia(current.getHoraAbertura());
        horarioFechamento = Horario.getReferencia(current.getHoraFechamento());
        statusPadrao = StatusPadrao.valueOf(current.getStatus().toUpperCase());
        tempoAtendimento = DatasEHoras.criarDateAPartirDeUmaHora(current.getTempoAtendimento());
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getContatoFacade().edit(contato);
            getEnderecoFacade().edit(endereco);
            if (!renderizarFormCNPJ()) {
                pessoa.setIdContato(contato);
                pessoa.setIdEndereco(endereco);
                getPessoaFacade().edit(pessoa);
                current.setIdPessoa(pessoa);
            } else {
                empresa.setIdContato(contato);
                empresa.setIdEndereco(endereco);
                getEmpresaFacade().edit(empresa);
                current.setIdEmpresa(empresa);
            }
            current.setNumFichaAutomatico(numFichaAutomatico.getValor());
            current.setTipoPagamentoProfissional(pagamentoProfissional.getPagamento());
            current.setTipoLancamentoCartaoCredito(lancamentoCartao.getTipo());
            current.setTimezone(fusoHorario.getFuso());
            current.setIsCpf(CPF);
            current.setTempoAtendimento(DatasEHoras.extrairHoraEMinutosDaData(tempoAtendimento));
            current.setHoraAbertura(horarioAbertura.getValorRelogio());
            current.setHoraFechamento(horarioFechamento.getValorRelogio());
            current.setStatus(statusPadrao.getStatus());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ClinicaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy(Clinica clinica) {
        try {
            getFacade().remove(clinica);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ClinicaDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        clinicas = getFacade().findByFilter(getFiltro());
        return "List";
    }
    
    public void processarPDF(Object document){
        try {
            PreProcessadorPDF.preProcessPDF(document, ResourceBundle.getBundle("/Bundle").getString("ListaClinicas"));
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(MaterialController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ClinicaDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }*/

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

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Clinica getClinica(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public String getNomeClinica(Clinica current) {
        if (current.getIsCpf().equals("SIM")) {
            return current.getIdPessoa().getNome();
        } else {
            return current.getIdEmpresa().getNomeFantasia();
        }
    }

    public String getCPF_CNPJ(Clinica current) {
        if (current.getIsCpf().equals("SIM")) {
            return current.getIdPessoa().getCpf();
        } else {
            return current.getIdEmpresa().getCnpj();
        }
    }

    public String getEstadoClinica(Clinica current) {
        if (current.getIsCpf().equals("SIM")) {
            return current.getIdPessoa().getIdEndereco().getEstado();
        } else {
            return current.getIdEmpresa().getIdEndereco().getEstado();
        }
    }

    public String getCidadeClinica(Clinica current) {
        if (current.getIsCpf().equals("SIM")) {
            return current.getIdPessoa().getIdEndereco().getCidade();
        } else {
            return current.getIdEmpresa().getIdEndereco().getCidade();
        }
    }
    
    public String getTelefoneClinica(Clinica current) {
        if (current.getIsCpf().equals("SIM")) {
            if(current.getIdPessoa().getIdContato().getCel() != null)
                return current.getIdPessoa().getIdContato().getCel();
            return current.getIdPessoa().getIdContato().getTel();
        } else {
            if(current.getIdEmpresa().getIdContato().getCel() != null)
                return current.getIdEmpresa().getIdContato().getCel();
            return current.getIdEmpresa().getIdContato().getTel();
        }
    }
    
    public StatusPadrao[] getStatus() {
        return StatusPadrao.values();
    }

    public Horario[] getHorarios() {
        return Horario.values();
    }

    public FusoHorario[] getFusoHorarios() {
        return FusoHorario.values();
    }

    public Afirmacao[] getNumFichaAutomaticos() {
        return Afirmacao.values();
    }

    public LancamentoCartaoCredito[] getLancamentosCartao() {
        return LancamentoCartaoCredito.values();
    }

    public PagamentoProfissional[] getPagamentosProfissional() {
        return PagamentoProfissional.values();
    }

    public StatusPadrao getStatusPadrao() {
        return statusPadrao;
    }

    public void setStatusPadrao(StatusPadrao statusPadrao) {
        this.statusPadrao = statusPadrao;
    }

    public Horario getHorarioAbertura() {
        return horarioAbertura;
    }

    public void setHorarioAbertura(Horario horarioAbertura) {
        this.horarioAbertura = horarioAbertura;
    }

    public Horario getHorarioFechamento() {
        return horarioFechamento;
    }

    public void setHorarioFechamento(Horario horarioFechamento) {
        this.horarioFechamento = horarioFechamento;
    }

    public Afirmacao getNumFichaAutomatico() {
        return numFichaAutomatico;
    }

    public void setNumFichaAutomatico(Afirmacao numFichaAutomatico) {
        this.numFichaAutomatico = numFichaAutomatico;
    }

    public FusoHorario getFusoHorario() {
        return fusoHorario;
    }

    public void setFusoHorario(FusoHorario fusoHorario) {
        this.fusoHorario = fusoHorario;
    }

    public LancamentoCartaoCredito getLancamentoCartao() {
        return lancamentoCartao;
    }

    public void setLancamentoCartao(LancamentoCartaoCredito lancamentoCartao) {
        this.lancamentoCartao = lancamentoCartao;
    }

    public PagamentoProfissional getPagamentoProfissional() {
        return pagamentoProfissional;
    }

    public void setPagamentoProfissional(PagamentoProfissional pagamentoProfissional) {
        this.pagamentoProfissional = pagamentoProfissional;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CNPJ) {
        this.CPF = CNPJ;
    }
    
   
    public boolean renderizarFormCNPJ() {
        return this.CPF.equals("NAO");
    }

    public List<Clinica> getClinicas() {
        if(clinicas == null)
            clinicas = new ArrayList<>();
        return clinicas;
    }

    public void setClinicas(List<Clinica> clinicas) {
        this.clinicas = clinicas;
    }

    public ClinicaFilter getFiltro() {
        if(filtro == null)
            filtro = new ClinicaFilter();
        return filtro;
    }

    public void setFiltro(ClinicaFilter filtro) {
        this.filtro = filtro;
    }

    public Date getTempoAtendimento() {
        return tempoAtendimento;
    }

    public void setTempoAtendimento(Date tempoAtendimento) {
        this.tempoAtendimento = tempoAtendimento;
    }
    
    @FacesConverter(forClass = Clinica.class,value = "clinicaControllerConveter")
    public static class ClinicaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ClinicaController controller = (ClinicaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "clinicaController");
            return controller.getClinica(getKey(value));
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
            if (object instanceof Clinica) {
                Clinica o = (Clinica) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Clinica.class.getName());
            }
        }

    }

}
