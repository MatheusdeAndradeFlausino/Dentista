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
@Table(name = "REGRA_ESPECIAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegraEspecial.findAll", query = "SELECT r FROM RegraEspecial r"),
    @NamedQuery(name = "RegraEspecial.findById", query = "SELECT r FROM RegraEspecial r WHERE r.id = :id"),
    @NamedQuery(name = "RegraEspecial.findByTipoComissao", query = "SELECT r FROM RegraEspecial r WHERE r.tipoComissao = :tipoComissao"),
    @NamedQuery(name = "RegraEspecial.findByValor", query = "SELECT r FROM RegraEspecial r WHERE r.valor = :valor")})
public class RegraEspecial implements Serializable {
    @JoinColumn(name = "idRegraComissao", referencedColumnName = "id")
    @ManyToOne
    private RegraComissao idRegraComissao;
    @JoinColumn(name = "idTabela", referencedColumnName = "id")
    @ManyToOne
    private TabelaProcedimento idTabela;
    @JoinColumn(name = "idProcedimento", referencedColumnName = "id")
    @ManyToOne
    private Procedimento idProcedimento;
    @JoinColumn(name = "idEspecialidade", referencedColumnName = "id")
    @ManyToOne
    private Especialidade idEspecialidade;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "tipoComissao")
    private String tipoComissao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;

    public RegraEspecial() {
    }

    public RegraEspecial(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoComissao() {
        return tipoComissao;
    }

    public void setTipoComissao(String tipoComissao) {
        this.tipoComissao = tipoComissao;
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
        if (!(object instanceof RegraEspecial)) {
            return false;
        }
        RegraEspecial other = (RegraEspecial) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.RegraEspecial[ id=" + id + " ]";
    }

    public RegraComissao getIdRegraComissao() {
        return idRegraComissao;
    }

    public void setIdRegraComissao(RegraComissao idRegraComissao) {
        this.idRegraComissao = idRegraComissao;
    }

    public TabelaProcedimento getIdTabela() {
        return idTabela;
    }

    public void setIdTabela(TabelaProcedimento idTabela) {
        this.idTabela = idTabela;
    }

    public Procedimento getIdProcedimento() {
        return idProcedimento;
    }

    public void setIdProcedimento(Procedimento idProcedimento) {
        this.idProcedimento = idProcedimento;
    }

    public Especialidade getIdEspecialidade() {
        return idEspecialidade;
    }

    public void setIdEspecialidade(Especialidade idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }
    
}
