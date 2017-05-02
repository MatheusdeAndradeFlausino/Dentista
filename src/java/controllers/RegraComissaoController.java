package controllers;

import classes.Clinica;
import classes.Especialidade;
import classes.Procedimento;
import classes.Regra;
import classes.RegraComissao;
import classes.RegraEspecial;
import classes.TabelaProcedimento;
import com.lowagie.text.DocumentException;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import enums.Afirmacao;
import enums.StatusPadrao;
import enums.TipoComissao;
import facades.RegraComissaoFacade;
import facades.RegraEspecialFacade;
import facades.RegraFacade;
import filters.RegraComissaoFilter;
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

@Named("regraComissaoController")
@SessionScoped
public class RegraComissaoController implements Serializable {

    private RegraComissao current;
    private Regra regra;
    private RegraEspecial regraEspecial;
    //private DataModel items = null;
    @EJB
    private facades.RegraComissaoFacade ejbFacade;
    @EJB
    private facades.RegraFacade ejbRegraFacade;
    @EJB
    private facades.RegraEspecialFacade ejbRegraEspecialFacade;
    //private PaginationHelper pagination;
    //private int selectedItemIndex;
    private boolean isBotaoEditar;
    private List<Regra> regras;
    private List<RegraEspecial> regrasEspeciais;
    private boolean isBotaoEditarEspecial;
    private List<RegraComissao> regrasComissao;
    private RegraComissaoFilter filtro;

    public RegraComissaoController() {
    }

    public RegraComissao getSelected() {
        if (current == null) {
            current = new RegraComissao();
            //selectedItemIndex = -1;
        }
        return current;
    }

    private RegraComissaoFacade getFacade() {
        return ejbFacade;
    }
    
    private RegraFacade getRegraFacade() {
        return ejbRegraFacade;
    }
    
