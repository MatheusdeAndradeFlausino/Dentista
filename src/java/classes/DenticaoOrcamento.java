/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valderlei
 */
@Entity
@Table(name = "DENTICAO_ORCAMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DenticaoOrcamento.findAll", query = "SELECT d FROM DenticaoOrcamento d"),
    @NamedQuery(name = "DenticaoOrcamento.findById", query = "SELECT d FROM DenticaoOrcamento d WHERE d.id = :id"),
    @NamedQuery(name = "DenticaoOrcamento.findByNome", query = "SELECT d FROM DenticaoOrcamento d WHERE d.nome = :nome")})
public class DenticaoOrcamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 10)
    @Column(name = "nome")
    private String nome;
    @JoinColumn(name = "idProcedimentoOrcamento", referencedColumnName = "id")
    @ManyToOne
    private OrcamentoProcedimento idProcedimentoOrcamento;

    public DenticaoOrcamento() {
    }

    public DenticaoOrcamento(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public OrcamentoProcedimento getIdProcedimentoOrcamento() {
        return idProcedimentoOrcamento;
    }

    public void setIdProcedimentoOrcamento(OrcamentoProcedimento idProcedimentoOrcamento) {
        this.idProcedimentoOrcamento = idProcedimentoOrcamento;
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
        if (!(object instanceof DenticaoOrcamento)) {
            return false;
        }
        DenticaoOrcamento other = (DenticaoOrcamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.DenticaoOrcamento[ id=" + id + " ]";
    }
    
}
