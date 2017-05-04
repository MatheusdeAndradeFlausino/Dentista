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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valderlei
 */
@Entity
@Table(name = "FILA_ESPERA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FilaEspera.findAll", query = "SELECT f FROM FilaEspera f"),
    @NamedQuery(name = "FilaEspera.findById", query = "SELECT f FROM FilaEspera f WHERE f.id = :id"),
    @NamedQuery(name = "FilaEspera.findByDataChegada", query = "SELECT f FROM FilaEspera f WHERE f.dataChegada = :dataChegada")})
public class FilaEspera implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "dataChegada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataChegada;
    @JoinColumn(name = "idPaciente", referencedColumnName = "id")
    @ManyToOne
    private Paciente idPaciente;
    @JoinColumn(name = "idProfissional", referencedColumnName = "id")
    @ManyToOne
    private Profissional idProfissional;
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    @Column(name = "tempoEspera")
    private Integer tempoEspera;
    @Column(name = "tempoAtendimento")
    private Integer tempoAtendimento;

    public FilaEspera() {
    }

    public FilaEspera(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(Date dataChegada) {
        this.dataChegada = dataChegada;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTempoEspera() {
        return tempoEspera;
    }

    public void setTempoEspera(Integer tempoEspera) {
        this.tempoEspera = tempoEspera;
    }

    public Integer getTempoAtendimento() {
        return tempoAtendimento;
    }

    public void setTempoAtendimento(Integer tempoAtendimento) {
        this.tempoAtendimento = tempoAtendimento;
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
        if (!(object instanceof FilaEspera)) {
            return false;
        }
        FilaEspera other = (FilaEspera) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.FilaEspera[ id=" + id + " ]";
    }
    
}
