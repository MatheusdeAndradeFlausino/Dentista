package controllers;

import classes.Grupo;
import com.lowagie.text.DocumentException;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import facades.GrupoFacade;
import enums.StatusPadrao;
import filters.GrupoFilter;
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

@Named("grupoController")
@SessionScoped
public class GrupoController implements Serializable {

    private Grupo current;
    //private DataModel items = null;
    @EJB
    private facades.GrupoFacade ejbFacade;
    //private PaginationHelper pagination;
    //private int selectedItemIndex;
    private List<Grupo> grupos;
    private GrupoFilter filtro;
   
    public GrupoController() {
    }

    public Grupo getSelected() {
        if (current == null) {
            current = new Grupo();
           // selectedItemIndex = -1;
        }
        return current;
    }
    
    public void limpar(){
        filtro = new GrupoFilter();
    }
    
    public void pesquisar(){
        grupos = getFacade().findByFilter(getFiltro());
    }

    private GrupoFacade getFacade() {
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

    public void processarPDF(Object document){
        try {
            PreProcessadorPDF.preProcessPDF(document, ResourceBundle.getBundle("/Bundle").getString("ListaGrupos"));
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(MaterialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String prepareList() {
        pesquisar();
        return "List";
    }

    public String prepareView(Grupo grupo) {
        current = grupo;
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Grupo();
        //selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.setStatus(StatusPadrao.ATIVO.getStatus());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("GrupoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit(Grupo grupo) {
        current = grupo;
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("GrupoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy(Grupo grupo) {
        try {
            getFacade().remove(grupo);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("GrupoDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        pesquisar();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("GrupoDeleted"));
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

    public Grupo getGrupo(java.lang.String id) {
        return ejbFacade.find(id);
    }
    
    public StatusPadrao[] getStatus(){
        return StatusPadrao.values();
    }

    public List<Grupo> getGrupos() {
        if(grupos == null)
            grupos = new ArrayList<>();
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public GrupoFilter getFiltro() {
        if(filtro == null)
            filtro = new GrupoFilter();
        return filtro;
    }

    public void setFiltro(GrupoFilter filtro) {
        this.filtro = filtro;
    }

    @FacesConverter(forClass = Grupo.class , value="grupoControllerConverter")
    public static class GrupoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GrupoController controller = (GrupoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "grupoController");
            return controller.getGrupo(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Grupo) {
                Grupo o = (Grupo) object;
                return getStringKey(o.getNome());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Grupo.class.getName());
            }
        }

    }

}
