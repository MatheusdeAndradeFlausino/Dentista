package controllers;

import classes.Clinica;
import classes.Especialidade;
import classes.Procedimento;
import classes.ProcedimentoClinica;
import classes.TabelaProcedimento;
import com.lowagie.text.DocumentException;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import enums.Afirmacao;
import enums.StatusPadrao;
import facades.ProcedimentoClinicaFacade;
import facades.ProcedimentoFacade;
import filters.ProcedimentoFilter;
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

@Named("procedimentoController")
@SessionScoped
public class ProcedimentoController implements Serializable {

    private Procedimento current;
    private ProcedimentoClinica procedimentoClinica;
    private List<ProcedimentoClinica> procedimentosClinica;
    //private DataModel items = null;
    @EJB
    private facades.ProcedimentoFacade ejbFacade;
    @EJB
    private facades.ProcedimentoClinicaFacade ejbProcedimentoClinicaFacade;
    private List<Procedimento> procedimentos;
    private ProcedimentoFilter filtro;
    //private PaginationHelper pagination;
    //private int selectedItemIndex;
    private boolean isBotaoEditar = false;

    public ProcedimentoController() {
    }

    public Procedimento getSelected() {
        if (current == null) {
            current = new Procedimento();
            //selectedItemIndex = -1;
        }
        return current;
    }

    public ProcedimentoClinica getProcedimentoClinica() {
        if (procedimentoClinica == null) {
            procedimentoClinica = new ProcedimentoClinica();
        }
        return procedimentoClinica;
    }

    public List<ProcedimentoClinica> getProcedimentosClinica() {
        if (procedimentosClinica == null) {
            procedimentosClinica = new ArrayList<>();
        }
        return procedimentosClinica;
    }

    private ProcedimentoFacade getFacade() {
        return ejbFacade;
    }

    private ProcedimentoClinicaFacade getProcedimentoClinicaFacade() {
        return ejbProcedimentoClinicaFacade;
    }
    
    public void limpar(){
        filtro = new ProcedimentoFilter();
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
    public void createNew() {
        if (!procedimentosClinica.contains(procedimentoClinica)) {
            procedimentosClinica.add(procedimentoClinica);
            procedimentoClinica = new ProcedimentoClinica();
        } else {
            System.out.println("DUPLICADO!");
        }
    }

    public String prepareList() {
        procedimentos = getFacade().findByFilter(getFiltro());
        return "List";
    }

    public String prepareView(Procedimento procedimento) {
        current = procedimento;
        procedimentosClinica = getProcedimentoClinicaFacade().findByProcedimento(current);
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }
    
    public void limparTabela(){
        getSelected().setIdTabela(null);
    }
    
    public void limparEspecialidade(){
        getSelected().setIdEspecialidade(null);
    }
    
    public void limparClinica(){
        getProcedimentoClinica().setIdClinica(null);
    }

    public String prepareCreate() {
        current = new Procedimento();
        reinit();
        procedimentosClinica = new ArrayList<>();
        //selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.setStatus(StatusPadrao.ATIVO.getStatus());
            getFacade().create(current);
            for (ProcedimentoClinica p : procedimentosClinica) {
                p.setIdProcedimento(current);
                getProcedimentoClinicaFacade().create(p);
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProcedimentoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit(Procedimento procedimento) {
        current = procedimento;
        procedimentosClinica = ejbProcedimentoClinicaFacade.findByProcedimento(current);
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            for (ProcedimentoClinica procedimentosClinica1 : procedimentosClinica) {
                procedimentosClinica1.setIdProcedimento(current);
                getProcedimentoClinicaFacade().edit(procedimentosClinica1);
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProcedimentoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public void processarPDF(Object document){
        try {
            PreProcessadorPDF.preProcessPDF(document, ResourceBundle.getBundle("/Bundle").getString("ListaProcedimentos"));
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(MaterialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void pesquisar(){
        procedimentos = getFacade().findByFilter(getFiltro());
    }

    public String destroy(Procedimento procedimento) {
        try {
            getFacade().remove(procedimento);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProcedimentoDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
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
     JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProcedimentoDeleted"));
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
     */
    public void deletarEdit(ProcedimentoClinica procedimentoClinica) {
        getProcedimentoClinicaFacade().remove(procedimentoClinica);
    }

    public void selecionarEdit(ProcedimentoClinica procedimentoClinica) {
        this.procedimentoClinica = procedimentoClinica;
        this.isBotaoEditar = true;
    }

    public String reinit() {
        procedimentoClinica = new ProcedimentoClinica();
        isBotaoEditar = false;
        return null;
    }

    public String reinitCriar() {
        ejbProcedimentoClinicaFacade.create(procedimentoClinica);
        procedimentoClinica = new ProcedimentoClinica();
        return null;
    }

    /*public DataModel getItems() {
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

    public Procedimento getProcedimento(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public void tabelaSelecionada(SelectEvent event) {
        TabelaProcedimento tabelaProcedimento = (TabelaProcedimento) event.getObject();
        this.getSelected().setIdTabela(tabelaProcedimento);
    }
    
    public void tabelaSelecionadaFilter(SelectEvent event) {
        TabelaProcedimento tabelaProcedimento = (TabelaProcedimento) event.getObject();
        this.getFiltro().setTabelaProcedimento(tabelaProcedimento);
    }

    public void especialidadeSelecionada(SelectEvent event) {
        Especialidade especialidade = (Especialidade) event.getObject();
        this.getSelected().setIdEspecialidade(especialidade);
    }
    
    public void especialidadeSelecionadaFilter(SelectEvent event) {
        Especialidade especialidade = (Especialidade) event.getObject();
        this.getFiltro().setEspecialidade(especialidade);
    }

    public void clinicaSelecionada(SelectEvent event) {
        Clinica clinica = (Clinica) event.getObject();
        this.getProcedimentoClinica().setIdClinica(clinica);
    }

    public StatusPadrao[] getStatus() {
        return StatusPadrao.values();
    }

    public Afirmacao[] getAfirmacoes() {
        return Afirmacao.values();
    }

    public boolean isIsBotaoEditar() {
        return isBotaoEditar;
    }

    public void setIsBotaoEditar(boolean isBotaoEditar) {
        this.isBotaoEditar = isBotaoEditar;
    }

    public List<Procedimento> getProcedimentos() {
        if (procedimentos == null) {
            procedimentos = new ArrayList<>();
        }
        return procedimentos;
    }

    public void setProcedimentos(List<Procedimento> procedimentos) {
        this.procedimentos = procedimentos;
    }

    public ProcedimentoFilter getFiltro() {
        if (filtro == null) {
            filtro = new ProcedimentoFilter();
        }
        return filtro;
    }

    public void setFiltro(ProcedimentoFilter filtro) {
        this.filtro = filtro;
    }

    @FacesConverter(forClass = Procedimento.class)
    public static class ProcedimentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProcedimentoController controller = (ProcedimentoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "procedimentoController");
            return controller.getProcedimento(getKey(value));
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
            if (object instanceof Procedimento) {
                Procedimento o = (Procedimento) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Procedimento.class.getName());
            }
        }

    }

}
