package controllers;

import classes.Mensalidade;
import classes.Motivo;
import classes.Paciente;
import classes.ParcelaMensalidade;
import controllers.util.JsfUtil;
import enums.Afirmacao;
import enums.StatusMensalidade;
import facades.MensalidadeFacade;
import facades.ParcelaMensalidadeFacade;
import filters.MensalidadeFilter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
import utils.DatasEHoras;

@Named("mensalidadeController")
@SessionScoped
public class MensalidadeController implements Serializable {

    private Mensalidade current;
    //private DataModel items = null;
    @EJB
    private facades.MensalidadeFacade ejbFacade;
    @EJB
    private facades.ParcelaMensalidadeFacade ejbParcelaMensalidadeFacade;
    //private PaginationHelper pagination;
    //private int selectedItemIndex;
    private MensalidadeFilter filtro;
    private List<ParcelaMensalidade> parcelasMensalidadesPagas;
    private List<ParcelaMensalidade> parcelasMensalidadesReceber;
    private List<Mensalidade> mensalidades;
    private ParcelaMensalidade parcelaMensalidade;

    public MensalidadeController() {
    }

    public Mensalidade getSelected() {
        if (current == null) {
            current = new Mensalidade();
            //selectedItemIndex = -1;
        }
        return current;
    }

    @PostConstruct
    public void init() {
        filtro = new MensalidadeFilter();
        parcelaMensalidade = new ParcelaMensalidade();
    }

    private MensalidadeFacade getFacade() {
        return ejbFacade;
    }

    private ParcelaMensalidadeFacade getParcelaMensalidadeFacade() {
        return ejbParcelaMensalidadeFacade;
    }

    public void limparPaciente() {
        getSelected().setIdPaciente(null);
    }
    
    public void limparPacienteFiltro(){
        getFiltro().setPaciente(null);
    }
    
    public void limpar(){
        setFiltro(null);
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
        filtro = new MensalidadeFilter();
        return "List";
    }

    public void pesquisar() {
        mensalidades = getFacade().findByFilter(filtro);
//        for (Mensalidade mensalidade : mensalidades) {
//            List<ParcelaMensalidade> aux = getParcelaMensalidadeFacade().findByMensalidade(mensalidade);
//            for (ParcelaMensalidade aux1 : aux) {
//                if(aux1.getPago().equals(Afirmacao.SIM.getValor()))
//                    parcelasMensalidadesPagas.add(aux1);
//                else
//                    parcelasMensalidadesReceber.add(aux1);
//            }
//        }
    }