    private RegraEspecialFacade getRegraEspecialFacade() {
        return ejbRegraEspecialFacade;
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

    public String prepareView(RegraComissao regraComissao) {
        current = regraComissao;
        regras = getRegraFacade().findByRegraComissao(current);
        regrasEspeciais = getRegraEspecialFacade().findByRegraComissao(current);
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new RegraComissao();
        regras = new ArrayList<>();
        regraEspecial = new RegraEspecial();
        regra = new Regra();
        regrasEspeciais = new ArrayList<>();        
        //selectedItemIndex = -1;
        return "Create";
    }

    public void limparClinica(){
        getSelected().setIdClinica(null);
    }
    
    public void limparEspecialidade(){
        getRegra().setIdEspecialidade(null);
    }
    
    public void limparProcedimento(){
        getRegraEspecial().setIdProcedimento(null);
    }
    
    public void limpar(){
        filtro = new RegraComissaoFilter();
    }
    
    public void processarPDF(Object document){
        try {
            PreProcessadorPDF.preProcessPDF(document, ResourceBundle.getBundle("/Bundle").getString("ListaRegrasComissao"));
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(MaterialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String create() {
        try {
            current.setStatus(StatusPadrao.ATIVO.getStatus());
            getFacade().create(current);
            for (Regra r : regras) {
                r.setIdRegraComissao(current);
                getRegraFacade().create(r);
            }
            for (RegraEspecial re : regrasEspeciais) {
                re.setIdRegraComissao(current);
                getRegraEspecialFacade().create(re);
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RegraComissaoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit(RegraComissao regraComissao) {
        current = regraComissao;
        regras = getRegraFacade().findByRegraComissao(current);
        regrasEspeciais = getRegraEspecialFacade().findByRegraComissao(current);
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RegraComissaoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public void pesquisar(){
        regrasComissao = getFacade().findByFilter(getFiltro());
    }
    
    

    public String destroy(RegraComissao regraComissao) {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RegraComissaoDeleted"));
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RegraComissaoDeleted"));
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
    }*/

    public void clinicaSelecionadaFiltro(SelectEvent event){
        Clinica clinica = (Clinica)event.getObject();
        this.getFiltro().setClinica(clinica);
    }
    
    public void clinicaSelecionada(SelectEvent event){
        Clinica clinica = (Clinica)event.getObject();
        this.getSelected().setIdClinica(clinica);
    }
    
    public void procedimentoSelecionado(SelectEvent event){
        Procedimento procedimento = (Procedimento)event.getObject();
        this.getRegraEspecial().setIdProcedimento(procedimento);
    }
    
    public void especialidadeSelecionada(SelectEvent event){
        Especialidade especialidade = (Especialidade)event.getObject();
        this.getRegra().setIdEspecialidade(especialidade);
    }
    
    public void especialidadeSelecionadaRegraEspecial(SelectEvent event){
        Especialidade especialidade = (Especialidade)event.getObject();
        this.getRegraEspecial().setIdEspecialidade(especialidade);
    }
    
    public Afirmacao[] getAfirmacoes(){
        return Afirmacao.values();
    }
    
    public TipoComissao[] getComissoes(){
        return TipoComissao.values();
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
    
    public String reinit(){
        regra = new Regra();
        isBotaoEditar = false;
        return null;
    }  
    
    public String reinitCriar(){
        getRegraFacade().create(regra);
        regra = new Regra();
        isBotaoEditar = false;
        return null;
    }
    
    public String reinitEspecialCriar(){
        getRegraEspecialFacade().create(regraEspecial);
        regraEspecial = new RegraEspecial();
        isBotaoEditarEspecial = false;
        return null;
    }
    
    public String reinitEspecial(){
        regraEspecial = new RegraEspecial();
        isBotaoEditarEspecial = false;
        return null;
    }
    
    public void deletarEdit(Regra regra){
        getRegraFacade().remove(regra);
    }
    
    public void selecionarEdit(Regra regra){
        this.regra = regra;
        this.isBotaoEditar = true;
    }
    
    public void deletarEditEspecial(RegraEspecial regraEspecial){
        getRegraEspecialFacade().remove(regraEspecial);
    }
    
    public void selecionarEditEspecial(RegraEspecial regraEspecial){
        this.regraEspecial = regraEspecial;
        this.isBotaoEditar = true;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public RegraComissao getRegraComissao(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public Regra getRegra() {
        if(regra == null)
            regra = new Regra();
        return regra;
    }

    public void setRegra(Regra regra) {
        this.regra = regra;
    }

    public RegraEspecial getRegraEspecial() {
        if(regraEspecial == null)
            regraEspecial = new RegraEspecial();
        return regraEspecial;
    }

    public void setRegraEspecial(RegraEspecial regraEspecial) {
        this.regraEspecial = regraEspecial;
    }

    public boolean isIsBotaoEditar() {
        return isBotaoEditar;
    }

    public void setIsBotaoEditar(boolean isBotaoEditar) {
        this.isBotaoEditar = isBotaoEditar;
    }

    public List<Regra> getRegras() {
        if(regras == null)
            regras = new ArrayList<>();
        return regras;
    }

    public void setRegras(List<Regra> regras) {
        this.regras = regras;
    }

    public List<RegraEspecial> getRegrasEspeciais() {
        if(regrasEspeciais == null)
            regrasEspeciais = new ArrayList<>();
        return regrasEspeciais;
    }

    public void setRegrasEspeciais(List<RegraEspecial> regrasEspeciais) {
        this.regrasEspeciais = regrasEspeciais;
    }

    public boolean isIsBotaoEditarEspecial() {
        return isBotaoEditarEspecial;
    }

    public void setIsBotaoEditarEspecial(boolean isBotaoEditarEspecial) {
        this.isBotaoEditarEspecial = isBotaoEditarEspecial;
    }
                
    public StatusPadrao[] getStatus(){
        return StatusPadrao.values();
    }

    public List<RegraComissao> getRegrasComissao() {
        if(regrasComissao == null)
            regrasComissao = new ArrayList<>();
        return regrasComissao;
    }

    public void setRegrasComissao(List<RegraComissao> regraComissao) {
        this.regrasComissao = regraComissao;
    }

    public RegraComissaoFilter getFiltro() {
        if(filtro == null)
            filtro = new RegraComissaoFilter();
        return filtro;
    }

    public void setFiltro(RegraComissaoFilter filtro) {
        this.filtro = filtro;
    }
    
    @FacesConverter(forClass = RegraComissao.class)
    public static class RegraComissaoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RegraComissaoController controller = (RegraComissaoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "regraComissaoController");
            return controller.getRegraComissao(getKey(value));
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
            if (object instanceof RegraComissao) {
                RegraComissao o = (RegraComissao) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + RegraComissao.class.getName());
            }
        }

    }

}
