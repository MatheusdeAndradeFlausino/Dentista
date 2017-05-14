/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lovControllers;

import classes.Motivo;
import enums.StatusPadrao;
import facades.MotivoFacade;
import filters.MotivoFilter;
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
 * @author vanderlei
 */
@Named(value = "searchMotivoController")
@SessionScoped
public class SearchMotivoController implements Serializable {

    private MotivoFilter filtro;
    @EJB
    private facades.MotivoFacade ejbFacade;
    private List<Motivo> motivos;
    
    /**
     * Creates a new instance of SearchMotivoController
     */
    
    @PostConstruct
    public void init() {
        filtro = new MotivoFilter();
    }
    
    public void pesquisar() {
        motivos = getFacade().findByFilter(filtro);
    }
    
    public void selecionar(Motivo motivo) {
        filtro = new MotivoFilter();
        motivos = new ArrayList<>();
        RequestContext.getCurrentInstance().closeDialog(motivo);
    }
    
    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("width", 800);
        opcoes.put("height", 600);
        opcoes.put("contentHeight", "100%");
        opcoes.put("contentWidth", "100%");
        RequestContext.getCurrentInstance().openDialog("/webapp/lovs/SearchMotivo", opcoes, null);
    }
    
    
    public SearchMotivoController() {
    }

    public MotivoFilter getFiltro() {
        return filtro;
    }

    public void setFiltro(MotivoFilter filtro) {
        this.filtro = filtro;
    }

    public MotivoFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(MotivoFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public List<Motivo> getMotivos() {
        return motivos;
    }

    public void setMotivos(List<Motivo> motivos) {
        this.motivos = motivos;
    }
      
    private MotivoFacade getFacade(){
        return ejbFacade;
    }
    
    public StatusPadrao[] getStatus(){
        return StatusPadrao.values();
    }
    
}
