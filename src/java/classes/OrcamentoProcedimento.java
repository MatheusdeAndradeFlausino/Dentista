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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author valderlei
 */
@Entity
@Table(name = "ORCAMENTO_PROCEDIMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrcamentoProcedimento.findAll", query = "SELECT o FROM OrcamentoProcedimento o"),
    @NamedQuery(name = "OrcamentoProcedimento.findById", query = "SELECT o FROM OrcamentoProcedimento o WHERE o.id = :id")})
public class OrcamentoProcedimento implements Serializable {
    @OneToMany(mappedBy = "idProcedimentoOrcamento")
    private Collection<DenticaoOrcamento> denticaoOrcamentoCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "idOrcamento", referencedColumnName = "id")
    @ManyToOne
    private Orcamento idOrcamento;
    @JoinColumn(name = "idProcedimento", referencedColumnName = "id")
    @ManyToOne
    private Procedimento idProcedimento;
    @Column(name = "quantidade")
    private Integer quantidade;
    @Column(name = "desconto")
    private Double desconto;
    @Column(name = "subTotal")
    private Double subTotal;
    @Column(name = "valor")
    private Double valor;

    public OrcamentoProcedimento() {
    }

    public OrcamentoProcedimento(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Orcamento getIdOrcamento() {
        return idOrcamento;
    }

    public void setIdOrcamento(Orcamento idOrcamento) {
        this.idOrcamento = idOrcamento;
    }

    public Procedimento getIdProcedimento() {
        return idProcedimento;
    }

    public void setIdProcedimento(Procedimento idProcedimento) {
        this.idProcedimento = idProcedimento;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
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
        if (!(object instanceof OrcamentoProcedimento)) {
            return false;
        }
        OrcamentoProcedimento other = (OrcamentoProcedimento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.OrcamentoProcedimento[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<DenticaoOrcamento> getDenticaoOrcamentoCollection() {
        return denticaoOrcamentoCollection;
    }

    public void setDenticaoOrcamentoCollection(Collection<DenticaoOrcamento> denticaoOrcamentoCollection) {
        this.denticaoOrcamentoCollection = denticaoOrcamentoCollection;
    }
    
}
