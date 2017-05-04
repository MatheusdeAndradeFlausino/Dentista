package controllers;

import classes.FilaEspera;
import classes.Paciente;
import classes.Profissional;
import controllers.util.JsfUtil;
import enums.StatusFilaEspera;
import facades.FilaEsperaFacade;
import filters.FilaEsperaFilter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;

@Named("filaEsperaController")
@SessionScoped
public class FilaEsperaController implements Serializable {

    private FilaEspera current;
    //private DataModel items = null;
    @EJB
    private facades.FilaEsperaFacade ejbFacade;
    private List<FilaEspera> filasEspera;
    private FilaEsperaFilter filtro;
    
    //private PaginationHelper pagination;
    //private int selectedItemIndex;

    public FilaEsperaController() {
    }

    public FilaEspera getSelected() {
        if (current == null) {
            current = new FilaEspera();
            //selectedItemIndex = -1;
        }
        return current;
    }

    @PostConstruct
    public void init(){
        getFiltro().setDataChegada(new Date());
        pesquisar();
    }
    
    private FilaEsperaFacade getFacade() {
        return ejbFacade;
    }
    
    public void limpar() {
        filtro = new FilaEsperaFilter();
    }

    public void pesquisar() {
        filasEspera = getFacade().findByFilter(getFiltro());
    }
    
    public void incrementarEspera(FilaEspera filaEspera){
        filaEspera.setTempoEspera(filaEspera.getTempoEspera() + 1);
    }
    
    public void pacienteSelecionadoFilter(SelectEvent event){
        Paciente paciente = (Paciente)event.getObject();
        this.getFiltro().setPaciente(paciente);
    }
    
    public void profissionalSelecionadoFilter(SelectEvent event){
        Profissional profissional = (Profissional)event.getObject();
        this.getFiltro().setProfissional(profissional);
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

    public void atender(FilaEspera filaEspera){
        filaEspera.setStatus(StatusFilaEspera.EM_ATENDIMENTO.getStatus());
    }
    
    public String prepareList() {
        pesquisar();
        return "List";
    }
    
    public void limparPaciente(){
        getSelected().setIdPaciente(null);
    }
    
    public void limparProfissional(){
        getSelected().setIdProfissional(null);
    }
    
    public String prepareView(FilaEspera filaEspera) {
        current = filaEspera;
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new FilaEspera();
        current.setDataChegada(new Date());
        //selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.setStatus(StatusFilaEspera.EM_ESPERA.getStatus());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FilaEsperaCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit(FilaEspera filaEspera) {
        current = filaEspera;
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FilaEsperaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy(FilaEspera filaEspera) {
        try {
            getFacade().remove(filaEspera);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FilaEsperaDeleted"));
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FilaEsperaDeleted"));
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

    public FilaEspera getFilaEspera(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    
    public void pacienteSelecionado(SelectEvent event) {
        Paciente paciente = (Paciente) event.getObject();
        this.current.setIdPaciente(paciente);
    }

    public void profissionalSelecionado(SelectEvent event) {
        Profissional profissional = (Profissional) event.getObject();
        this.current.setIdProfissional(profissional);
    }

    public List<FilaEspera> getFilasEspera() {
        if(filasEspera == null)
            filasEspera = new ArrayList<>();
        return filasEspera;
    }

    public void setFilasEspera(List<FilaEspera> filasEspera) {
        this.filasEspera = filasEspera;
    }

    public FilaEsperaFilter getFiltro() {
        if(filtro == null){
            filtro = new FilaEsperaFilter();
        }
        return filtro;
    }

    public void setFiltro(FilaEsperaFilter filtro) {
        this.filtro = filtro;
    }
    
    public StatusFilaEspera[] getStatus(){
        return StatusFilaEspera.values();
    }
    
    public List<FilaEspera> getTodosEmEspera(){
        List<FilaEspera> emEsperas = new LinkedList<>();
        for (FilaEspera fila : getFilasEspera()) {
            if(fila.getStatus().equals(StatusFilaEspera.EM_ESPERA.getStatus()))
                emEsperas.add(fila);
        }
        return emEsperas;
    }
    
    public List<FilaEspera> getTodosEmAtendimento(){
        List<FilaEspera> emAtendimento = new LinkedList<>();
        for (FilaEspera fila : getFilasEspera()) {
            if(fila.getStatus().equals(StatusFilaEspera.EM_ATENDIMENTO.getStatus()))
                emAtendimento.add(fila);
        }
        return emAtendimento;
    }
    
    public List<FilaEspera> getTodosAtendido(){
        List<FilaEspera> atendido = new LinkedList<>();
        for (FilaEspera fila : getFilasEspera()) {
            if(fila.getStatus().equals(StatusFilaEspera.ATENDIDO.getStatus()))
                atendido.add(fila);
        }
        return atendido;
    }
    
    @FacesConverter(forClass = FilaEspera.class)
    public static class FilaEsperaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FilaEsperaController controller = (FilaEsperaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "filaEsperaController");
            return controller.getFilaEspera(getKey(value));
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
            if (object instanceof FilaEspera) {
                FilaEspera o = (FilaEspera) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + FilaEspera.class.getName());
            }
        }

    }

}
