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
import javax.persistence.Lob;
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
@Table(name = "PACIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paciente.findAll", query = "SELECT p FROM Paciente p"),
    @NamedQuery(name = "Paciente.findById", query = "SELECT p FROM Paciente p WHERE p.id = :id"),
    @NamedQuery(name = "Paciente.findByNumFicha", query = "SELECT p FROM Paciente p WHERE p.numFicha = :numFicha"),
    @NamedQuery(name = "Paciente.findByStatus", query = "SELECT p FROM Paciente p WHERE p.status = :status")})
public class Paciente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "numFicha")
    private Integer numFicha;
    @Lob
    @Size(max = 65535)
    @Column(name = "observacao")
    private String observacao;
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "idPaciente")
    private Collection<Mensalidade> mensalidadeCollection;
    @OneToMany(mappedBy = "idPaciente")
    private Collection<Agendamento> agendamentoCollection;
    @OneToMany(mappedBy = "idPaciente")
    private Collection<Cheque> chequeCollection;
    @OneToMany(mappedBy = "idPaciente")
    private Collection<Indicacao> indicacaoCollection;
    @OneToMany(mappedBy = "idPaciente")
    private Collection<Orcamento> orcamentoCollection;
    @OneToMany(mappedBy = "idPaciente")
    private Collection<ContaAvulsa> contaAvulsaCollection;
    @OneToMany(mappedBy = "idPaciente")
    private Collection<HistoricoLaboratorio> historicoLaboratorioCollection;
    @OneToMany(mappedBy = "idPaciente")
    private Collection<FilaEspera> filaEsperaCollection;
    @OneToMany(mappedBy = "idPaciente")
    private Collection<Orto> ortoCollection;
    @JoinColumn(name = "idPessoa", referencedColumnName = "id")
    @ManyToOne
    private Pessoa idPessoa;
    @OneToMany(mappedBy = "idPaciente")
    private Collection<Anamnese> anamneseCollection;
    @OneToMany(mappedBy = "idPaciente")
    private Collection<Documentacao> documentacaoCollection;

    public Paciente() {
    }

    public Paciente(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumFicha() {
        return numFicha;
    }

    public void setNumFicha(Integer numFicha) {
        this.numFicha = numFicha;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<Mensalidade> getMensalidadeCollection() {
        return mensalidadeCollection;
    }

    public void setMensalidadeCollection(Collection<Mensalidade> mensalidadeCollection) {
        this.mensalidadeCollection = mensalidadeCollection;
    }

    @XmlTransient
    public Collection<Agendamento> getAgendamentoCollection() {
        return agendamentoCollection;
    }

    public void setAgendamentoCollection(Collection<Agendamento> agendamentoCollection) {
        this.agendamentoCollection = agendamentoCollection;
    }

    @XmlTransient
    public Collection<Cheque> getChequeCollection() {
        return chequeCollection;
    }

    public void setChequeCollection(Collection<Cheque> chequeCollection) {
        this.chequeCollection = chequeCollection;
    }

    @XmlTransient
    public Collection<Indicacao> getIndicacaoCollection() {
        return indicacaoCollection;
    }

    public void setIndicacaoCollection(Collection<Indicacao> indicacaoCollection) {
        this.indicacaoCollection = indicacaoCollection;
    }

    @XmlTransient
    public Collection<Orcamento> getOrcamentoCollection() {
        return orcamentoCollection;
    }

    public void setOrcamentoCollection(Collection<Orcamento> orcamentoCollection) {
        this.orcamentoCollection = orcamentoCollection;
    }

    @XmlTransient
    public Collection<ContaAvulsa> getContaAvulsaCollection() {
        return contaAvulsaCollection;
    }

    public void setContaAvulsaCollection(Collection<ContaAvulsa> contaAvulsaCollection) {
        this.contaAvulsaCollection = contaAvulsaCollection;
    }

    @XmlTransient
    public Collection<HistoricoLaboratorio> getHistoricoLaboratorioCollection() {
        return historicoLaboratorioCollection;
    }

    public void setHistoricoLaboratorioCollection(Collection<HistoricoLaboratorio> historicoLaboratorioCollection) {
        this.historicoLaboratorioCollection = historicoLaboratorioCollection;
    }

    @XmlTransient
    public Collection<FilaEspera> getFilaEsperaCollection() {
        return filaEsperaCollection;
    }

    public void setFilaEsperaCollection(Collection<FilaEspera> filaEsperaCollection) {
        this.filaEsperaCollection = filaEsperaCollection;
    }

    @XmlTransient
    public Collection<Orto> getOrtoCollection() {
        return ortoCollection;
    }

    public void setOrtoCollection(Collection<Orto> ortoCollection) {
        this.ortoCollection = ortoCollection;
    }

    public Pessoa getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Pessoa idPessoa) {
        this.idPessoa = idPessoa;
    }

    @XmlTransient
    public Collection<Anamnese> getAnamneseCollection() {
        return anamneseCollection;
    }

    public void setAnamneseCollection(Collection<Anamnese> anamneseCollection) {
        this.anamneseCollection = anamneseCollection;
    }

    @XmlTransient
    public Collection<Documentacao> getDocumentacaoCollection() {
        return documentacaoCollection;
    }

    public void setDocumentacaoCollection(Collection<Documentacao> documentacaoCollection) {
        this.documentacaoCollection = documentacaoCollection;
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
        if (!(object instanceof Paciente)) {
            return false;
        }
        Paciente other = (Paciente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.Paciente[ id=" + id + " ]";
    }
    
}
