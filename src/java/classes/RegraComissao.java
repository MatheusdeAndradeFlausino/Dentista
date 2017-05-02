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
@Table(name = "REGRA_COMISSAO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegraComissao.findAll", query = "SELECT r FROM RegraComissao r"),
    @NamedQuery(name = "RegraComissao.findById", query = "SELECT r FROM RegraComissao r WHERE r.id = :id")})
public class RegraComissao implements Serializable {
    @OneToMany(mappedBy = "idRegraComissao")
    private Collection<Regra> regraCollection;
    @OneToMany(mappedBy = "idRegraComissao")
    private Collection<RegraEspecial> regraEspecialCollection;
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
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "idClinica", referencedColumnName = "id")
    @ManyToOne
    private Clinica idClinica;
    @OneToMany(mappedBy = "idRegraComissao")
    private Collection<Profissional> profissionalCollection;

    public RegraComissao() {
    }

    public RegraComissao(Integer id) {
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

    public Clinica getIdClinica() {
        return idClinica;
    }

    public void setIdClinica(Clinica idClinica) {
        this.idClinica = idClinica;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @XmlTransient
    public Collection<Profissional> getProfissionalCollection() {
        return profissionalCollection;
    }

    public void setProfissionalCollection(Collection<Profissional> profissionalCollection) {
        this.profissionalCollection = profissionalCollection;
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
        if (!(object instanceof RegraComissao)) {
            return false;
        }
        RegraComissao other = (RegraComissao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.RegraComissao[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Regra> getRegraCollection() {
        return regraCollection;
    }

    public void setRegraCollection(Collection<Regra> regraCollection) {
        this.regraCollection = regraCollection;
    }

    @XmlTransient
    public Collection<RegraEspecial> getRegraEspecialCollection() {
        return regraEspecialCollection;
    }

    public void setRegraEspecialCollection(Collection<RegraEspecial> regraEspecialCollection) {
        this.regraEspecialCollection = regraEspecialCollection;
    }
    
}
