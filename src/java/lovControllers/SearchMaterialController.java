/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lovControllers;

import classes.Clinica;
import classes.Material;
import enums.StatusPadrao;
import facades.MaterialFacade;
import filters.MaterialFilter;
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
@Named(value = "searchMaterialController")
@SessionScoped
public class SearchMaterialController implements Serializable {

    private MaterialFilter filtro;
    @EJB
    private facades.MaterialFacade ejbFacade;
    private List<Material> materiais;
    
    /**
     * Creates a new instance of SearchMaterialController
     */
    
    @PostConstruct
    public void init() {
        filtro = new MaterialFilter();
    }
    
    public void pesquisar() {
        materiais = getFacade().findByFilter(filtro);
    }
    
    public void selecionar(Material material) {
        filtro = new MaterialFilter();
        materiais = new ArrayList<>();
        RequestContext.getCurrentInstance().closeDialog(material);
    }
    
    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("width", 800);
        opcoes.put("height", 600);
        opcoes.put("contentHeight", "100%");
        opcoes.put("contentWidth", "100%");
        RequestContext.getCurrentInstance().openDialog("/webapp/lovs/SearchMaterial", opcoes, null);
    }
    
    public void clinicaSelecionada(SelectEvent event){
        Clinica clinica = (Clinica)event.getObject();
        this.filtro.setClinica(clinica);
    }
    
    public SearchMaterialController() {
    }

    public MaterialFilter getFiltro() {
        return filtro;
    }

    public void setFiltro(MaterialFilter filtro) {
        this.filtro = filtro;
    }

    public MaterialFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(MaterialFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public List<Material> getMateriais() {
        return materiais;
    }

    public void setMateriais(List<Material> materiais) {
        this.materiais = materiais;
    }
      
    private MaterialFacade getFacade(){
        return ejbFacade;
    }
    
    public StatusPadrao[] getStatus(){
        return StatusPadrao.values();
    }
    
}
