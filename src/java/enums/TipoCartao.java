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
public enum TipoCartao {
    CREDITO("Crédito"),
    DEBITO("Débito");
    
    private final String tipo;
    
    TipoCartao(String tipo){
        this.tipo = tipo;
    }
    
    public String getTipo(){
        return this.tipo;
    }
    
    public final TipoCartao getReferencia(String tipo){
        switch(tipo){
            case "Crédito":
                return TipoCartao.CREDITO;
            case "Débito":
                return TipoCartao.DEBITO;
            default:
                return null;
        }
    }
}
