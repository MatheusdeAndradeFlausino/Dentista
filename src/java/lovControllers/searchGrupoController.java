/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lovControllers;

import classes.Grupo;
import classes.TabelaProcedimento;
import enums.StatusPadrao;
import facades.GrupoFacade;
import filters.GrupoFilter;
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
@Named(value = "searchGrupoController")
@SessionScoped
public class searchGrupoController implements Serializable {
    
    private GrupoFilter filtro;
    @EJB
    private facades.GrupoFacade ejbFacade;
    private List<Grupo> grupos;
    /**
     * Creates a new instance of searchGrupoController
     */
    public searchGrupoController() {
    }
    
    @PostConstruct
    public void init() {
        filtro = new GrupoFilter();
    }
    
    public void pesquisar() {
        grupos = getFacade().findByFilter(filtro);
    }
    
    public void selecionar(Grupo grupo) {
        filtro = new GrupoFilter();
        grupos = new ArrayList<>();
        RequestContext.getCurrentInstance().closeDialog(grupo);
    }
    
    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("width", 800);
        opcoes.put("height", 600);
        opcoes.put("contentHeight", "100%");
        opcoes.put("contentWidth", "100%");
        RequestContext.getCurrentInstance().openDialog("/webapp/lovs/SearchGrupo", opcoes, null);
    }

    public void limpar(){
        filtro = new GrupoFilter();
    }
    
    public GrupoFilter getFiltro() {
        return filtro;
    }

    public void setFiltro(GrupoFilter filtro) {
        this.filtro = filtro;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }
    
    private GrupoFacade getFacade(){
        return ejbFacade;
    }  
    
    public StatusPadrao[] getStatus(){
        return StatusPadrao.values();
    }
    
}
