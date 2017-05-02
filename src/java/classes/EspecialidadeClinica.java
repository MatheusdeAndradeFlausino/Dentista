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
@Table(name = "ESPECIALIDADE_CLINICA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EspecialidadeClinica.findAll", query = "SELECT e FROM EspecialidadeClinica e"),
    @NamedQuery(name = "EspecialidadeClinica.findById", query = "SELECT e FROM EspecialidadeClinica e WHERE e.id = :id")})
public class EspecialidadeClinica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "idEspecialidade", referencedColumnName = "id")
    @ManyToOne
    private Especialidade idEspecialidade;
    @JoinColumn(name = "idClinica", referencedColumnName = "id")
    @ManyToOne
    private Clinica idClinica;
    @Size(max = 20)
    @Column(name = "tipoComissionamento")
    private String tipoComissionamento;
    @Size(max = 20)
    @Column(name = "aberturaFuncionario")
    private String aberturaFuncionario;


    public EspecialidadeClinica() {
    }

    public EspecialidadeClinica(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Especialidade getIdEspecialidade() {
        return idEspecialidade;
    }

    public void setIdEspecialidade(Especialidade idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }

    public Clinica getIdClinica() {
        return idClinica;
    }

    public void setIdClinica(Clinica idClinica) {
        this.idClinica = idClinica;
    }
    
    public String getTipoComissionamento() {
        return tipoComissionamento;
    }

    public void setTipoComissionamento(String tipoComissionamento) {
        this.tipoComissionamento = tipoComissionamento;
    }

    public String getAberturaFuncionario() {
        return aberturaFuncionario;
    }

    public void setAberturaFuncionario(String aberturaFuncionario) {
        this.aberturaFuncionario = aberturaFuncionario;
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
        if (!(object instanceof EspecialidadeClinica)) {
            return false;
        }
        EspecialidadeClinica other = (EspecialidadeClinica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.EspecialidadeClinica[ id=" + id + " ]";
    }
    
}
