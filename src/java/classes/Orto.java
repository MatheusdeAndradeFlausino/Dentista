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
@Table(name = "ORTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orto.findAll", query = "SELECT o FROM Orto o"),
    @NamedQuery(name = "Orto.findById", query = "SELECT o FROM Orto o WHERE o.id = :id"),
    @NamedQuery(name = "Orto.findByDataInicio", query = "SELECT o FROM Orto o WHERE o.dataInicio = :dataInicio"),
    @NamedQuery(name = "Orto.findByDataFinal", query = "SELECT o FROM Orto o WHERE o.dataFinal = :dataFinal"),
    @NamedQuery(name = "Orto.findByStatus", query = "SELECT o FROM Orto o WHERE o.status = :status")})
public class Orto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "dataInicio")
    @Temporal(TemporalType.DATE)
    private Date dataInicio;
    @Column(name = "dataFinal")
    @Temporal(TemporalType.DATE)
    private Date dataFinal;
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "idOrto")
    private Collection<ProcedimentoOrto> procedimentoOrtoCollection;
    @JoinColumn(name = "idPaciente", referencedColumnName = "id")
    @ManyToOne
    private Paciente idPaciente;

    public Orto() {
    }

    public Orto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<ProcedimentoOrto> getProcedimentoOrtoCollection() {
        return procedimentoOrtoCollection;
    }

    public void setProcedimentoOrtoCollection(Collection<ProcedimentoOrto> procedimentoOrtoCollection) {
        this.procedimentoOrtoCollection = procedimentoOrtoCollection;
    }

    public Paciente getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Paciente idPaciente) {
        this.idPaciente = idPaciente;
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
        if (!(object instanceof Orto)) {
            return false;
        }
        Orto other = (Orto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.Orto[ id=" + id + " ]";
    }
    
}
