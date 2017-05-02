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
import javax.persistence.Lob;
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
@Table(name = "BANDEIRA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bandeira.findAll", query = "SELECT b FROM Bandeira b"),
    @NamedQuery(name = "Bandeira.findById", query = "SELECT b FROM Bandeira b WHERE b.id = :id"),
    @NamedQuery(name = "Bandeira.findByBandeira", query = "SELECT b FROM Bandeira b WHERE b.bandeira = :bandeira"),
    @NamedQuery(name = "Bandeira.findByTipoCartao", query = "SELECT b FROM Bandeira b WHERE b.tipoCartao = :tipoCartao"),
    @NamedQuery(name = "Bandeira.findByTaxa", query = "SELECT b FROM Bandeira b WHERE b.taxa = :taxa"),
    @NamedQuery(name = "Bandeira.findByDia", query = "SELECT b FROM Bandeira b WHERE b.dia = :dia")})
public class Bandeira implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "bandeira")
    private String bandeira;
    @Size(max = 20)
    @Column(name = "tipoCartao")
    private String tipoCartao;
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "taxa")
    private Double taxa;
    @Column(name = "dia")
    private Integer dia;
    @Lob
    @Size(max = 65535)
    @Column(name = "descricao")
    private String descricao;

    public Bandeira() {
    }

    public Bandeira(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public String getTipoCartao() {
        return tipoCartao;
    }

    public void setTipoCartao(String tipoCartao) {
        this.tipoCartao = tipoCartao;
    }

    public Double getTaxa() {
        return taxa;
    }

    public void setTaxa(Double taxa) {
        this.taxa = taxa;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        if (!(object instanceof Bandeira)) {
            return false;
        }
        Bandeira other = (Bandeira) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.Bandeira[ id=" + id + " ]";
    }
    
}
