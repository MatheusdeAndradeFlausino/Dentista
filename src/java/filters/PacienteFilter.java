/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

/**
 *
 * @author valderlei
 */
public class PacienteFilter {
    
    private String nome = null;
    private Integer numFicha = null;
    private String status = null;
    private String cpf = null;
    private String rg = null;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumFicha() {
        return numFicha;
    }

    public void setNumFicha(Integer numFicha) {
        this.numFicha = numFicha;
    }    
}
