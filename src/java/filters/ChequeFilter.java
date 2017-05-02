/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import classes.Paciente;
import java.util.Date;

/**
 *
 * @author valderlei
 */
public class ChequeFilter {
    
    private Paciente paciente;
    private Date dataInicialVencimento;
    private Date dataFinalVencimento;
    private Date dataInicialBaixa;
    private Date dataFinalBaixa;
    private Date dataInicialEmissao;
    private Date dataFinalEmissao;
    private String isChequeBaixado;
    private String nomeCheque;
    private Integer numCheque;

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Date getDataInicialVencimento() {
        return dataInicialVencimento;
    }

    public void setDataInicialVencimento(Date dataInicialVencimento) {
        this.dataInicialVencimento = dataInicialVencimento;
    }

    public Date getDataFinalVencimento() {
        return dataFinalVencimento;
    }

    public void setDataFinalVencimento(Date dataFinalVencimento) {
        this.dataFinalVencimento = dataFinalVencimento;
    }

    public Date getDataInicialBaixa() {
        return dataInicialBaixa;
    }

    public void setDataInicialBaixa(Date dataInicialBaixa) {
        this.dataInicialBaixa = dataInicialBaixa;
    }

    public Date getDataFinalBaixa() {
        return dataFinalBaixa;
    }

    public void setDataFinalBaixa(Date dataFinalBaixa) {
        this.dataFinalBaixa = dataFinalBaixa;
    }

    public Date getDataInicialEmissao() {
        return dataInicialEmissao;
    }

    public void setDataInicialEmissao(Date dataInicialEmissao) {
        this.dataInicialEmissao = dataInicialEmissao;
    }

    public Date getDataFinalEmissao() {
        return dataFinalEmissao;
    }

    public void setDataFinalEmissao(Date dataFinalEmissao) {
        this.dataFinalEmissao = dataFinalEmissao;
    }

    public String getIsChequeBaixado() {
        return isChequeBaixado;
    }

    public void setIsChequeBaixado(String isChequeBaixado) {
        this.isChequeBaixado = isChequeBaixado;
    }

    public String getNomeCheque() {
        return nomeCheque;
    }

    public void setNomeCheque(String nomeCheque) {
        this.nomeCheque = nomeCheque;
    }

    public Integer getNumCheque() {
        return numCheque;
    }

    public void setNumCheque(Integer numCheque) {
        this.numCheque = numCheque;
    }
    
    
    
}
