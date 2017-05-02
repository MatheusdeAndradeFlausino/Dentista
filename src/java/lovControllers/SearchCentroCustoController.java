/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lovControllers;

import classes.CentroCusto;
import enums.StatusPadrao;
import facades.CentroCustoFacade;
import filters.CentroCustoFilter;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.context.RequestContext;

/**
 *
 * @author valderlei
 */
@Named(value = "searchCentroCustoController")
@SessionScoped
public class SearchCentroCustoController implements Serializable {

    
    private CentroCustoFilter filtro;
    @EJB
    private facades.CentroCustoFacade ejbFacade;
    private List<CentroCusto> centrosCusto;
    /**
     * Creates a new instance of SearchCentroCustoController
     */
    public SearchCentroCustoController() {
    }
    
    @PostConstruct
    public void init() {
        filtro = new CentroCustoFilter();
    }
    
    public void pesquisar() {
        centrosCusto = getFacade().findByFilter(filtro);
    }
    
    public void selecionar(CentroCusto centroCusto) {
        filtro = new CentroCustoFilter();
        centrosCusto = new ArrayList<>();
        RequestContext.getCurrentInstance().closeDialog(centroCusto);
    }
    
    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("width", 800);
        opcoes.put("height", 600);
        opcoes.put("contentHeight", "100%");
        opcoes.put("contentWidth", "100%");
        RequestContext.getCurrentInstance().openDialog("/webapp/lovs/SearchCentroCusto", opcoes, null);
    }

    public CentroCustoFilter getFiltro() {
        return filtro;
    }

    public void setFiltro(CentroCustoFilter filtro) {
        this.filtro = filtro;
    }

    public List<CentroCusto> getCentrosCusto() {
        return centrosCusto;
    }

    public void setCentrosCusto(List<CentroCusto> centrosCusto) {
        this.centrosCusto = centrosCusto;
    }
    
    private CentroCustoFacade getFacade(){
        return ejbFacade;
    }
    
    public StatusPadrao[] getStatus(){
        return StatusPadrao.values();
    }
}
