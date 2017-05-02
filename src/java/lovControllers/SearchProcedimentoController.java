/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lovControllers;

import classes.Especialidade;
import classes.Procedimento;
import classes.TabelaProcedimento;
import enums.StatusPadrao;
import facades.ProcedimentoFacade;
import filters.ProcedimentoFilter;
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
import org.primefaces.event.SelectEvent;

/**
 *
 * @author valderlei
 */
@Named(value = "searchProcedimentoController")
@SessionScoped
public class SearchProcedimentoController implements Serializable {

    private ProcedimentoFilter filtro;
    @EJB
    private facades.ProcedimentoFacade ejbFacade;
    private List<Procedimento> procedimentos;
    
    public SearchProcedimentoController() {
    }

    public void pesquisar() {
        procedimentos = getFacade().findByFilter(filtro);
    }
    
    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("width", 800);
        opcoes.put("height", 600);
        opcoes.put("contentHeight", "100%");
        opcoes.put("contentWidth", "100%");
        RequestContext.getCurrentInstance().openDialog("/webapp/lovs/SearchProcedimento", opcoes, null);
    }
    
    @PostConstruct
    public void init() {
        filtro = new ProcedimentoFilter();
    }

    public void selecionar(Procedimento procedimento) {
        filtro = new ProcedimentoFilter();
        procedimentos = new ArrayList<>();
        RequestContext.getCurrentInstance().closeDialog(procedimento);
    }
    
    public void tabelaSelecionada(SelectEvent event){
        TabelaProcedimento tabela = (TabelaProcedimento)event.getObject();
        this.filtro.setTabelaProcedimento(tabela);
    }
    
    public void especialidadeSelecionada(SelectEvent event){
        Especialidade especialidade = (Especialidade)event.getObject();
        this.filtro.setEspecialidade(especialidade);
    }
    
    private ProcedimentoFacade getFacade() {
        return ejbFacade;
    }
    
    public ProcedimentoFilter getFiltro() {
        return filtro;
    }

    public void setFiltro(ProcedimentoFilter filtro) {
        this.filtro = filtro;
    }

    public ProcedimentoFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(ProcedimentoFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public List<Procedimento> getProcedimentos() {
        return procedimentos;
    }

    public void setProcedimentos(List<Procedimento> procedimentos) {
        this.procedimentos = procedimentos;
    }
    
    public StatusPadrao[] getStatus(){
        return StatusPadrao.values();
    }
    
}
