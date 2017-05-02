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
public enum Afirmacao {
    SIM("Sim"),NAO("Não");
    
    private final String valor;
    
    Afirmacao(String valor){
        this.valor = valor;
    }
    
    public String getValor(){
        return this.valor;
    }
    
    public static Afirmacao getReferencia(String valor){
        switch(valor){
            case "Sim":
                return Afirmacao.SIM;
            case "Não":
                return Afirmacao.NAO;
            default:
                return null;
        }
    }
}
