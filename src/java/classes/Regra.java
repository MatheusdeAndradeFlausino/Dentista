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
@Table(name = "REGRA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Regra.findAll", query = "SELECT r FROM Regra r"),
    @NamedQuery(name = "Regra.findById", query = "SELECT r FROM Regra r WHERE r.id = :id"),
    @NamedQuery(name = "Regra.findByPorcentagem", query = "SELECT r FROM Regra r WHERE r.porcentagem = :porcentagem"),
    @NamedQuery(name = "Regra.findByIsTotalBruto", query = "SELECT r FROM Regra r WHERE r.isTotalBruto = :isTotalBruto")})
public class Regra implements Serializable {
    @JoinColumn(name = "idRegraComissao", referencedColumnName = "id")
    @ManyToOne
    private RegraComissao idRegraComissao;
    @JoinColumn(name = "idEspecialidade", referencedColumnName = "id")
    @ManyToOne
    private Especialidade idEspecialidade;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "porcentagem")
    private Double porcentagem;
    @Size(max = 3)
    @Column(name = "isTotalBruto")
    private String isTotalBruto;

    public Regra() {
    }

    public Regra(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(Double porcentagem) {
        this.porcentagem = porcentagem;
    }

    public String getIsTotalBruto() {
        return isTotalBruto;
    }

    public void setIsTotalBruto(String isTotalBruto) {
        this.isTotalBruto = isTotalBruto;
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
        if (!(object instanceof Regra)) {
            return false;
        }
        Regra other = (Regra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.Regra[ id=" + id + " ]";
    }

    public RegraComissao getIdRegraComissao() {
        return idRegraComissao;
    }

    public void setIdRegraComissao(RegraComissao idRegraComissao) {
        this.idRegraComissao = idRegraComissao;
    }

    public Especialidade getIdEspecialidade() {
        return idEspecialidade;
    }

    public void setIdEspecialidade(Especialidade idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }
    
}
