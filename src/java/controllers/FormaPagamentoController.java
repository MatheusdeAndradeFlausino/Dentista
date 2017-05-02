package controllers;

import classes.FormaPagamento;
import com.lowagie.text.DocumentException;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import facades.FormaPagamentoFacade;
import enums.StatusPadrao;
import filters.FormaPagamentoFilter;
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

@Named("formaPagamentoController")
@SessionScoped
public class FormaPagamentoController implements Serializable {

    private FormaPagamento current;
    //private DataModel items = null;
    @EJB
    private facades.FormaPagamentoFacade ejbFacade;
    //private PaginationHelper pagination;
    //private int selectedItemIndex;
    private List<FormaPagamento> formasPagamento;
    private FormaPagamentoFilter filtro;

    public FormaPagamentoController() {
    }

    public FormaPagamento getSelected() {
        if (current == null) {
            current = new FormaPagamento();
            //selectedItemIndex = -1;
        }
        return current;
    }

    private FormaPagamentoFacade getFacade() {
        return ejbFacade;
    }
    
    public void pesquisar(){
        formasPagamento = getFacade().findByFilter(getFiltro());
    }

    public void limpar(){
        filtro = new FormaPagamentoFilter();
    }
    
    public void processarPDF(Object document){
        try {
            PreProcessadorPDF.preProcessPDF(document, ResourceBundle.getBundle("/Bundle").getString("ListaFormaPagamentos"));
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(MaterialController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    }
*/
    public String prepareList() {
        pesquisar();
        return "List";
    }

    public String prepareView(FormaPagamento formaPagamento) {
        current = formaPagamento;
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new FormaPagamento();
        //selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.setStatus(StatusPadrao.ATIVO.getStatus());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FormaPagamentoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit(FormaPagamento formaPagamento) {
        current = formaPagamento;
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FormaPagamentoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy(FormaPagamento formaPagamento) {
        try {
            getFacade().remove(formaPagamento);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FormaPagamentoDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        pesquisar();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FormaPagamentoDeleted"));
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

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public FormaPagamento getFormaPagamento(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    
    public StatusPadrao[] getStatus(){
        return StatusPadrao.values();
    }

    public enums.FormaPagamento[] getTipoFormasPagamento(){
        return enums.FormaPagamento.values();
    }

    public List<FormaPagamento> getFormasPagamento() {
        if(formasPagamento == null)
            formasPagamento = new ArrayList<>();
        return formasPagamento;
    }

    public void setFormasPagamento(List<FormaPagamento> formasPagamento) {
        this.formasPagamento = formasPagamento;
    }

    public FormaPagamentoFilter getFiltro() {
        if(filtro == null)
            filtro = new FormaPagamentoFilter();
        return filtro;
    }

    public void setFiltro(FormaPagamentoFilter filtro) {
        this.filtro = filtro;
    }
    
    @FacesConverter(forClass = FormaPagamento.class)
    public static class FormaPagamentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FormaPagamentoController controller = (FormaPagamentoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "formaPagamentoController");
            return controller.getFormaPagamento(getKey(value));
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
            if (object instanceof FormaPagamento) {
                FormaPagamento o = (FormaPagamento) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + FormaPagamento.class.getName());
            }
        }

    }

}
