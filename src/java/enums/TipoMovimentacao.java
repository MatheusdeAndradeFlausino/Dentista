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
public enum TipoMovimentacao {
    ENTRADA("Entrada"),
    SAIDA("Saída");
    
    private final String tipo;
        
    TipoMovimentacao(String tipo){
        this.tipo = tipo;
    }
    
    public String getTipo(){
        return this.tipo;
    }
    
    public static TipoMovimentacao getReferencia(String tipo){
        switch(tipo){
            case "Entrada":
                return TipoMovimentacao.ENTRADA;
            case "Saída":
                return TipoMovimentacao.SAIDA;
            default:
                return null;
        }
    }
}
