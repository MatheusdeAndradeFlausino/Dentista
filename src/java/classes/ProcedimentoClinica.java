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
@Table(name = "PROCEDIMENTO_CLINICA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProcedimentoClinica.findAll", query = "SELECT p FROM ProcedimentoClinica p"),
    @NamedQuery(name = "ProcedimentoClinica.findById", query = "SELECT p FROM ProcedimentoClinica p WHERE p.id = :id")})
public class ProcedimentoClinica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "idProcedimento", referencedColumnName = "id")
    @ManyToOne
    private Procedimento idProcedimento;
    @JoinColumn(name = "idClinica", referencedColumnName = "id")
    @ManyToOne
    private Clinica idClinica;
    @Column(name = "valor")
    private Double valor;


    public ProcedimentoClinica() {
    }

    public ProcedimentoClinica(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Procedimento getIdProcedimento() {
        return idProcedimento;
    }

    public void setIdProcedimento(Procedimento idProcedimento) {
        this.idProcedimento = idProcedimento;
    }

    public Clinica getIdClinica() {
        return idClinica;
    }

    public void setIdClinica(Clinica idClinica) {
        this.idClinica = idClinica;
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
        if (!(object instanceof ProcedimentoClinica)) {
            return false;
        }
        ProcedimentoClinica other = (ProcedimentoClinica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.ProcedimentoClinica[ id=" + id + " ]";
    }
    
}
