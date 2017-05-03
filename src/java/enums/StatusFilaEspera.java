/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

/**
 *
 * @author vanderlei
 */
public enum StatusFilaEspera {
    EM_ESPERA("Em Espera"),ATENDIDO("Atendido"),EM_ATENDIMENTO("Em Atendimento");
    
    private final String status;
    
    StatusFilaEspera(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    
    public StatusFilaEspera getReferencia(String status){
        switch(status){
            case "Em Atendimento":
                return StatusFilaEspera.EM_ATENDIMENTO;
            case "Em Espera":
                return StatusFilaEspera.EM_ESPERA;
            case "Atendido":
                return StatusFilaEspera.ATENDIDO;
            default:
                return null;
        }
    }
}
