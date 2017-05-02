/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author valderlei
 */
@Entity
@Table(name = "PROFISSIONAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Profissional.findAll", query = "SELECT p FROM Profissional p"),
    @NamedQuery(name = "Profissional.findById", query = "SELECT p FROM Profissional p WHERE p.id = :id"),
    @NamedQuery(name = "Profissional.findByProfissao", query = "SELECT p FROM Profissional p WHERE p.profissao = :profissao"),
    @NamedQuery(name = "Profissional.findByBanco", query = "SELECT p FROM Profissional p WHERE p.banco = :banco"),
    @NamedQuery(name = "Profissional.findByAgencia", query = "SELECT p FROM Profissional p WHERE p.agencia = :agencia"),
    @NamedQuery(name = "Profissional.findByContaCorrente", query = "SELECT p FROM Profissional p WHERE p.contaCorrente = :contaCorrente"),
    @NamedQuery(name = "Profissional.findByStatus", query = "SELECT p FROM Profissional p WHERE p.status = :status")})
public class Profissional implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "profissao")
    private String profissao;
    @Size(max = 50)
    @Column(name = "banco")
    private String banco;
    @Size(max = 10)
    @Column(name = "agencia")
    private String agencia;
    @Size(max = 10)
    @Column(name = "contaCorrente")
    private String contaCorrente;
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "idProfissional")
    private Collection<ProcedimentoOrto> procedimentoOrtoCollection;
    @OneToMany(mappedBy = "idProfissional")
    private Collection<Agendamento> agendamentoCollection;
    @OneToMany(mappedBy = "idProfissional")
    private Collection<Indicacao> indicacaoCollection;
    @OneToMany(mappedBy = "idProfissional")
    private Collection<ContasPagar> contasPagarCollection;
    @OneToMany(mappedBy = "idProfissional")
    private Collection<Orcamento> orcamentoCollection;
    @OneToMany(mappedBy = "idProfissional")
    private Collection<FilaEspera> filaEsperaCollection;
    @JoinColumn(name = "idClinica", referencedColumnName = "id")
    @ManyToOne
    private Clinica idClinica;
    @JoinColumn(name = "idPessoa", referencedColumnName = "id")
    @ManyToOne
    private Pessoa idPessoa;
    @JoinColumn(name = "idRegraComissao", referencedColumnName = "id")
    @ManyToOne
    private RegraComissao idRegraComissao;
    @OneToMany(mappedBy = "idProfissional")
    private Collection<Anamnese> anamneseCollection;

    public Profissional() {
    }

    public Profissional(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<ProcedimentoOrto> getProcedimentoOrtoCollection() {
        return procedimentoOrtoCollection;
    }

    public void setProcedimentoOrtoCollection(Collection<ProcedimentoOrto> procedimentoOrtoCollection) {
        this.procedimentoOrtoCollection = procedimentoOrtoCollection;
    }

    @XmlTransient
    public Collection<Agendamento> getAgendamentoCollection() {
        return agendamentoCollection;
    }

    public void setAgendamentoCollection(Collection<Agendamento> agendamentoCollection) {
        this.agendamentoCollection = agendamentoCollection;
    }

    @XmlTransient
    public Collection<Indicacao> getIndicacaoCollection() {
        return indicacaoCollection;
    }

    public void setIndicacaoCollection(Collection<Indicacao> indicacaoCollection) {
        this.indicacaoCollection = indicacaoCollection;
    }

    @XmlTransient
    public Collection<ContasPagar> getContasPagarCollection() {
        return contasPagarCollection;
    }

    public void setContasPagarCollection(Collection<ContasPagar> contasPagarCollection) {
        this.contasPagarCollection = contasPagarCollection;
    }

    @XmlTransient
    public Collection<Orcamento> getOrcamentoCollection() {
        return orcamentoCollection;
    }

    public void setOrcamentoCollection(Collection<Orcamento> orcamentoCollection) {
        this.orcamentoCollection = orcamentoCollection;
    }

    @XmlTransient
    public Collection<FilaEspera> getFilaEsperaCollection() {
        return filaEsperaCollection;
    }

    public void setFilaEsperaCollection(Collection<FilaEspera> filaEsperaCollection) {
        this.filaEsperaCollection = filaEsperaCollection;
    }

    public Clinica getIdClinica() {
        return idClinica;
    }

    public void setIdClinica(Clinica idClinica) {
        this.idClinica = idClinica;
    }

    public Pessoa getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Pessoa idPessoa) {
        this.idPessoa = idPessoa;
    }

    public RegraComissao getIdRegraComissao() {
        return idRegraComissao;
    }

    public void setIdRegraComissao(RegraComissao idRegraComissao) {
        this.idRegraComissao = idRegraComissao;
    }

    @XmlTransient
    public Collection<Anamnese> getAnamneseCollection() {
        return anamneseCollection;
    }

    public void setAnamneseCollection(Collection<Anamnese> anamneseCollection) {
        this.anamneseCollection = anamneseCollection;
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
        if (!(object instanceof Profissional)) {
            return false;
        }
        Profissional other = (Profissional) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.Profissional[ id=" + id + " ]";
    }
    
}
