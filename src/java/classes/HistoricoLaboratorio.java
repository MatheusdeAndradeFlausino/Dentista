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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valderlei
 */
@Entity
@Table(name = "HISTORICO_LABORATORIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistoricoLaboratorio.findAll", query = "SELECT h FROM HistoricoLaboratorio h"),
    @NamedQuery(name = "HistoricoLaboratorio.findById", query = "SELECT h FROM HistoricoLaboratorio h WHERE h.id = :id")})
public class HistoricoLaboratorio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "idPaciente", referencedColumnName = "id")
    @ManyToOne
    private Paciente idPaciente;
    @JoinColumn(name = "idProcedimento", referencedColumnName = "id")
    @ManyToOne
    private Procedimento idProcedimento;

    public HistoricoLaboratorio() {
    }

    public HistoricoLaboratorio(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Paciente getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Paciente idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Procedimento getIdProcedimento() {
        return idProcedimento;
    }

    public void setIdProcedimento(Procedimento idProcedimento) {
        this.idProcedimento = idProcedimento;
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
        if (!(object instanceof HistoricoLaboratorio)) {
            return false;
        }
        HistoricoLaboratorio other = (HistoricoLaboratorio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.HistoricoLaboratorio[ id=" + id + " ]";
    }
    
}
