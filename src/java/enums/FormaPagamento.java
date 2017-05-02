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
public enum FormaPagamento {

    BOLETO("Boleto"),
    CHEQUE("Cheque"),
    CARTAO_CREDITO("Cartão de Crédito"),
    CARTAO_DEBITO("Cartão de Débito"),
    DINHEIRO("Dinheiro");

    private final String forma;

    FormaPagamento(String forma) {
        this.forma = forma;
    }

    public String getForma() {
        return this.forma;
    }

    public static FormaPagamento getReferencia(String forma) {
        switch (forma) {
            case "Boleto":
                return FormaPagamento.BOLETO;
            case "Cheque":
                return FormaPagamento.CHEQUE;
            case "Cartão de Crédito":
                return FormaPagamento.CARTAO_CREDITO;
            case "Cartão de Débito":
                return FormaPagamento.CARTAO_DEBITO;
            case "Dinheiro":
                return FormaPagamento.DINHEIRO;
            default:
                return null;
        }
    }
}
