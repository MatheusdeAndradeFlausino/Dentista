/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import classes.Especialidade;
import classes.TabelaProcedimento;

/**
 *
 * @author valderlei
 */
public class ProcedimentoFilter {
    private String descricao;
    private TabelaProcedimento tabelaProcedimento;
    private Especialidade especialidade;
    private String status;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TabelaProcedimento getTabelaProcedimento() {
        return tabelaProcedimento;
    }

    public void setTabelaProcedimento(TabelaProcedimento tabelaProcedimento) {
        this.tabelaProcedimento = tabelaProcedimento;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
