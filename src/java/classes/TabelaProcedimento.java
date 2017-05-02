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
@Table(name = "TABELA_PROCEDIMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TabelaProcedimento.findAll", query = "SELECT t FROM TabelaProcedimento t"),
    @NamedQuery(name = "TabelaProcedimento.findById", query = "SELECT t FROM TabelaProcedimento t WHERE t.id = :id"),
    @NamedQuery(name = "TabelaProcedimento.findByIsConvenio", query = "SELECT t FROM TabelaProcedimento t WHERE t.isConvenio = :isConvenio"),
    @NamedQuery(name = "TabelaProcedimento.findByStatus", query = "SELECT t FROM TabelaProcedimento t WHERE t.status = :status")})
public class TabelaProcedimento implements Serializable {
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
    @Size(max = 3)
    @Column(name = "isConvenio")
    private String isConvenio;
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "idTabela")
    private Collection<Procedimento> procedimentoCollection;
    @OneToMany(mappedBy = "idTabela")
    private Collection<RegraEspecial> regraEspecialCollection;

    public TabelaProcedimento() {
    }

    public TabelaProcedimento(Integer id) {
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

    public String getIsConvenio() {
        return isConvenio;
    }

    public void setIsConvenio(String isConvenio) {
        this.isConvenio = isConvenio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TabelaProcedimento)) {
            return false;
        }
        TabelaProcedimento other = (TabelaProcedimento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.TabelaProcedimento[ id=" + id + " ]";
    }
    
}
