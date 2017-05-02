/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lovControllers;

import classes.Fornecedor;
import enums.Despesa;
import enums.StatusPadrao;
import facades.FornecedorFacade;
import filters.FornecedorFilter;
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
@Named(value = "searchFornecedorController")
@SessionScoped
public class SearchFornecedorController implements Serializable {

    private FornecedorFilter filtro;
    @EJB
    private facades.FornecedorFacade ejbFacade;
    private List<Fornecedor> fornecedores;
    
    /**
     * Creates a new instance of SearchFornecedorController
     */
    public SearchFornecedorController() {
    }

    @PostConstruct
    public void init() {
        filtro = new FornecedorFilter();
    }
    
    public void pesquisar() {
        fornecedores = getFacade().findByFilter(filtro);
    }
    
    public void selecionar(Fornecedor fornecedor) {
        filtro = new FornecedorFilter();
        fornecedores = new ArrayList<>();
        RequestContext.getCurrentInstance().closeDialog(fornecedor);
    }
    
    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("width", 800);
        opcoes.put("height", 600);
        opcoes.put("contentHeight", "100%");
        opcoes.put("contentWidth", "100%");
        RequestContext.getCurrentInstance().openDialog("/webapp/lovs/SearchFornecedor", opcoes, null);
    }
    
    public FornecedorFilter getFiltro() {
        return filtro;
    }

    public void setFiltro(FornecedorFilter filtro) {
        this.filtro = filtro;
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }
 
    private FornecedorFacade getFacade(){
        return ejbFacade;
    }
    
    public StatusPadrao[] getStatus(){
        return StatusPadrao.values();
    }
    
    public Despesa[] getDespesas(){
        return Despesa.values();
    }
    
}
