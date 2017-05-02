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
public enum TipoComissionamento {
    INDIVIDUAL("Individual"),
    CONJUNTO("Conjunto");
    
    private final String tipo;
    
    TipoComissionamento(String tipo){
        this.tipo = tipo;
    }
    
    public String getTipo(){
        return this.tipo;
    }
    
    public static TipoComissionamento getReferencia(String tipo){
        switch(tipo){
            case "Individual":
                return TipoComissionamento.INDIVIDUAL;
            case "Conjunto":
                return TipoComissionamento.CONJUNTO;
            default:
                return null;
        }
    }
}
