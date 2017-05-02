package controllers;

import classes.Clinica;
import classes.Especialidade;
import classes.EspecialidadeClinica;
import com.lowagie.text.DocumentException;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import enums.AberturaFuncionario;
import facades.EspecialidadeFacade;
import enums.StatusPadrao;
import enums.TipoComissionamento;
import facades.EspecialidadeClinicaFacade;
import filters.EspecialidadeFilter;
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

@Named("especialidadeController")
@SessionScoped
public class EspecialidadeController implements Serializable {

    private Especialidade current;
    //private DataModel items = null;
    private StatusPadrao statusPadrao;
    private EspecialidadeClinica especialidadeClinica;
    private List<EspecialidadeClinica> especialidadesClinica;
    private List<Especialidade> especialidades;
    private EspecialidadeFilter filtro;
    @EJB
    private facades.EspecialidadeFacade ejbFacade;
    @EJB
    private facades.EspecialidadeClinicaFacade ejbEspecialidadeClinica;
    private boolean isBotaoEditar = false;
    //private PaginationHelper pagination;
    //private int selectedItemIndex;

    public EspecialidadeController() {
    }

    public Especialidade getSelected() {
        if (current == null) {
            current = new Especialidade();
            //selectedItemIndex = -1;
        }
        return current;
    }
    
    public EspecialidadeClinica getEspecialidadeClinica() {
        if (especialidadeClinica == null) {
            especialidadeClinica = new EspecialidadeClinica();            
        }
        return especialidadeClinica;
    }
    
    public List<EspecialidadeClinica> getEspecialidadesClinica() {
        if (especialidadesClinica == null) {
            especialidadesClinica = new ArrayList<>();            
        }
        return especialidadesClinica;
    }
    
     public void createNew() {
        if (!especialidadesClinica.contains(especialidadeClinica)) {
            especialidadesClinica.add(especialidadeClinica);
            especialidadeClinica = new EspecialidadeClinica();
        } else {
            System.out.println("DUPLICADO!");
        }
    }
     
    public void limparClinica(){
        getEspecialidadeClinica().setIdClinica(null);
    } 
     
    public void limpar(){
        filtro = new EspecialidadeFilter();
    }
    
    public String reinit(){
        especialidadeClinica = new EspecialidadeClinica();
        isBotaoEditar = false;
        return null;
    }

    private EspecialidadeFacade getFacade() {
        return ejbFacade;
    }
    
    private EspecialidadeClinicaFacade getEspecialidadeClinicaFacade() {
        return ejbEspecialidadeClinica;
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

    public String prepareView(Especialidade especialidade) {
        current = especialidade;
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Especialidade();
        //selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.setStatus(StatusPadrao.ATIVO.getStatus());
            getFacade().create(current);
            for (EspecialidadeClinica e : especialidadesClinica) {
                e.setIdEspecialidade(current);
                getEspecialidadeClinicaFacade().create(e);
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EspecialidadeCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit(Especialidade especialidade) {
        current = especialidade;
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EspecialidadeUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy(Especialidade especialidade) {
        try {
            getFacade().remove(especialidade);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EspecialidadeDeleted"));
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
    }*/

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EspecialidadeDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }
    
    public void clinicaSelecionada(SelectEvent event){
        especialidadeClinica.setIdClinica( (Clinica)event.getObject() );        
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
    public void processarPDF(Object document) {
        try {
            PreProcessadorPDF.preProcessPDF(document, ResourceBundle.getBundle("/Bundle").getString("ListaEspecialidades"));
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(MaterialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pesquisar() {
        especialidades = getFacade().findByFilter(getFiltro());
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Especialidade getEspecialidade(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    
    public TipoComissionamento[] getTiposComissionamento(){
        return TipoComissionamento.values();
    }
    
    public AberturaFuncionario[] getAberturas(){
        return AberturaFuncionario.values();
    }
    
    public StatusPadrao[] getStatus(){
        return StatusPadrao.values();
    }
    
    public StatusPadrao getStatusPadrao() {
        return statusPadrao;
    }
    
    public void setStatusPadrao(StatusPadrao statusPadrao) {
        this.statusPadrao = statusPadrao;
    }

    public List<Especialidade> getEspecialidades() {
        if(especialidades == null)
            especialidades = new ArrayList<>();
        return especialidades;
    }

    public void setEspecialidades(List<Especialidade> especialidades) {
        this.especialidades = especialidades;
    }

    public EspecialidadeFilter getFiltro() {
        if(filtro == null)
            filtro = new EspecialidadeFilter();
        return filtro;
    }

    public void setFiltro(EspecialidadeFilter filtro) {
        this.filtro = filtro;
    }

    public boolean isIsBotaoEditar() {
        return isBotaoEditar;
    }
    
    public void deletarEdit(EspecialidadeClinica especialidadeClinica){
        getEspecialidadeClinicaFacade().remove(especialidadeClinica);
    }
    
    public void selecionarEdit(EspecialidadeClinica especialidadeClinica){
        this.especialidadeClinica = especialidadeClinica;
        this.isBotaoEditar = true;
    }

    public void setIsBotaoEditar(boolean isBotaoEditar) {
        this.isBotaoEditar = isBotaoEditar;
    }
    
    @FacesConverter(forClass = Especialidade.class)
    public static class EspecialidadeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EspecialidadeController controller = (EspecialidadeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "especialidadeController");
            return controller.getEspecialidade(getKey(value));
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
            if (object instanceof Especialidade) {
                Especialidade o = (Especialidade) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Especialidade.class.getName());
            }
        }

    }

}
