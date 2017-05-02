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
public enum StatusAgendamento {

    AGENDADO("Agendado"),
    ATENDIDO("Atendido"),
    CANCELADO("Cancelado"),
    CONFIRMADO("Confirmado"),
    ENCAIXE("Encaixe"),
    FALTOU("Faltou"),
    REMARCADO("Remarcado");

    private final String status;

    StatusAgendamento(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public static StatusAgendamento getReferencia(String status) {
        switch (status) {
            case "Agendado":
                return StatusAgendamento.AGENDADO;
            case "Atendido":
                return StatusAgendamento.ATENDIDO;
            case "Cancelado":
                return StatusAgendamento.CANCELADO;
            case "Confirmado":
                return StatusAgendamento.CONFIRMADO;
            case "Encaixe":
                return StatusAgendamento.ENCAIXE;
            case "Faltou":
                return StatusAgendamento.FALTOU;
            case "Remarcador":
                return StatusAgendamento.REMARCADO;
            default:
                return null;
        }
    }
}
