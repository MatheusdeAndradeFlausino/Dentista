/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import classes.PermissoesGrupo;
import enums.ModuloNomes;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vanderlei
 */
public class ModulosPermissoes {

    private List<Modulo> modulosPermitidos;

    public ModulosPermissoes(){
        modulosPermitidos = new ArrayList<>();
        popularModulosPermissoes();
    }
    
    public List<Modulo> getModulosPermitidos() {
        return modulosPermitidos;
    }

    public void setModulosPermitidos(List<Modulo> modulosPermitidos) {
        this.modulosPermitidos = modulosPermitidos;
    }
    
    public void popularModulosPermissoes(List<PermissoesGrupo> permissoesGrupos) {
        for (PermissoesGrupo pg : permissoesGrupos) {
            setBooleanPermissaoComNome(pg.getNomePermissao(), true);
        }
    }  
    
    public void setBooleanPermissaoComNome(String nome, boolean valor){
        for (Modulo modulosPermitido : modulosPermitidos) {
            if(modulosPermitido.getNome().equals(nome)){
                modulosPermitido.setPermitir(valor);
                break;
            }
        }
    }
    
    private void popularModulosPermissoes() {
       for(ModuloNomes nome : ModuloNomes.values()){
           Modulo modulo = new Modulo(nome.getNome(),false);
           modulosPermitidos.add(modulo);
       }
    }

    public class Modulo {

        private String nome;
        private boolean permitir;

        public Modulo(String nome, boolean permitir) {
            this.nome = nome;
            this.permitir = permitir;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public boolean isPermitir() {
            return permitir;
        }

        public void setPermitir(boolean permitir) {
            this.permitir = permitir;
        }
    }
}