    public String prepareView(Mensalidade mensalidade) {
        current = mensalidade;
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public void prepareCancelar(Mensalidade mensalidade) {
        this.current = mensalidade;
    }

    public String prepareCreate() {
        current = new Mensalidade();
        //selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.setQuantidadeMesesPagos(0);
            current.setStatus(StatusMensalidade.ATIVA.getStatus());
            getFacade().create(current);
            gerarParcelas();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MensalidadeCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public void gerarParcelas() {
        try {
            int qtdMesesPagos = current.getQuantidadeMesesPagos();
            for (int i = 0; i < current.getQuantidadeMeses(); i++) {
                ParcelaMensalidade pm = new ParcelaMensalidade();
                pm.setIdMensalidade(current);
                if (qtdMesesPagos > 0) {
                    pm.setPago(Afirmacao.SIM.getValor());
                    pm.setValorPago(current.getValor());
                    qtdMesesPagos--;
                } else {
                    pm.setPago(Afirmacao.NAO.getValor());
                    pm.setValorPago(0d);
                }
                pm.setDataVencimento(DatasEHoras.adicionarMesesAUmDate(current.getDataVencimento(), i));
                getParcelaMensalidadeFacade().create(pm);
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void removerTodasParcelas() {
        List<ParcelaMensalidade> msds = parcelasMensalidadesPagas;
        msds.addAll(parcelasMensalidadesReceber);
        for (ParcelaMensalidade mensalidade : msds) {
            getParcelaMensalidadeFacade().remove(mensalidade);
        }
    }

    public String prepareEdit(Mensalidade mensalidade) {
        current = mensalidade;
        List<ParcelaMensalidade> aux = getParcelaMensalidadeFacade().findByMensalidade(mensalidade);
        parcelasMensalidadesPagas = new ArrayList<>();
        parcelasMensalidadesReceber = new ArrayList<>();
        for (ParcelaMensalidade aux1 : aux) {
            if (aux1.getPago().equals(Afirmacao.SIM.getValor())) {
                parcelasMensalidadesPagas.add(aux1);
            } else {
                parcelasMensalidadesReceber.add(aux1);
            }
        }
        current.setQuantidadeMesesPagos(parcelasMensalidadesPagas.size());
//        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public void editarParcelaMensalidade(ParcelaMensalidade parcelaMensalidade) {
        this.parcelaMensalidade = parcelaMensalidade;
    }

    public void updateParcelaMensalidade() {
        try {
            if (parcelaMensalidade.getValorAReceber() == 0) {
                parcelaMensalidade.setPago(Afirmacao.SIM.getValor());
            } else if (parcelaMensalidade.getValorAReceber() < 0) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ValorPagamentoMaior"));
                prepareEdit(current);
                return;
            } else {
                parcelaMensalidade.setPago(Afirmacao.NAO.getValor());
            }

            getParcelaMensalidadeFacade().edit(parcelaMensalidade);
            prepareEdit(current);
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MensalidadeUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public String update() {
        try {
            int qtdMesesPagos = current.getQuantidadeMesesPagos();
            if (qtdMesesPagos <= current.getQuantidadeMeses()) {
                removerTodasParcelas();
                gerarParcelas();
                if (qtdMesesPagos == current.getQuantidadeMeses()) {
                    current.setStatus(StatusMensalidade.QUITADA.getStatus());
                }
                getFacade().edit(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MensalidadeUpdated"));
                return prepareList();
            } else {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ErroClientePagouMaisParcelas"));
                return null;
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy(Mensalidade mensalidade) {
        try {
            getFacade().remove(mensalidade);
            pesquisar();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MensalidadeDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        return "List";
    }

    public void pacienteSelecionado(SelectEvent event) {
        Paciente paciente = (Paciente) event.getObject();
        if (filtro == null) {
            filtro = new MensalidadeFilter();
        }
        this.filtro.setPaciente(paciente);
    }

    public void pacienteSelecionadoCreate(SelectEvent event) {
        Paciente paciente = (Paciente) event.getObject();
        this.current.setIdPaciente(paciente);
    }

    public void motivoSelecionado(SelectEvent event) {
        Motivo motivo = (Motivo) event.getObject();
        this.current.setIdMotivo(motivo);
    }

    public String cancelarMensalidade() {
        current.setStatus(StatusMensalidade.CANCELADA.getStatus());
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MensalidadeCancelada"));
            return "List";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public void efetuarPagamento(ParcelaMensalidade mensalidade){
        mensalidade.setValorPago(current.getValor());
        this.parcelaMensalidade = mensalidade;
        updateParcelaMensalidade();
    }

    public boolean isMensalidadeAtiva(Mensalidade mensalidade) {
        return mensalidade.getStatus().equals(StatusMensalidade.ATIVA.getStatus());
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
     JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MensalidadeDeleted"));
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

    public Mensalidade getMensalidade(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public MensalidadeFilter getFiltro() {
        if (filtro == null) {
            filtro = new MensalidadeFilter();
        }
        return filtro;
    }

    public void setFiltro(MensalidadeFilter filtro) {
        this.filtro = filtro;
    }

    public List<ParcelaMensalidade> getParcelasMensalidadesPagas() {
        return parcelasMensalidadesPagas;
    }

    public void setParcelasMensalidadesPagas(List<ParcelaMensalidade> parcelasMensalidadesPagas) {
        this.parcelasMensalidadesPagas = parcelasMensalidadesPagas;
    }

    public List<ParcelaMensalidade> getParcelasMensalidadesReceber() {
        return parcelasMensalidadesReceber;
    }

    public void setParcelasMensalidadesReceber(List<ParcelaMensalidade> parcelasMensalidadesReceber) {
        this.parcelasMensalidadesReceber = parcelasMensalidadesReceber;
    }

    public StatusMensalidade[] getStatus() {
        return StatusMensalidade.values();
    }

    public List<Mensalidade> getMensalidades() {
        return mensalidades;
    }

    public void setMensalidades(List<Mensalidade> mensalidades) {
        this.mensalidades = mensalidades;
    }

    public ParcelaMensalidade getParcelaMensalidade() {

        return parcelaMensalidade;
    }

    public void setParcelaMensalidade(ParcelaMensalidade parcelaMensalidade) {

        this.parcelaMensalidade = parcelaMensalidade;
    }

    @FacesConverter(forClass = Mensalidade.class)
    public static class MensalidadeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MensalidadeController controller = (MensalidadeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mensalidadeController");
            return controller.getMensalidade(getKey(value));
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
            if (object instanceof Mensalidade) {
                Mensalidade o = (Mensalidade) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Mensalidade.class.getName());
            }
        }

    }

}
