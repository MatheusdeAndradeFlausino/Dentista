package controllers;

import classes.Agendamento;
import classes.Especialidade;
import classes.Paciente;
import classes.Procedimento;
import classes.Profissional;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import enums.StatusAgendamento;
import enums.StatusPadrao;
import facades.AgendamentoFacade;
import filters.AgendamentoFilter;
import java.io.IOException;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import utils.DatasEHoras;

@Named("agendamentoController")
@SessionScoped
public class AgendamentoController implements Serializable {

    private Agendamento current;
    //private DataModel items = null;
    @EJB
    private facades.AgendamentoFacade ejbFacade;
    //private PaginationHelper pagination;
    //private int selectedItemIndex;
    private Date tempoAtendimento;
    private ScheduleModel eventModel;
    private ScheduleEvent evento;
    private AgendamentoFilter filtro;

    public AgendamentoController() {
    }

    public Agendamento getSelected() {
        if (current == null) {
            current = new Agendamento();
            //selectedItemIndex = -1;
        }
        return current;
    }

    private AgendamentoFacade getFacade() {
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
    public void pacienteSelecionado(SelectEvent event) {
        Paciente paciente = (Paciente) event.getObject();
        this.current.setIdPaciente(paciente);
    }

    public void profissionalSelecionado(SelectEvent event) {
        Profissional profissional = (Profissional) event.getObject();
        this.current.setIdProfissional(profissional);
    }

    public void procedimentoSelecionado(SelectEvent event) {
        Procedimento procedimento = (Procedimento) event.getObject();
        this.current.setIdProcedimento(procedimento);
    }

    public void pacienteSelecionadoFilter(SelectEvent event) {
        Paciente paciente = (Paciente) event.getObject();
        this.getFiltro().setPaciente(paciente);
    }

    public void profissionalSelecionadoFilter(SelectEvent event) {
        Profissional profissional = (Profissional) event.getObject();
        this.getFiltro().setProfissional(profissional);
    }

    public void procedimentoSelecionadoFilter(SelectEvent event) {
        Procedimento procedimento = (Procedimento) event.getObject();
        this.getFiltro().setProcedimento(procedimento);
    }
    
    public void limparPaciente(){
        getSelected().setIdPaciente(null);
    }
    
    public void limparProfissional(){
        getSelected().setIdProfissional(null);
    }
    
    public void limparProcedimento(){
        getSelected().setIdProcedimento(null);
    }

    public void limpar() {
        filtro = new AgendamentoFilter();
    }

    public void pesquisar() {
        eventModel = new DefaultScheduleModel();
        List<Agendamento> agendamentos = getFacade().findByFilter(getFiltro());
        for (Agendamento agendamento : agendamentos) {
            DefaultScheduleEvent event = new DefaultScheduleEvent();
            event.setAllDay(false);
            event.setDescription(agendamento.getObservacao());
            event.setEditable(true);
            event.setStartDate(agendamento.getData());
            event.setTitle(agendamento.getIdPaciente().getIdPessoa().getNome());
            event.setData(agendamento);
            event.setEndDate(DatasEHoras.adicionarHorasAUmDate(agendamento.getData(), agendamento.getTempoAtendimento()));
            eventModel.addEvent(event);
        }
    }

    public boolean verificarDisponibilidade() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Profissional profissional = getSelected().getIdProfissional();
        Date InicioAtendimento = getSelected().getData();
        Date fimAtendimento = DatasEHoras.adicionarHorasAUmDate(InicioAtendimento, DatasEHoras.extrairHoraEMinutosDaData(tempoAtendimento));

        List<Agendamento> agendamentos = getFacade().verificaDisponibilidade(InicioAtendimento, profissional);

        for (Agendamento agendamento : agendamentos) {
            Date inicioAgendamentoJaMarcado = agendamento.getData();
            Date fimAgendamentoJaMarcado = DatasEHoras.adicionarHorasAUmDate(agendamento.getData(), agendamento.getTempoAtendimento());

            if (!agendamento.equals(getSelected())) {
                if (DatasEHoras.isHorarioNoIntervalo(sdf.format(inicioAgendamentoJaMarcado), sdf.format(fimAgendamentoJaMarcado), sdf.format(InicioAtendimento))) {
                    return false;
                }

                if (DatasEHoras.isHorarioNoIntervalo(sdf.format(inicioAgendamentoJaMarcado), sdf.format(fimAgendamentoJaMarcado), sdf.format(fimAtendimento))) {
                    return false;
                }

                if (DatasEHoras.isHorarioNoIntervalo(sdf.format(InicioAtendimento), sdf.format(fimAtendimento), sdf.format(inicioAgendamentoJaMarcado))) {
                    return false;
                }

                if (InicioAtendimento.compareTo(inicioAgendamentoJaMarcado) == 0 || fimAtendimento.compareTo(fimAgendamentoJaMarcado) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public String prepareList() {
        pesquisar();
        //recreateModel();
        return "List";
    }

    public void editarAgendamento(ActionEvent actionEvent) {
        try {
            current = (Agendamento) evento.getData();
            tempoAtendimento = DatasEHoras.criarDateAPartirDeUmaHora(current.getTempoAtendimento());
            FacesContext.getCurrentInstance().getExternalContext().redirect("Edit.xhtml?faces-redirect=true");
        } catch (IOException ex) {
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onEventSelect(SelectEvent selectEvent) {
        evento = (ScheduleEvent) selectEvent.getObject();
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        Agendamento agendamento = (Agendamento) event.getScheduleEvent().getData();
        agendamento.setData(event.getScheduleEvent().getStartDate());
        getFacade().edit(agendamento);
    }

    public String prepareView(Agendamento agendamento) {
        current = agendamento;
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Agendamento();
        tempoAtendimento = null;
        //selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            if (verificarDisponibilidade()) {
                current.setTempoAtendimento(DatasEHoras.extrairHoraEMinutosDaData(tempoAtendimento));
                current.setStatus(StatusAgendamento.AGENDADO.getStatus());
                getFacade().create(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AgendamentoCreated"));
                return prepareCreate();
            } else {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("HorarioIndisponivel"));
                return null;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit(Agendamento agendamento) {
        current = agendamento;
        tempoAtendimento = DatasEHoras.criarDateAPartirDeUmaHora(current.getTempoAtendimento());
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            if (verificarDisponibilidade()) {
                current.setTempoAtendimento(DatasEHoras.extrairHoraEMinutosDaData(tempoAtendimento));
                getFacade().edit(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AgendamentoUpdated"));
                return "View";
            } else {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("HorarioIndisponivel"));
                return null;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy(Agendamento agendamento) {
        try {
            getFacade().remove(agendamento);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AgendamentoDeleted"));
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
     JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AgendamentoDeleted"));
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
     }
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Agendamento getAgendamento(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public Date getTempoAtendimento() {
        return tempoAtendimento;
    }

    public void setTempoAtendimento(Date tempoAtendimento) {
        this.tempoAtendimento = tempoAtendimento;
    }

    public ScheduleModel getEventModel() {
        if (eventModel == null) {
            eventModel = new DefaultScheduleModel();
        }
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public StatusAgendamento[] getStatus() {
        return StatusAgendamento.values();
    }

    public ScheduleEvent getEvento() {
        return evento;
    }

    public void setEvento(ScheduleEvent evento) {
        this.evento = evento;
    }

    public AgendamentoFilter getFiltro() {
        if (filtro == null) {
            filtro = new AgendamentoFilter();
        }
        return filtro;
    }

    public void setFiltro(AgendamentoFilter filtro) {
        this.filtro = filtro;
    }

    @FacesConverter(forClass = Agendamento.class)
    public static class AgendamentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AgendamentoController controller = (AgendamentoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "agendamentoController");
            return controller.getAgendamento(getKey(value));
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
            if (object instanceof Agendamento) {
                Agendamento o = (Agendamento) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Agendamento.class.getName());
            }
        }

    }

}
