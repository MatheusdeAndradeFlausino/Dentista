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
public enum PagamentoProfissional {

    DIARIO("Diário"), PROCEDIMENTO("Procedimento");

    private final String pagamento;

    PagamentoProfissional(String pagamento) {
        this.pagamento = pagamento;
    }

    public String getPagamento() {
        return this.pagamento;
    }

    public static PagamentoProfissional getReferencia(String pagamento) {
        switch (pagamento) {
            case "Diário":
                return PagamentoProfissional.DIARIO;
            case "Procedimento":
                return PagamentoProfissional.PROCEDIMENTO;
            default:
                return null;
        }
    }
}
