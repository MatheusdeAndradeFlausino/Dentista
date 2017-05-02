/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lovControllers;

import classes.Especialidade;
import enums.StatusPadrao;
import enums.TipoComissionamento;
import facades.EspecialidadeFacade;
import filters.EspecialidadeFilter;
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
@Named(value = "searchEspecialidadeController")
@SessionScoped
public class SearchEspecialidadeController implements Serializable {

    
    private EspecialidadeFilter filtro;
    @EJB
    private facades.EspecialidadeFacade ejbFacade;
    private List<Especialidade> especialidades;
    /**
     * Creates a new instance of searchEspecialidadeController
     */
    public SearchEspecialidadeController() {
    }

    @PostConstruct
    public void init() {
        filtro = new EspecialidadeFilter();
    }
    
    public void pesquisar() {
        especialidades = getFacade().findByFilter(filtro);
    }
    
    public void selecionar(Especialidade especialidade) {
        filtro = new EspecialidadeFilter();
        especialidades = new ArrayList<>();
        RequestContext.getCurrentInstance().closeDialog(especialidade);
    }
    
    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("width", 800);
        opcoes.put("height", 600);
        opcoes.put("contentHeight", "100%");
        opcoes.put("contentWidth", "100%");
        RequestContext.getCurrentInstance().openDialog("/webapp/lovs/SearchEspecialidade", opcoes, null);
    }
    
    public EspecialidadeFilter getFiltro() {
        return filtro;
    }

    public void setFiltro(EspecialidadeFilter filtro) {
        this.filtro = filtro;
    }

    private EspecialidadeFacade getFacade() {
        return ejbFacade;
    }

    public List<Especialidade> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidade> especialidades) {
        this.especialidades = especialidades;
    }
    
    public StatusPadrao[] getStatus(){
        return StatusPadrao.values();
    }
    
    public TipoComissionamento[] getTiposComissionamento(){
        return TipoComissionamento.values();
    }
    
}
