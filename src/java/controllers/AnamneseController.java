package controllers;

import classes.Anamnese;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import facades.AnamneseFacade;
import classes.Paciente;
import classes.Profissional;
import com.lowagie.text.DocumentException;
import enums.Afirmacao;
import enums.StatusPadrao;
import filters.AnamneseFilter;
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
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;
import utils.PreProcessadorPDF;

@Named("anamneseController")
@SessionScoped
public class AnamneseController implements Serializable {

    private Anamnese current;    
    //private DataModel items = null;
    @EJB
    private facades.AnamneseFacade ejbFacade;
    //private PaginationHelper pagination;
    //private int selectedItemIndex;
    private List<Anamnese> anamneses;
    private AnamneseFilter filtro;
    
    public AnamneseController() {
    }

    public Anamnese getSelected() {
        if (current == null) {
            current = new Anamnese();
            //selectedItemIndex = -1;
        }
        return current;
    }    
    
    public void pacienteSelecionado(SelectEvent event){
        Paciente paciente = (Paciente)event.getObject();
        this.getSelected().setIdPaciente(paciente);
    }
    
    public void pacienteSelecionadoFilter(SelectEvent event){
        Paciente paciente = (Paciente)event.getObject();
        this.getFiltro().setPaciente(paciente);
    }
    
    public void profissionalSelecionadoFilter(SelectEvent event){
        Profissional profissional = (Profissional)event.getObject();
        this.getFiltro().setProfissional(profissional);
    }
    
    public void profissionalSelecionado(SelectEvent event){
        Profissional profissional = (Profissional)event.getObject();
        this.getSelected().setIdProfissional(profissional);
    }

    public void limparPaciente(){
        getSelected().setIdPaciente(null);
    }
    
    public void limparProfissional(){
        getSelected().setIdProfissional(null);
    }
    
    private AnamneseFacade getFacade() {
        return ejbFacade;
    }
    
    public void processarPDF(Object document){
        try {
            PreProcessadorPDF.preProcessPDF(document, ResourceBundle.getBundle("/Bundle").getString("ListaAnamneses"));
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(MaterialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void limpar(){
        filtro = new AnamneseFilter();
    }
    
    public void pesquisar(){
        anamneses = getFacade().findByFilter(getFiltro());
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
        anamneses = getFacade().findByFilter(getFiltro());
        return "List";
    }

    public String prepareView(Anamnese anamnese) {
        current = anamnese;
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Anamnese();        
        //selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.setData(new Date());
            current.setStatus(StatusPadrao.ATIVO.getStatus());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AnamneseCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit(Anamnese anamnese) {
        current = anamnese;
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {            
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AnamneseUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy(Anamnese anamnese) {
        try {
            getFacade().remove(anamnese);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AnamneseDeleted"));
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AnamneseDeleted"));
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

    public Anamnese getAnamnese(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public List<Anamnese> getAnamneses() {
        if(anamneses == null)
            anamneses = new ArrayList<>();
        return anamneses;
    }

    public void setAnamneses(List<Anamnese> anamneses) {
        this.anamneses = anamneses;
    }

    public AnamneseFilter getFiltro() {
        if(filtro == null)
            filtro = new AnamneseFilter();
        return filtro;
    }

    public void setFiltro(AnamneseFilter filtro) {
        this.filtro = filtro;
    }
    
    public Afirmacao[] getAfirmacoes(){
        return Afirmacao.values();
    }
    
    public StatusPadrao[] getStatus(){
        return StatusPadrao.values();
    }
       
    @FacesConverter(forClass = Anamnese.class)
    public static class AnamneseControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AnamneseController controller = (AnamneseController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "anamneseController");
            return controller.getAnamnese(getKey(value));
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
            if (object instanceof Anamnese) {
                Anamnese o = (Anamnese) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Anamnese.class.getName());
            }
        }

    }

}
