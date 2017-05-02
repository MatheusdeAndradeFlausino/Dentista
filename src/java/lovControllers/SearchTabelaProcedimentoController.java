/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lovControllers;

import classes.TabelaProcedimento;
import enums.Afirmacao;
import enums.StatusPadrao;
import facades.TabelaProcedimentoFacade;
import filters.TabelaProcedimentoFilter;
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
@Named(value = "searchTabelaProcedimentoController")
@SessionScoped
public class SearchTabelaProcedimentoController implements Serializable {

    private TabelaProcedimentoFilter filtro;
    @EJB
    private facades.TabelaProcedimentoFacade ejbFacade;
    private List<TabelaProcedimento> tabelasProcedimentos;
    
    /**
     * Creates a new instance of SearchTabelaProcedimentoController
     */
    public SearchTabelaProcedimentoController() {
    }

    @PostConstruct
    public void init() {
        filtro = new TabelaProcedimentoFilter();
    }
    
    public void pesquisar() {
        tabelasProcedimentos = getFacade().findByFilter(filtro);
    }
    
    public void selecionar(TabelaProcedimento tabelaProcedimento) {
        filtro = new TabelaProcedimentoFilter();
        tabelasProcedimentos = new ArrayList<>();
        RequestContext.getCurrentInstance().closeDialog(tabelaProcedimento);
    }
    
    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("width", 800);
        opcoes.put("height", 600);
        opcoes.put("contentHeight", "100%");
        opcoes.put("contentWidth", "100%");
        RequestContext.getCurrentInstance().openDialog("/webapp/lovs/SearchTabelaProcedimento", opcoes, null);
    }
    
    private TabelaProcedimentoFacade getFacade(){
        return ejbFacade;
    }
    
    public TabelaProcedimentoFilter getFiltro() {
        return filtro;
    }

    public void setFiltro(TabelaProcedimentoFilter filtro) {
        this.filtro = filtro;
    }

    public List<TabelaProcedimento> getTabelasProcedimentos() {
        return tabelasProcedimentos;
    }

    public void setTabelasProcedimentos(List<TabelaProcedimento> tabelasProcedimentos) {
        this.tabelasProcedimentos = tabelasProcedimentos;
    }
    
    public StatusPadrao[] getStatus(){
        return StatusPadrao.values();
    }
    
    public Afirmacao[] getAfirmacoes(){
        return Afirmacao.values();
    }
       
}
