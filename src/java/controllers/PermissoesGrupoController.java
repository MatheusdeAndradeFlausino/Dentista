/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import classes.Grupo;
import classes.PermissoesGrupo;
import enums.StatusPadrao;
import facades.GrupoFacade;
import facades.PermissoesGrupoFacade;
import filters.GrupoFilter;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import utils.ModulosPermissoes;
import utils.ModulosPermissoes.Modulo;

/**
 *
 * @author vanderlei
 */
@Named(value = "permissoesGrupoController")
@SessionScoped
public class PermissoesGrupoController implements Serializable {
    
    @EJB
    private facades.GrupoFacade grupoFacade;
    
    @EJB
    private facades.PermissoesGrupoFacade ejbFacade;
    
    private List<Grupo> grupos;
    private GrupoFilter filtro;
    private ModulosPermissoes permissoes;
    private Grupo grupo;
    
    
    public PermissoesGrupoController() {
    }
    
    private PermissoesGrupoFacade getFacade(){
        return ejbFacade;
    }
    
    private GrupoFacade getGrupoFacade() {
        return grupoFacade;
    }
    
    public void limpar(){
        filtro = new GrupoFilter();
        grupo = null;
    }
    
    public void pesquisar(){
        grupos = getGrupoFacade().findByFilter(getFiltro());
    }
    
    public void encontrarModulosPermitidosParaOGrupo(){
        permissoes = new ModulosPermissoes();
        permissoes.popularModulosPermissoes(getFacade().findByGrupo(grupo));
    }

    public void salvarPermissoes(){
        getFacade().deleteAllPermissionsFromGrupo(grupo);
        for (Modulo modulo : permissoes.getModulosPermitidos()) {
            if(modulo.isPermitir()){
                PermissoesGrupo pg = new PermissoesGrupo();
                pg.setGrupo(grupo);
                pg.setNomePermissao(modulo.getNome());
                getFacade().create(pg);
            }
        }
    }
    
    public boolean renderizarCamposBooleanos(){
        return grupo != null;
    }
    
    public List<Grupo> getGrupos() {
        if(grupos == null)
            grupos = new ArrayList<>();
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public GrupoFilter getFiltro() {
        if(filtro == null)
            filtro = new GrupoFilter();
        return filtro;
    }

    public void setFiltro(GrupoFilter filtro) {
        this.filtro = filtro;
    }
    
    public StatusPadrao[] getStatus(){
        return StatusPadrao.values();
    }    

    public ModulosPermissoes getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(ModulosPermissoes permissoes) {
        this.permissoes = permissoes;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
}
