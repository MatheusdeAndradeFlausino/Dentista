/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

/**
 *
 * @author valderlei
 */
public enum AberturaFuncionario {
    MANUAL("Manual"),
    AUTOMATICO("Automático");
    
    private final String tipo;
    
    AberturaFuncionario(String tipo){
        this.tipo = tipo;
    }
    
    public String getTipo(){
        return this.tipo;
    }
    
    public static AberturaFuncionario getReferencia(String tipo){
        switch(tipo){
            case "Manual":
                return AberturaFuncionario.MANUAL;
            case "Automático":
                return AberturaFuncionario.AUTOMATICO;
            default:
                return null;
        }
    } 
}
