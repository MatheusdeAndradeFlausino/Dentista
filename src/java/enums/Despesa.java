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
public enum Despesa {

    PESSOA("Pessoa"),
    IMPOSTO("Imposto"),
    FORNECEDOR_DE_PRODUTOS("Fornecedor de Produtos"),
    DESPESA_FIXA("Despesa Fixa"),
    DESPESA_VARIAVEL("Despesa Variável");

    private final String tipo;

    Despesa(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return this.tipo;
    }

    public static Despesa getReferencia(String tipo) {
        switch (tipo) {
            case "Pessoa":
                return Despesa.PESSOA;
            case "Imposto":
                return Despesa.IMPOSTO;
            case "Fornecedor de Produtos":
                return Despesa.FORNECEDOR_DE_PRODUTOS;
            case "Despesa Fixa":
                return Despesa.DESPESA_FIXA;
            case "Despesa Variável":
                return Despesa.DESPESA_VARIAVEL;
            default:
                return null;                      
        }
    }

}
