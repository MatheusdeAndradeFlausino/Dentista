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
public enum TipoComissao {

    PORCENTAGEM("Porcentagem"),
    VALOR_FIXO("Valor Fixo");

    private final String tipo;

    TipoComissao(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return this.tipo;
    }

    public static TipoComissao getReferencia(String tipo) {
        switch (tipo) {
            case "Porcentagem":
                return TipoComissao.PORCENTAGEM;
            case "Valor Fixo":
                return TipoComissao.VALOR_FIXO;
            default:
                return null;
        }
    }
}
