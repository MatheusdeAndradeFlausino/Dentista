/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import classes.ContasPagar;
import classes.Paciente;
import classes.Profissional;
import facades.ContasPagarFacade;
import facades.PacienteFacade;
import facades.ProfissionalFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author vanderlei
 */
@Named(value = "homeController")
@SessionScoped
public class HomeController implements Serializable {

    @EJB
    private ProfissionalFacade profissionalFacade;
    
    @EJB
    private PacienteFacade pacienteFacade;
    
    @EJB
    private ContasPagarFacade contasPagarFacade;
    
    private ProfissionalFacade getProfissionalFacade(){
        return profissionalFacade;
    }
    
    private PacienteFacade getPacienteFacade(){
        return pacienteFacade;
    }
    
    private ContasPagarFacade getContasPagarFacade(){
        return contasPagarFacade;
    }
    
    /**
     * Creates a new instance of HomeController
     */
    public HomeController() {
    }
    
    public List<Paciente> pacientesAniversariantes(){
        return getPacienteFacade().findAllAniversariantes(new Date());
    }
    
    public List<Profissional> profissionaisAniversariantes(){
        return getProfissionalFacade().findAllAniversariantes(new Date());
    }
    
    public List<ContasPagar> contasVencidas(){
        return getContasPagarFacade().findAllContasVencidas(new Date());
    }
    
    
}
