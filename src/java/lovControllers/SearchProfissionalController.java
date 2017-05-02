/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lovControllers;


import facades.ProfissionalFacade;
import classes.Clinica;
import classes.Profissional;
import enums.StatusPadrao;
import filters.ProfissionalFilter;
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
@Named(value = "searchProfissionalController")
@SessionScoped
public class SearchProfissionalController implements Serializable {

    private ProfissionalFilter filtro;
    @EJB
    private facades.ProfissionalFacade ejbFacade;
    private List<Profissional> profissionais;

    /**
     * Creates a new instance of SearchProfissionalController
     */
    public SearchProfissionalController() {
    }

    @PostConstruct
    public void init() {
        filtro = new ProfissionalFilter();
    }

    public ProfissionalFilter getFiltro() {
        return filtro;
    }

    public void setFiltro(ProfissionalFilter filtro) {
        this.filtro = filtro;
    }

    private ProfissionalFacade getFacade() {
        return ejbFacade;
    }

    public void pesquisar() {
        profissionais = getFacade().findByFilter(filtro);
    }

    public void selecionar(Profissional profissional) {
        filtro = new ProfissionalFilter();
        profissionais = new ArrayList<>();
        RequestContext.getCurrentInstance().closeDialog(profissional);
    }
    
    public void clinicaSelecionada(SelectEvent event){
        Clinica clinica = (Clinica)event.getObject();
        this.filtro.setClinica(clinica);
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("width", 800);
        opcoes.put("height", 600);
        opcoes.put("contentHeight", "100%");
        opcoes.put("contentWidth", "100%");
        RequestContext.getCurrentInstance().openDialog("/webapp/lovs/SearchProfissional", opcoes, null);
    }

    public List<Profissional> getProfissionais() {
        return profissionais;
    }

    public void setProfissionais(List<Profissional> profissionais) {
        this.profissionais = profissionais;
    }
    
    public StatusPadrao[] getStatus(){
        return StatusPadrao.values();
    }
}
