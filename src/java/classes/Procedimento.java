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
@Table(name = "PROCEDIMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Procedimento.findAll", query = "SELECT p FROM Procedimento p"),
    @NamedQuery(name = "Procedimento.findById", query = "SELECT p FROM Procedimento p WHERE p.id = :id"),
    @NamedQuery(name = "Procedimento.findByValorPadrao", query = "SELECT p FROM Procedimento p WHERE p.valorPadrao = :valorPadrao"),
    @NamedQuery(name = "Procedimento.findByIsGerarComissao", query = "SELECT p FROM Procedimento p WHERE p.isGerarComissao = :isGerarComissao"),
    @NamedQuery(name = "Procedimento.findByIsGerarAtendimento", query = "SELECT p FROM Procedimento p WHERE p.isGerarAtendimento = :isGerarAtendimento"),
    @NamedQuery(name = "Procedimento.findByIsGerarLaboratorio", query = "SELECT p FROM Procedimento p WHERE p.isGerarLaboratorio = :isGerarLaboratorio"),
    @NamedQuery(name = "Procedimento.findByStatus", query = "SELECT p FROM Procedimento p WHERE p.status = :status")})
public class Procedimento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Size(max = 65535)
    @Column(name = "descricao")
    private String descricao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorPadrao")
    private Double valorPadrao;
    @Size(max = 3)
    @Column(name = "isGerarComissao")
    private String isGerarComissao;
    @Size(max = 3)
    @Column(name = "isGerarAtendimento")
    private String isGerarAtendimento;
    @Size(max = 3)
    @Column(name = "isGerarLaboratorio")
    private String isGerarLaboratorio;
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "idProcedimento")
    private Collection<ProcedimentoClinica> procedimentoClinicaCollection;
    @OneToMany(mappedBy = "idProcedimento")
    private Collection<ProcedimentoOrto> procedimentoOrtoCollection;
    @OneToMany(mappedBy = "idProcedimento")
    private Collection<ContaAvulsa> contaAvulsaCollection;
    @JoinColumn(name = "idTabela", referencedColumnName = "id")
    @ManyToOne
    private TabelaProcedimento idTabela;
    @JoinColumn(name = "idEspecialidade", referencedColumnName = "id")
    @ManyToOne
    private Especialidade idEspecialidade;
    @OneToMany(mappedBy = "idProcedimento")
    private Collection<OrcamentoProcedimento> orcamentoProcedimentoCollection;
    @OneToMany(mappedBy = "idProcedimento")
    private Collection<HistoricoLaboratorio> historicoLaboratorioCollection;
    @OneToMany(mappedBy = "idProcedimento")
    private Collection<RegraEspecial> regraEspecialCollection;

    public Procedimento() {
    }

    public Procedimento(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValorPadrao() {
        return valorPadrao;
    }

    public void setValorPadrao(Double valorPadrao) {
        this.valorPadrao = valorPadrao;
    }

    public String getIsGerarComissao() {
        return isGerarComissao;
    }

    public void setIsGerarComissao(String isGerarComissao) {
        this.isGerarComissao = isGerarComissao;
    }

    public String getIsGerarAtendimento() {
        return isGerarAtendimento;
    }

    public void setIsGerarAtendimento(String isGerarAtendimento) {
        this.isGerarAtendimento = isGerarAtendimento;
    }

    public String getIsGerarLaboratorio() {
        return isGerarLaboratorio;
    }

    public void setIsGerarLaboratorio(String isGerarLaboratorio) {
        this.isGerarLaboratorio = isGerarLaboratorio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<ProcedimentoClinica> getProcedimentoClinicaCollection() {
        return procedimentoClinicaCollection;
    }

    public void setProcedimentoClinicaCollection(Collection<ProcedimentoClinica> procedimentoClinicaCollection) {
        this.procedimentoClinicaCollection = procedimentoClinicaCollection;
    }

    @XmlTransient
    public Collection<ProcedimentoOrto> getProcedimentoOrtoCollection() {
        return procedimentoOrtoCollection;
    }

    public void setProcedimentoOrtoCollection(Collection<ProcedimentoOrto> procedimentoOrtoCollection) {
        this.procedimentoOrtoCollection = procedimentoOrtoCollection;
    }

    @XmlTransient
    public Collection<ContaAvulsa> getContaAvulsaCollection() {
        return contaAvulsaCollection;
    }

    public void setContaAvulsaCollection(Collection<ContaAvulsa> contaAvulsaCollection) {
        this.contaAvulsaCollection = contaAvulsaCollection;
    }

    public TabelaProcedimento getIdTabela() {
        return idTabela;
    }

    public void setIdTabela(TabelaProcedimento idTabela) {
        this.idTabela = idTabela;
    }

    public Especialidade getIdEspecialidade() {
        return idEspecialidade;
    }

    public void setIdEspecialidade(Especialidade idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }

    @XmlTransient
    public Collection<OrcamentoProcedimento> getOrcamentoProcedimentoCollection() {
        return orcamentoProcedimentoCollection;
    }

    public void setOrcamentoProcedimentoCollection(Collection<OrcamentoProcedimento> orcamentoProcedimentoCollection) {
        this.orcamentoProcedimentoCollection = orcamentoProcedimentoCollection;
    }

    @XmlTransient
    public Collection<HistoricoLaboratorio> getHistoricoLaboratorioCollection() {
        return historicoLaboratorioCollection;
    }

    public void setHistoricoLaboratorioCollection(Collection<HistoricoLaboratorio> historicoLaboratorioCollection) {
        this.historicoLaboratorioCollection = historicoLaboratorioCollection;
    }

    @XmlTransient
    public Collection<RegraEspecial> getRegraEspecialCollection() {
        return regraEspecialCollection;
    }

    public void setRegraEspecialCollection(Collection<RegraEspecial> regraEspecialCollection) {
        this.regraEspecialCollection = regraEspecialCollection;
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
        if (!(object instanceof Procedimento)) {
            return false;
        }
        Procedimento other = (Procedimento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.Procedimento[ id=" + id + " ]";
    }
    
}
