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
public enum StatusMensalidade {

    ATIVA("Ativa"),
    QUITADA("Quitada"),
    CANCELADA("Cancelada");

    private final String status;

    StatusMensalidade(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public static StatusMensalidade getReferencia(String status) {
        switch (status) {
            case "Ativa":
                return StatusMensalidade.ATIVA;
            case "Quitada":
                return StatusMensalidade.QUITADA;
            case "Cancelada":
                return StatusMensalidade.CANCELADA;
            default:
                return null;
        }
    }
}
