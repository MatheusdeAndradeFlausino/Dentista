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
public enum TipoConta {
    FORNECEDOR("Fornecedor"),
    PROFISSIONAL("Profissional");
    
    private final String tipo;
    
    TipoConta(String tipo){
        this.tipo = tipo;
    }
    
    public String getTipo(){
        return this.tipo;
    }
    
    public static TipoConta getReferencia(String tipo){
        switch(tipo){
            case "Fornecedor":
                return TipoConta.FORNECEDOR;
            case "Profissional":
                return TipoConta.PROFISSIONAL;
            default:
                return null;
        }
    }
}
