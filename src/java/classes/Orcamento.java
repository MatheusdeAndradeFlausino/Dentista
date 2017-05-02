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
@Table(name = "ORCAMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orcamento.findAll", query = "SELECT o FROM Orcamento o"),
    @NamedQuery(name = "Orcamento.findById", query = "SELECT o FROM Orcamento o WHERE o.id = :id"),
    @NamedQuery(name = "Orcamento.findByIsEnviarSMS", query = "SELECT o FROM Orcamento o WHERE o.isEnviarSMS = :isEnviarSMS"),
    @NamedQuery(name = "Orcamento.findByData", query = "SELECT o FROM Orcamento o WHERE o.data = :data"),
    @NamedQuery(name = "Orcamento.findByStatus", query = "SELECT o FROM Orcamento o WHERE o.status = :status")})
public class Orcamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 3)
    @Column(name = "isEnviarSMS")
    private String isEnviarSMS;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "idPaciente", referencedColumnName = "id")
    @ManyToOne
    private Paciente idPaciente;
    @JoinColumn(name = "idProfissional", referencedColumnName = "id")
    @ManyToOne
    private Profissional idProfissional;
    @OneToMany(mappedBy = "idOrcamento")
    private Collection<OrcamentoProcedimento> orcamentoProcedimentoCollection;

    public Orcamento() {
    }

    public Orcamento(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsEnviarSMS() {
        return isEnviarSMS;
    }

    public void setIsEnviarSMS(String isEnviarSMS) {
        this.isEnviarSMS = isEnviarSMS;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Paciente getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Paciente idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Profissional getIdProfissional() {
        return idProfissional;
    }

    public void setIdProfissional(Profissional idProfissional) {
        this.idProfissional = idProfissional;
    }

    @XmlTransient
    public Collection<OrcamentoProcedimento> getOrcamentoProcedimentoCollection() {
        return orcamentoProcedimentoCollection;
    }

    public void setOrcamentoProcedimentoCollection(Collection<OrcamentoProcedimento> orcamentoProcedimentoCollection) {
        this.orcamentoProcedimentoCollection = orcamentoProcedimentoCollection;
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
        if (!(object instanceof Orcamento)) {
            return false;
        }
        Orcamento other = (Orcamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.Orcamento[ id=" + id + " ]";
    }
    
}
