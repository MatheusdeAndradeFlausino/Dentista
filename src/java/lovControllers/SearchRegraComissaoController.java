/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lovControllers;

import classes.Clinica;
import classes.RegraComissao;
import enums.StatusPadrao;
import facades.RegraComissaoFacade;
import filters.RegraComissaoFilter;
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
@Named(value = "searchRegraComissaoController")
@SessionScoped
public class SearchRegraComissaoController implements Serializable {

    
    private RegraComissaoFilter filtro;
    @EJB
    private facades.RegraComissaoFacade ejbFacade;
    private List<RegraComissao> regrasComissao;
    /**
     * Creates a new instance of SearchRegraComissaoController
     */
    public SearchRegraComissaoController() {
    }
    
    @PostConstruct
    public void init() {
        filtro = new RegraComissaoFilter();
    }
    
    private RegraComissaoFacade getFacade(){
        return ejbFacade;
    }
    
     public void pesquisar() {
        regrasComissao = getFacade().findByFilter(filtro);
    }

    public void selecionar(RegraComissao regraComissao) {
        filtro = new RegraComissaoFilter();
        regrasComissao = new ArrayList<>();
        RequestContext.getCurrentInstance().closeDialog(regraComissao);
    }
    
    public void clinicaSelecionada(SelectEvent event){
        Clinica clinica = (Clinica)event.getObject();
        this.filtro.setClinica(clinica);
        System.out.println("Slecionou clinica regra");
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("width", 800);
        opcoes.put("height", 600);
        opcoes.put("contentHeight", "100%");
        opcoes.put("contentWidth", "100%");            
        RequestContext.getCurrentInstance().openDialog("/webapp/lovs/SearchRegraComissao", opcoes, null);
    }
    
    public StatusPadrao[] getStatus(){
        return StatusPadrao.values();
    }

    public RegraComissaoFilter getFiltro() {
        return filtro;
    }

    public void setFiltro(RegraComissaoFilter filtro) {
        this.filtro = filtro;
    }

    public List<RegraComissao> getRegrasComissao() {
        return regrasComissao;
    }

    public void setRegrasComissao(List<RegraComissao> regrasComissao) {
        this.regrasComissao = regrasComissao;
    }    
    
}
