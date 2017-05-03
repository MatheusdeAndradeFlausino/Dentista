package controllers;

import classes.Material;
import classes.Movimentacao;
import com.lowagie.text.DocumentException;
import controllers.util.JsfUtil;
import enums.TipoMovimentacao;
import facades.MaterialFacade;
import facades.MovimentacaoFacade;
import filters.MovimentacaoFilter;
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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;
import utils.PreProcessadorPDF;

@Named("movimentacaoController")
@SessionScoped
public class MovimentacaoController implements Serializable {

    private Movimentacao current;
    private Movimentacao auxEdit;
    //private DataModel items = null;
    @EJB
    private facades.MovimentacaoFacade ejbFacade;
    @EJB
    private facades.MaterialFacade ejbMaterialFacade;
    //private PaginationHelper pagination;
    //private int selectedItemIndex;
    private List<Movimentacao> movimentacoes;
    private MovimentacaoFilter filtro;

    public MovimentacaoController() {
    }

    public Movimentacao getSelected() {
        if (current == null) {
            current = new Movimentacao();
            //selectedItemIndex = -1;
        }
        return current;
    }

    public void limpar() {
        filtro = new MovimentacaoFilter();
    }

    public void pesquisar() {
        movimentacoes = getFacade().findByFilter(getFiltro());
    }

    public void limparMaterial() {
        getSelected().setIdMaterial(null);
    }

    public void processarPDF(Object document) {
        try {
            PreProcessadorPDF.preProcessPDF(document, ResourceBundle.getBundle("/Bundle").getString("ListaMovimentacoes"));
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(MaterialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private MovimentacaoFacade getFacade() {
        return ejbFacade;
    }

    private MaterialFacade getMaterialFacade() {
        return ejbMaterialFacade;
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

    public String prepareView(Movimentacao movimentacao) {
        current = movimentacao;
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Movimentacao();
        //selectedItemIndex = -1;
        return "Create";
    }

    public boolean materialDisponivel() {
        return !(getSelected().getTipoMovimentacao().equals(TipoMovimentacao.SAIDA.getTipo())
                && getSelected().getIdMaterial().getSaldo() < getSelected().getQuantidade());
    }

    public double calculaValorSaldoMaterial() {
        if (current.getTipoMovimentacao().equals(TipoMovimentacao.ENTRADA.getTipo())) {
            return current.getIdMaterial().getSaldo() + current.getQuantidade();
        } else {
            return current.getIdMaterial().getSaldo() - current.getQuantidade();
        }
    }

    public String create() {
        try {
            if (materialDisponivel()) {
                current.getIdMaterial().setSaldo(calculaValorSaldoMaterial());
                current.setData(new Date());
                getFacade().create(current);
                getMaterialFacade().edit(current.getIdMaterial());
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MovimentacaoCreated"));
                return prepareCreate();
            } else {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("SaldoMaterialIndisponivel"));
                return null;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit(Movimentacao movimentacao) {
        current = movimentacao;
        try {
            auxEdit = current.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(MovimentacaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        double saldo = -1;
        try {
            if (auxEdit.getTipoMovimentacao().equals(current.getTipoMovimentacao())
                    && current.getTipoMovimentacao().equals(TipoMovimentacao.ENTRADA.getTipo())) {
                saldo = calculaValorSaldoMaterial() - auxEdit.getQuantidade();
            }
            if (auxEdit.getTipoMovimentacao().equals(current.getTipoMovimentacao())
                    && current.getTipoMovimentacao().equals(TipoMovimentacao.SAIDA.getTipo())) {
                saldo = calculaValorSaldoMaterial() + auxEdit.getQuantidade();
            }
            if (!auxEdit.getTipoMovimentacao().equals(current.getTipoMovimentacao())
                    && current.getTipoMovimentacao().equals(TipoMovimentacao.ENTRADA.getTipo())) {
                saldo = calculaValorSaldoMaterial() + auxEdit.getQuantidade();
            }
            if (!auxEdit.getTipoMovimentacao().equals(current.getTipoMovimentacao())
                    && current.getTipoMovimentacao().equals(TipoMovimentacao.SAIDA.getTipo())) {
                saldo = calculaValorSaldoMaterial() - auxEdit.getQuantidade();
            }

            if (saldo >= 0) {
                current.getIdMaterial().setSaldo(saldo);
                getFacade().edit(current);
                getMaterialFacade().edit(current.getIdMaterial());
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MovimentacaoUpdated"));
                return "View";
            } else {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("SaldoMaterialIndisponivel"));
                return null;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy(Movimentacao movimentacao) {
        try {
            double saldo = 0;
            List<Movimentacao> movimentacoes = getFacade().findAllMovimentacaoOfMaterialAfterDate(movimentacao);
            movimentacoes.add(movimentacao);
            for (Movimentacao mov : movimentacoes) {
                if (mov.getTipoMovimentacao().equals(TipoMovimentacao.ENTRADA.getTipo())) {
                    saldo -= mov.getQuantidade();
                }
                if (mov.getTipoMovimentacao().equals(TipoMovimentacao.SAIDA.getTipo())) {
                    saldo += mov.getIdMaterial().getSaldo() + mov.getQuantidade();
                }
                getFacade().remove(mov);
            }
            movimentacao.getIdMaterial().setSaldo(saldo);
            getMaterialFacade().edit(movimentacao.getIdMaterial());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MovimentacaoDeleted"));
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
     JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MovimentacaoDeleted"));
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
    public void materialSelecionado(SelectEvent event) {
        Material material = (Material) event.getObject();
        this.getSelected().setIdMaterial(material);
    }

    public void materialSelecionadoFilter(SelectEvent event) {
        Material material = (Material) event.getObject();
        this.getFiltro().setMaterial(material);
    }

    /*public DataModel getItems() {
     if (items == null) {
     items = getPagination().createPageDataModel();
     }
     return items;
     }

     private void recreateModel() {
     items = null;
     }*/
    public TipoMovimentacao[] getTiposMovimentacao() {
        return TipoMovimentacao.values();
    }

    /* private void recreatePagination() {
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

    public Movimentacao getMovimentacao(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public List<Movimentacao> getMovimentacoes() {
        if (movimentacoes == null) {
            movimentacoes = new ArrayList<>();
        }
        return movimentacoes;
    }

    public void setMovimentacoes(List<Movimentacao> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }

    public MovimentacaoFilter getFiltro() {
        if (filtro == null) {
            filtro = new MovimentacaoFilter();
        }
        return filtro;
    }

    public void setFiltro(MovimentacaoFilter filtro) {
        this.filtro = filtro;
    }

    @FacesConverter(forClass = Movimentacao.class)
    public static class MovimentacaoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MovimentacaoController controller = (MovimentacaoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "movimentacaoController");
            return controller.getMovimentacao(getKey(value));
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
            if (object instanceof Movimentacao) {
                Movimentacao o = (Movimentacao) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Movimentacao.class.getName());
            }
        }

    }

}
