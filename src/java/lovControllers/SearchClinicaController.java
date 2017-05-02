/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lovControllers;

import facades.ClinicaFacade;
import classes.Clinica;
import enums.StatusPadrao;
import filters.ClinicaFilter;
import filters.EspecialidadeFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

/**
 *
 * @author valderlei
 */
@Named(value = "searchClinicaController")
@SessionScoped
public class SearchClinicaController implements Serializable {

    
    private List<Clinica> clinicas;
    private ClinicaFilter filtro;
    @EJB
    private facades.ClinicaFacade ejbFacade;

    /**
     * Creates a new instance of SearchClinicaController
     */
    public SearchClinicaController() {
    }

    @PostConstruct
    public void init() {
        filtro = new ClinicaFilter();
    }
    
    public void pesquisar() {
        clinicas = getFacade().findByFilter(getFiltro());
    }

    public void selecionar(Clinica clinica) {
        clinicas = new ArrayList<>();
        filtro = new ClinicaFilter();
        RequestContext.getCurrentInstance().closeDialog(clinica);
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("width", 800);
        opcoes.put("height", 600);
        opcoes.put("contentHeight", "100%");
        opcoes.put("contentWidth", "100%");
        RequestContext.getCurrentInstance().openDialog("/webapp/lovs/SearchClinica", opcoes, null);
    }


    public List<Clinica> getClinicas() {
        return clinicas;
    }

    private ClinicaFacade getFacade() {
        return ejbFacade;
    }

    public ClinicaFilter getFiltro() {
        return filtro;
    }

    public void setFiltro(ClinicaFilter filtro) {
        this.filtro = filtro;
    }
    
    public StatusPadrao[] getStatus(){
        return StatusPadrao.values();
    }
}
