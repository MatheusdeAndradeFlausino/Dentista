/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valderlei
 */
@Entity
@Table(name = "PARCELA_MENSALIDADE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParcelaMensalidade.findAll", query = "SELECT p FROM ParcelaMensalidade p"),
    @NamedQuery(name = "ParcelaMensalidade.findById", query = "SELECT p FROM ParcelaMensalidade p WHERE p.id = :id"),
    @NamedQuery(name = "ParcelaMensalidade.findByDataVencimento", query = "SELECT p FROM ParcelaMensalidade p WHERE p.dataVencimento = :dataVencimento"),
    @NamedQuery(name = "ParcelaMensalidade.findByValorPago", query = "SELECT p FROM ParcelaMensalidade p WHERE p.valorPago = :valorPago"),
    @NamedQuery(name = "ParcelaMensalidade.findByPago", query = "SELECT p FROM ParcelaMensalidade p WHERE p.pago = :pago")})
public class ParcelaMensalidade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "dataVencimento")
    @Temporal(TemporalType.DATE)
    private Date dataVencimento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorPago")
    private Double valorPago;
    @Size(max = 3)
    @Column(name = "pago")
    private String pago;
    @JoinColumn(name = "idMensalidade", referencedColumnName = "id")
    @ManyToOne
    private Mensalidade idMensalidade;
    @Transient
    private Double valorAReceber;

    @Transient
    public Double getValorAReceber() {
        return idMensalidade.getValor() - valorPago;
    }

    public void setValorAReceber(Double valorAReceber) {
        this.valorAReceber = valorAReceber;
    }

    public ParcelaMensalidade() {
    }

    public ParcelaMensalidade(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public Mensalidade getIdMensalidade() {
        return idMensalidade;
    }

    public void setIdMensalidade(Mensalidade idMensalidade) {
        this.idMensalidade = idMensalidade;
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
        if (!(object instanceof ParcelaMensalidade)) {
            return false;
        }
        ParcelaMensalidade other = (ParcelaMensalidade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.ParcelaMensalidade[ id=" + id + " ]";
    }
    
}
