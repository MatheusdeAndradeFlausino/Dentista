/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lovControllers;

import facades.PacienteFacade;
import classes.Paciente;
import enums.StatusPadrao;
import filters.PacienteFilter;
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
@Named(value = "searchPacienteController")
@SessionScoped
public class SearchPacienteController implements Serializable {

    private PacienteFilter filtro;
    @EJB
    private facades.PacienteFacade ejbFacade;
    private List<Paciente> pacientes;

    
    /**
     * Creates a new instance of SearchPacienteController
     */
    public SearchPacienteController() {
    }
    
    @PostConstruct
    public void init(){
        filtro = new PacienteFilter();
    }
    
    public PacienteFilter getFiltro() {
        return filtro;
    }

    public void setFiltro(PacienteFilter filtro) {
        this.filtro = filtro;
    }
    
    private PacienteFacade getFacade() {
        return ejbFacade;
    }
    
    public void pesquisar(){
        pacientes = getFacade().findByFilter(filtro);
    }
    
    public void selecionar(Paciente paciente){
        filtro = new PacienteFilter();
        pacientes = new ArrayList<>();
        RequestContext.getCurrentInstance().closeDialog(paciente);
    }
    
    public void abrirDialogo(){
        Map<String,Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("width", 800);
        opcoes.put("height", 600);
        opcoes.put("contentHeight", "100%");
        opcoes.put("contentWidth", "100%");
        RequestContext.getCurrentInstance().openDialog("/webapp/lovs/SearchPaciente",opcoes, null);
    }
    
    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }
    
    public StatusPadrao[] getStatus(){
        return StatusPadrao.values();
    }
    
}
