/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

/**
 *
 * @author valderlei
 */
public enum Horario {

    ZERO_HORA("00:00"),
    UMA_HORA("01:00"),
    DUAS_HORAS("02:00"),
    TRES_HORAS("03:00"),
    QUATRO_HORAS("04:00"),
    CINCO_HORAS("05:00"),
    SEIS_HORAS("06:00"),
    SETE_HORAS("07:00"),
    OITO_HORAS("08:00"),
    NOVE_HORAS("09:00"),
    DEZ_HORAS("10:00"),
    ONZE_HORAS("11:00"),
    DOZE_HORAS("12:00"),
    TREZE_HORAS("13:00"),
    QUATORZE_HORAS("14:00"),
    QUINZE_HORAS("15:00"),
    DEZESSEIS_HORAS("16:00"),
    DEZESSETE_HORAS("17:00"),
    DEZOITO_HORAS("18:00"),
    DEZENOVE_HORAS("19:00"),
    VINTE_HORAS("20:00"),
    VINTE_UMA_HORAS("21:00"),
    VINTE_DUAS_HORAS("22:00"),
    VINTE_TRES_HORAS("23:00");

    private final String valorRelogio;

    Horario(String valorRelogio) {
        this.valorRelogio = valorRelogio;
    }

    public String getValorRelogio() {
        return this.valorRelogio;
    }

    public static Horario getReferencia(String horario) {
        switch (horario) {
            case "00:00":
                return Horario.ZERO_HORA;
            case "01:00":
                return Horario.UMA_HORA;
            case "02:00":
                return Horario.DUAS_HORAS;
            case "03:00":
                return Horario.TRES_HORAS;
            case "04:00":
                return Horario.QUATRO_HORAS;
            case "05:00":
                return Horario.CINCO_HORAS;
            case "06:00":
                return Horario.SEIS_HORAS;
            case "07:00":
                return Horario.SETE_HORAS;
            case "08:00":
                return Horario.OITO_HORAS;
            case "09:00":
                return Horario.NOVE_HORAS;
            case "10:00":
                return Horario.DEZ_HORAS;
            case "11:00":
                return Horario.ONZE_HORAS;
            case "12:00":
                return Horario.DOZE_HORAS;
            case "13:00":
                return Horario.TREZE_HORAS;
            case "14:00":
                return Horario.QUATORZE_HORAS;
            case "15:00":
                return Horario.QUINZE_HORAS;
            case "16:00":
                return Horario.DEZESSEIS_HORAS;
            case "17:00":
                return Horario.DEZESSETE_HORAS;
            case "18:00":
                return Horario.DEZOITO_HORAS;
            case "19:00":
                return Horario.DEZENOVE_HORAS;
            case "20:00":
                return Horario.VINTE_HORAS;
            case "21:00":
                return Horario.VINTE_UMA_HORAS;
            case "22:00":
                return Horario.VINTE_DUAS_HORAS;
            case "23:00":
                return Horario.VINTE_TRES_HORAS;
            default:
                return null;
        }
    }
}
