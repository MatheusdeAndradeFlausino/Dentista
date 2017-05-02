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
public enum StatusContasPagar {
    A_PAGAR("A Pagar"),
    PAGO("Pago");
    
    private final String status;
    
    StatusContasPagar(String status){
        this.status = status;
    }
    
    public String getStatus(){
        return this.status;
    }
    
    public static StatusContasPagar getReferencia(String status){
        switch(status){
            case "A Pagar":
                return StatusContasPagar.A_PAGAR;
            case "Pago":
                return StatusContasPagar.PAGO;
            default:
                return null;
        }
    }
}
