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
public enum LancamentoCartaoCredito {

    ADIANTAMENTO("Adiantamento da parcela"),
    BRUTO_DO_DIA("Bruto do dia"),
    DIA_DA_PARCELA("Dia da Parcela");

    private final String tipo;

    LancamentoCartaoCredito(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return this.tipo;
    }

    public static LancamentoCartaoCredito getReferencia(String tipo) {
        switch (tipo) {
            case "Adiantamento da parcela":
                return LancamentoCartaoCredito.ADIANTAMENTO;
            case "Dia da Parcela":
                return LancamentoCartaoCredito.DIA_DA_PARCELA;
            case "Bruto do dia":
                return LancamentoCartaoCredito.BRUTO_DO_DIA;
            default:
                return null;
        }
    }

}
