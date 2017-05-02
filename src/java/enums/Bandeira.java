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
public enum Bandeira {

    VISA("Visa"),
    MASTERCARD("Mastercard"),
    AMERICAN_EXPRESS("American Express"),
    DINERS("Diners"),
    ELO("Elo"),
    OUTRA("Outra");

    private final String bandeira;

    Bandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public String getBandeira() {
        return this.bandeira;
    }

    public static Bandeira getReferencia(String bandeira) {
        switch (bandeira) {
            case "Elo":
                return Bandeira.ELO;
            case "Visa":
                return Bandeira.VISA;
            case "Mastercard":
                return Bandeira.MASTERCARD;
            case "Diners":
                return Bandeira.DINERS;
            case "American Express":
                return Bandeira.AMERICAN_EXPRESS;
            case "Outra":
                return Bandeira.OUTRA;
            default:
                return null;
        }
    }

}
