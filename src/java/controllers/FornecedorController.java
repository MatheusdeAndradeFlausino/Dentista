package controllers;

import classes.Fornecedor;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import facades.FornecedorFacade;
import classes.Contato;
import classes.Empresa;
import classes.Endereco;
import classes.Pessoa;
import com.lowagie.text.DocumentException;
import enums.Despesa;
import enums.StatusPadrao;
import filters.FornecedorFilter;
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
import utils.PreProcessadorPDF;

@Named("fornecedorController")
@SessionScoped
public class FornecedorController implements Serializable {

    private Fornecedor current;
    private Pessoa pessoa;
    private Endereco endereco;
    private Contato contato;
    private Empresa empresa;
    //private DataModel items = null;
    private StatusPadrao statusPadrao;
    private Despesa despesa;   
    @EJB
    private facades.FornecedorFacade ejbFacade;
    @EJB
    private facades.PessoaFacade pessoaFacade;
    @EJB
    private facades.ContatoFacade contatoFacade;
    @EJB
    private facades.EnderecoFacade enderecoFacade;
    @EJB
    private facades.EmpresaFacade empresaFacade;
    private List<Fornecedor> fornecedores;
    private FornecedorFilter filtro;
    //private PaginationHelper pagination;
    //private int selectedItemIndex;

    public FornecedorController() {
    }

    public Fornecedor getSelected() {
        if (current == null) {
            current = new Fornecedor();
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

    private FornecedorFacade getFacade() {
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
    
    public void processarPDF(Object document){
        try {
            PreProcessadorPDF.preProcessPDF(document, ResourceBundle.getBundle("/Bundle").getString("ListaFornecedores"));
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(MaterialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void limpar(){
        filtro = new FornecedorFilter();
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
        fornecedores = getFacade().findByFilter(getFiltro());
    }

    public String prepareView(Fornecedor fornecedor) {
        current = fornecedor;
        despesa = Despesa.getReferencia(current.getTipoDespesa());
        if(renderizarFORMPessoas()){
            contato = current.getIdPessoa().getIdContato();
            endereco = current.getIdPessoa().getIdEndereco();
        }else if(renderizarFORMEmpresas()){
            contato = current.getIdEmpresa().getIdContato();
            endereco = current.getIdEmpresa().getIdEndereco();
        }
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Fornecedor();
        contato = new Contato();
        endereco = new Endereco();
        empresa = new Empresa();
        pessoa = new Pessoa();
        despesa = Despesa.PESSOA;
        //selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            if (renderizarFORMPessoas() || renderizarFORMEmpresas()) {
                getContatoFacade().create(contato);
                getEnderecoFacade().create(endereco);
                if (renderizarFORMPessoas()) {
                    pessoa.setIdContato(contato);
                    pessoa.setIdEndereco(endereco);
                    getPessoaFacade().create(pessoa);
                    current.setIdPessoa(pessoa);
                } else if (renderizarFORMEmpresas()) {
                    empresa.setIdContato(contato);
                    empresa.setIdEndereco(endereco);
                    getEmpresaFacade().create(empresa);
                    current.setIdEmpresa(empresa);
                }
            }
            current.setTipoDespesa(despesa.getTipo());
            current.setStatus(StatusPadrao.ATIVO.getStatus());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FornecedorCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit(Fornecedor fornecedor) {
        current = fornecedor;
        despesa = Despesa.getReferencia(current.getTipoDespesa());
        if (renderizarFORMPessoas()) {
            pessoa = current.getIdPessoa();
            contato = pessoa.getIdContato();
            endereco = pessoa.getIdEndereco();
            empresa = new Empresa();
        } else if(renderizarFORMEmpresas()){
            empresa = current.getIdEmpresa();
            contato = empresa.getIdContato();
            endereco = empresa.getIdEndereco();
            pessoa = new Pessoa();
        }        
        statusPadrao = StatusPadrao.valueOf(current.getStatus().toUpperCase());
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            if (renderizarFORMPessoas() || renderizarFORMEmpresas()) {
                getContatoFacade().create(contato);
                getEnderecoFacade().create(endereco);
                if (renderizarFORMPessoas()) {
                    pessoa.setIdContato(contato);
                    pessoa.setIdEndereco(endereco);
                    getPessoaFacade().create(pessoa);
                    current.setIdPessoa(pessoa);
                } else if (renderizarFORMEmpresas()) {
                    empresa.setIdContato(contato);
                    empresa.setIdEndereco(endereco);
                    getEmpresaFacade().create(empresa);
                    current.setIdEmpresa(empresa);
                }
            }
            current.setTipoDespesa(despesa.getTipo());
            current.setStatus(statusPadrao.getStatus());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FornecedorUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy(Fornecedor fornecedor) {
        try {
            getFacade().remove(fornecedor);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FornecedorDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        fornecedores = getFacade().findByFilter(getFiltro());
        return "List";
    }

    /*public String destroyAndView() {
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FornecedorDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }
*/
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
    }*/
    
    public String getNomeFornecedor(Fornecedor fornecedor){
        Despesa d = Despesa.getReferencia(fornecedor.getTipoDespesa());
        switch(d){
            case FORNECEDOR_DE_PRODUTOS:
                return fornecedor.getIdEmpresa().getNomeFantasia();
            case PESSOA:
                return fornecedor.getIdPessoa().getNome();
            default:
                return fornecedor.getDescricao();
        }
    }

   /* public DataModel getItems() {
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

    public Fornecedor getFornecedor(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    
    public StatusPadrao[] getStatus(){
        return StatusPadrao.values();
    }

    public Despesa[] getDespesas() {
        return Despesa.values();
    }
    
    public StatusPadrao getStatusPadrao() {
        return statusPadrao;
    }
    
    public Despesa getDespesa() {
        return despesa;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }

    public void setStatusPadrao(StatusPadrao statusPadrao) {
        this.statusPadrao = statusPadrao;
    }

    public boolean renderizarFORMEmpresas() {
        return despesa == Despesa.FORNECEDOR_DE_PRODUTOS;
    }

    public boolean renderizarFORMPessoas() {
        return despesa == Despesa.PESSOA;
    }

    public List<Fornecedor> getFornecedores() {
        if(fornecedores == null)
            fornecedores = new ArrayList<>();
        return fornecedores;
    }

    public void setFornecedores(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }

    public FornecedorFilter getFiltro() {
        if(filtro == null)
            filtro = new FornecedorFilter();
        return filtro;
    }

    public void setFiltro(FornecedorFilter filtro) {
        this.filtro = filtro;
    }
    
    @FacesConverter(forClass = Fornecedor.class)
    public static class FornecedorControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FornecedorController controller = (FornecedorController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "fornecedorController");
            return controller.getFornecedor(getKey(value));
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
            if (object instanceof Fornecedor) {
                Fornecedor o = (Fornecedor) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Fornecedor.class.getName());
            }
        }

    }

}
