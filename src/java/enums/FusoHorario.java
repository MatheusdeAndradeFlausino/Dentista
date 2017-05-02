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
public enum FusoHorario {

    BRASILIA("(GMT-03:00)Brasília"),
    MANAUS("(GMT-04:00)Manaus"),
    RIOBRANCO("(GMT-05:00)Rio Branco");

    private final String fuso;

    FusoHorario(String fuso) {
        this.fuso = fuso;
    }

    public String getFuso() {
        return this.fuso;
    }

    public static FusoHorario getReferencia(String fuso) {
        switch (fuso) {
            case "(GMT-03:00)Brasília":
                return FusoHorario.BRASILIA;
            case "(GMT-04:00)Manaus":
                return FusoHorario.MANAUS;
            case "(GMT-05:00)Rio Branco":
                return FusoHorario.RIOBRANCO;
            default:
                return null;
        }
    }

}
