/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valderlei
 */
@Entity
@Table(name = "CHEQUE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cheque.findAll", query = "SELECT c FROM Cheque c"),
    @NamedQuery(name = "Cheque.findById", query = "SELECT c FROM Cheque c WHERE c.id = :id"),
    @NamedQuery(name = "Cheque.findByNomeCheque", query = "SELECT c FROM Cheque c WHERE c.nomeCheque = :nomeCheque"),
    @NamedQuery(name = "Cheque.findByNumCheque", query = "SELECT c FROM Cheque c WHERE c.numCheque = :numCheque"),
    @NamedQuery(name = "Cheque.findByBanco", query = "SELECT c FROM Cheque c WHERE c.banco = :banco"),
    @NamedQuery(name = "Cheque.findByAgencia", query = "SELECT c FROM Cheque c WHERE c.agencia = :agencia"),
    @NamedQuery(name = "Cheque.findByContaCorrente", query = "SELECT c FROM Cheque c WHERE c.contaCorrente = :contaCorrente"),
    @NamedQuery(name = "Cheque.findByDataEmissao", query = "SELECT c FROM Cheque c WHERE c.dataEmissao = :dataEmissao"),
    @NamedQuery(name = "Cheque.findByDataVencimento", query = "SELECT c FROM Cheque c WHERE c.dataVencimento = :dataVencimento"),
    @NamedQuery(name = "Cheque.findByDataBaixa", query = "SELECT c FROM Cheque c WHERE c.dataBaixa = :dataBaixa"),
    @NamedQuery(name = "Cheque.findByValor", query = "SELECT c FROM Cheque c WHERE c.valor = :valor"),
    @NamedQuery(name = "Cheque.findByIsChequeBaixado", query = "SELECT c FROM Cheque c WHERE c.isChequeBaixado = :isChequeBaixado")})
public class Cheque implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "nomeCheque")
    private String nomeCheque;
    @Column(name = "numCheque")
    private Integer numCheque;
    @Size(max = 50)
    @Column(name = "banco")
    private String banco;
    @Size(max = 20)
    @Column(name = "agencia")
    private String agencia;
    @Size(max = 20)
    @Column(name = "contaCorrente")
    private String contaCorrente;
    @Column(name = "dataEmissao")
    @Temporal(TemporalType.DATE)
    private Date dataEmissao;
    @Column(name = "dataVencimento")
    @Temporal(TemporalType.DATE)
    private Date dataVencimento;
    @Column(name = "dataBaixa")
    @Temporal(TemporalType.DATE)
    private Date dataBaixa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Size(max = 3)
    @Column(name = "isChequeBaixado")
    private String isChequeBaixado;
    @Lob
    @Size(max = 65535)
    @Column(name = "destino")
    private String destino;
    @JoinColumn(name = "idPaciente", referencedColumnName = "id")
    @ManyToOne
    private Paciente idPaciente;

    public Cheque() {
    }

    public Cheque(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(String contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Date getDataBaixa() {
        return dataBaixa;
    }

    public void setDataBaixa(Date dataBaixa) {
        this.dataBaixa = dataBaixa;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getIsChequeBaixado() {
        return isChequeBaixado;
    }

    public void setIsChequeBaixado(String isChequeBaixado) {
        this.isChequeBaixado = isChequeBaixado;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Paciente getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Paciente idPaciente) {
        this.idPaciente = idPaciente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cheque)) {
            return false;
        }
        Cheque other = (Cheque) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.Cheque[ id=" + id + " ]";
    }
    
}
