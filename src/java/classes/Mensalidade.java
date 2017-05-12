/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author valderlei
 */
@Entity
@Table(name = "MENSALIDADE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mensalidade.findAll", query = "SELECT m FROM Mensalidade m"),
    @NamedQuery(name = "Mensalidade.findById", query = "SELECT m FROM Mensalidade m WHERE m.id = :id"),
    @NamedQuery(name = "Mensalidade.findByValor", query = "SELECT m FROM Mensalidade m WHERE m.valor = :valor"),
    @NamedQuery(name = "Mensalidade.findByDataVencimento", query = "SELECT m FROM Mensalidade m WHERE m.dataVencimento = :dataVencimento"),
    @NamedQuery(name = "Mensalidade.findByQuantidadeMeses", query = "SELECT m FROM Mensalidade m WHERE m.quantidadeMeses = :quantidadeMeses")})
public class Mensalidade implements Serializable {
    @OneToMany(mappedBy = "idMensalidade")
    private Collection<ParcelaMensalidade> parcelaMensalidadeCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Column(name = "dataVencimento")
    @Temporal(TemporalType.DATE)
    private Date dataVencimento;
    @Column(name = "quantidadeMeses")
    private Integer quantidadeMeses;
    @JoinColumn(name = "idPaciente", referencedColumnName = "id")
    @ManyToOne
    private Paciente idPaciente;
    @JoinColumn(name = "idMotivo", referencedColumnName = "id")
    @ManyToOne
    private Motivo idMotivo;
    @Column(name = "quantidadeMesesPagos")
    private Integer quantidadeMesesPagos;
    @Size(max = 20)
    @Column(name = "status")
    private String status;

    public Mensalidade() {
    }

    public Mensalidade(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Integer getQuantidadeMeses() {
        return quantidadeMeses;
    }

    public void setQuantidadeMeses(Integer quantidadeMeses) {
        this.quantidadeMeses = quantidadeMeses;
    }

    public Paciente getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Paciente idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Integer getQuantidadeMesesPagos() {
        return quantidadeMesesPagos;
    }

    public void setQuantidadeMesesPagos(Integer quantidadeMesesPagos) {
        this.quantidadeMesesPagos = quantidadeMesesPagos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Motivo getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(Motivo idMotivo) {
        this.idMotivo = idMotivo;
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
        if (!(object instanceof Mensalidade)) {
            return false;
        }
        Mensalidade other = (Mensalidade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.Mensalidade[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<ParcelaMensalidade> getParcelaMensalidadeCollection() {
        return parcelaMensalidadeCollection;
    }

    public void setParcelaMensalidadeCollection(Collection<ParcelaMensalidade> parcelaMensalidadeCollection) {
        this.parcelaMensalidadeCollection = parcelaMensalidadeCollection;
    }
    
}
