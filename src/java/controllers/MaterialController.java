package controllers;

import classes.Clinica;
import classes.Material;
import com.lowagie.text.DocumentException;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import facades.MaterialFacade;
import enums.StatusPadrao;
import filters.MaterialFilter;
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

@Named("materialController")
@SessionScoped
public class MaterialController implements Serializable {

    private Material current;
    private String desc;
    private MaterialFilter filtro;
    //private DataModel items = null;
    @EJB
    private facades.MaterialFacade ejbFacade;
   // private PaginationHelper pagination;
    //private int selectedItemIndex;
    private List<Material> materiais;

    public MaterialController() {
    }

    public Material getSelected() {
        if (current == null) {
            current = new Material();
            //selectedItemIndex = -1;
        }
        return current;
    }

    private MaterialFacade getFacade() {
        return ejbFacade;
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
                    return new ListDataModel(getFacade().findByFilterWithRange(getFiltro(), new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }*/

    public String prepareList() {
        materiais = getFacade().findByFilter(getFiltro());
        return "List";
    }

    public String prepareView(Material material) {
        current = material;
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Material();
        //selectedItemIndex = -1;
        return "Create";
    }
    
    public void limparClinica(){
        getSelected().setIdClinica(null);
    }

    public String create() {
        try {
            current.setStatus(StatusPadrao.ATIVO.getStatus());
            getFacade().create(current);
            System.out.println("POS : " + current.getDescricao());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MaterialCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit(Material material) {
        current = material;
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MaterialUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public void limparPaciente(){
        getSelected().setIdClinica(null);
    }
    
    public void clinicaSelecionada(SelectEvent event){
        Clinica clinica = (Clinica)event.getObject();
        this.getSelected().setIdClinica(clinica);
    }
    
    public void clinicaSelecionadaFiltro(SelectEvent event){
        Clinica clinica = (Clinica)event.getObject();
        this.getFiltro().setClinica(clinica);
    }

    public String destroy(Material material) {
        try {
            getFacade().remove(material);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MaterialDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        materiais = getFacade().findByFilter(getFiltro());
        return "List";
    }
    
    public void pesquisar(){
        materiais = getFacade().findByFilter(filtro);
    }
    
    public void processarPDF(Object document){
        try {
            PreProcessadorPDF.preProcessPDF(document, "Lista de Materiais");
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MaterialDeleted"));
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
            current = getFacade().findByFilterWithRange(getFiltro(), new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }*/

    public StatusPadrao[] getStatus(){
        return StatusPadrao.values();
    }
    
    /*private void recreateModel() {
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

    public Material getMaterial(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public MaterialFilter getFiltro() {
        if(filtro == null)
            filtro = new MaterialFilter();
        return filtro;
    }

    public void setFiltro(MaterialFilter filtro) {
        this.filtro = filtro;
    }
    
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Material> getMateriais() {
        if(materiais == null)
            materiais = new ArrayList<>();
        return materiais;
    }

    public void setMateriais(List<Material> materiais) {
        this.materiais = materiais;
    }
       
    @FacesConverter(forClass = Material.class)
    public static class MaterialControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MaterialController controller = (MaterialController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "materialController");
            return controller.getMaterial(getKey(value));
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
            if (object instanceof Material) {
                Material o = (Material) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Material.class.getName());
            }
        }

    }

}
