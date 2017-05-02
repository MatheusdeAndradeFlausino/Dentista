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
public enum StatusPadrao {
    ATIVO("Ativo"),
    INATIVO("Inativo");
    
    private String status;
    
    StatusPadrao(String status){
        this.status = status;
    }
    
    public String getStatus(){
        return this.status;
    }
    
}
