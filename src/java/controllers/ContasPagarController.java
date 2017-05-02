package controllers;

import classes.CentroCusto;
import classes.Clinica;
import classes.ContasPagar;
import classes.Fornecedor;
import classes.Profissional;
import com.lowagie.text.DocumentException;
import controllers.util.JsfUtil;
import enums.StatusContasPagar;
import enums.TipoConta;
import facades.ContasPagarFacade;
import filters.ContasPagarFilter;
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
import org.primefaces.event.SelectEvent;
import utils.PreProcessadorPDF;

@Named("contasPagarController")
@SessionScoped
public class ContasPagarController implements Serializable {

    private ContasPagar current;
    //private DataModel items = null;
    @EJB
    private facades.ContasPagarFacade ejbFacade;
    //private PaginationHelper pagination;
    //private int selectedItemIndex;
    private List<ContasPagar> contasPagar;
    private ContasPagarFilter filtro;

    public ContasPagarController() {
    }

    public ContasPagar getSelected() {
        if (current == null) {
            current = new ContasPagar();
            //selectedItemIndex = -1;
        }
        return current;
    }

    private ContasPagarFacade getFacade() {
        return ejbFacade;
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

    public String prepareView(ContasPagar contasPagar) {
        current = contasPagar;
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new ContasPagar();
        current.setTipoConta(TipoConta.FORNECEDOR.getTipo());
        //selectedItemIndex = -1;
        return "Create";
    }
    
    public boolean renderizarCampoProfissional(){
        return getSelected().getTipoConta().equals(TipoConta.PROFISSIONAL.getTipo());
    }
    
    public String create() {
        try {
            current.setStatus(StatusContasPagar.A_PAGAR.getStatus());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ContasPagarCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit(ContasPagar contasPagar) {
        current = contasPagar;
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ContasPagarUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy(ContasPagar contasPagar) {
        try {
            getFacade().remove(contasPagar);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ContasPagarDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        pesquisar();
        return "List";
    }
    
    public void pesquisar(){
        contasPagar = getFacade().findByFilter(getFiltro());
    }
    
    public void limpar(){
        filtro = new ContasPagarFilter();
    }
    
    public void processarPDF(Object document){
        try {
            PreProcessadorPDF.preProcessPDF(document, ResourceBundle.getBundle("/Bundle").getString("ListaContasPagar"));
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(MaterialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public double valorLote(){
        double soma = 0;
        for (ContasPagar contasPagar1 : contasPagar) {
            if(contasPagar1.getStatus().equals(StatusContasPagar.A_PAGAR.getStatus()))
                soma += contasPagar1.getValor();
        }
        return soma;
    }
    
    public String getNomeDivida(ContasPagar contasPagar){
        if(contasPagar.getIdFornecedor() != null)
            return contasPagar.getIdFornecedor().getNome();
        if(contasPagar.getIdProfissional() != null)
            return contasPagar.getIdProfissional().getIdPessoa().getNome();
        return "";
    }
    
    public void efetuarPagamentoLote(){
        for (ContasPagar contasPagar1 : contasPagar) {
            efetuarPagamento(contasPagar1);
        }
    }
    
    public boolean ContaAPagar(ContasPagar contasPagar){
        return contasPagar.getStatus().equals(StatusContasPagar.A_PAGAR.getStatus());
    }
    
    public void efetuarPagamento(ContasPagar contasPagar){
        contasPagar.setStatus(StatusContasPagar.PAGO.getStatus());
        getFacade().edit(contasPagar);
    }
    
    public void limparFornecedor(){
        getSelected().setIdFornecedor(null);
    }
    
    public void limparClinica(){
        getSelected().setIdClinica(null);
    }
    
    public void limparCentroCusto(){
        getSelected().setIdCentroCusto(null);
    }
    
    public void limparProfissional(){
        getSelected().setIdProfissional(null);
    }
    
    public void profissionalSelecionado(SelectEvent event){
        Profissional profissional = (Profissional)event.getObject();
        this.getSelected().setIdProfissional(profissional);
        this.getSelected().setIdFornecedor(null);
    }
    
    public void centroCustoSelecionado(SelectEvent event){
        CentroCusto centroCusto = (CentroCusto)event.getObject();
        this.getSelected().setIdCentroCusto(centroCusto);
    }
    
    public void clinicaSelecionada(SelectEvent event){
        Clinica clinica = (Clinica)event.getObject();
        this.getSelected().setIdClinica(clinica);
    }
    
    public void fornecedorSelecionado(SelectEvent event){
        Fornecedor fornecedor = (Fornecedor)event.getObject();
        this.getSelected().setIdFornecedor(fornecedor);
        this.getSelected().setIdProfissional(null);
    }
    
    public void profissionalSelecionadoFilter(SelectEvent event){
        Profissional profissional = (Profissional)event.getObject();
        this.getFiltro().setProfissional(profissional);
        this.getFiltro().setFornecedor(null);
    }
    
    public void centroCustoSelecionadoFilter(SelectEvent event){
        CentroCusto centroCusto = (CentroCusto)event.getObject();
        this.getFiltro().setCentroCusto(centroCusto);
    }
    
    public void clinicaSelecionadaFilter(SelectEvent event){
        Clinica clinica = (Clinica)event.getObject();
        this.getFiltro().setClinica(clinica);
    }
    
    public void fornecedorSelecionadoFilter(SelectEvent event){
        Fornecedor fornecedor = (Fornecedor)event.getObject();
        this.getFiltro().setFornecedor(fornecedor);
        this.getFiltro().setProfissional(null);
    }
    
    public StatusContasPagar[] getStatus(){
        return StatusContasPagar.values();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ContasPagarDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
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

    public ContasPagar getContasPagar(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    
    public TipoConta[] getTiposConta(){
        return TipoConta.values();
    }

    public List<ContasPagar> getContasPagar() {
        if(contasPagar == null)
            contasPagar = new ArrayList<>();
        return contasPagar;
    }

    public void setContasPagar(List<ContasPagar> contasPagar) {
        this.contasPagar = contasPagar;
    }

    public ContasPagarFilter getFiltro() {
        if(filtro == null)
            filtro = new ContasPagarFilter();
        return filtro;
    }

    public void setFiltro(ContasPagarFilter filtro) {
        this.filtro = filtro;
    }
    
    @FacesConverter(forClass = ContasPagar.class)
    public static class ContasPagarControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ContasPagarController controller = (ContasPagarController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "contasPagarController");
            return controller.getContasPagar(getKey(value));
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
            if (object instanceof ContasPagar) {
                ContasPagar o = (ContasPagar) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ContasPagar.class.getName());
            }
        }

    }

}
