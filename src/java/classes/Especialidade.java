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
import javax.persistence.Lob;
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
@Table(name = "ESPECIALIDADE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Especialidade.findAll", query = "SELECT e FROM Especialidade e"),
    @NamedQuery(name = "Especialidade.findById", query = "SELECT e FROM Especialidade e WHERE e.id = :id"),
    @NamedQuery(name = "Especialidade.findByTipoComissionamento", query = "SELECT e FROM Especialidade e WHERE e.tipoComissionamento = :tipoComissionamento"),
    @NamedQuery(name = "Especialidade.findByStatus", query = "SELECT e FROM Especialidade e WHERE e.status = :status")})
public class Especialidade implements Serializable {
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
    @Size(max = 50)
    @Column(name = "tipoComissionamento")
    private String tipoComissionamento;
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "idEspecialidade")
    private Collection<Regra> regraCollection;   
    @OneToMany(mappedBy = "idEspecialidade")
    private Collection<ContaAvulsa> contaAvulsaCollection;
    @OneToMany(mappedBy = "idEspecialidade")
    private Collection<Procedimento> procedimentoCollection;
    @OneToMany(mappedBy = "idEspecialidade")
    private Collection<RegraEspecial> regraEspecialCollection;
    @OneToMany(mappedBy = "idEspecialidade")
    private Collection<EspecialidadeClinica> especialidadeClinicaCollection;
    @OneToMany(mappedBy = "idEspecialidade")
    private Collection<Documentacao> documentacaoCollection;

    public Especialidade() {
    }

    public Especialidade(Integer id) {
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

    public String getTipoComissionamento() {
        return tipoComissionamento;
    }

    public void setTipoComissionamento(String tipoComissionamento) {
        this.tipoComissionamento = tipoComissionamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<Regra> getRegraCollection() {
        return regraCollection;
    }

    public void setRegraCollection(Collection<Regra> regraCollection) {
        this.regraCollection = regraCollection;
    }
    
    @XmlTransient
    public Collection<ContaAvulsa> getContaAvulsaCollection() {
        return contaAvulsaCollection;
    }

    public void setContaAvulsaCollection(Collection<ContaAvulsa> contaAvulsaCollection) {
        this.contaAvulsaCollection = contaAvulsaCollection;
    }

    @XmlTransient
    public Collection<Procedimento> getProcedimentoCollection() {
        return procedimentoCollection;
    }

    public void setProcedimentoCollection(Collection<Procedimento> procedimentoCollection) {
        this.procedimentoCollection = procedimentoCollection;
    }

    @XmlTransient
    public Collection<RegraEspecial> getRegraEspecialCollection() {
        return regraEspecialCollection;
    }

    public void setRegraEspecialCollection(Collection<RegraEspecial> regraEspecialCollection) {
        this.regraEspecialCollection = regraEspecialCollection;
    }

    @XmlTransient
    public Collection<EspecialidadeClinica> getEspecialidadeClinicaCollection() {
        return especialidadeClinicaCollection;
    }

    public void setEspecialidadeClinicaCollection(Collection<EspecialidadeClinica> especialidadeClinicaCollection) {
        this.especialidadeClinicaCollection = especialidadeClinicaCollection;
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
        if (!(object instanceof Especialidade)) {
            return false;
        }
        Especialidade other = (Especialidade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.Especialidade[ id=" + id + " ]";
    }
    
}
