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
@Table(name = "CONTA_AVULSA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContaAvulsa.findAll", query = "SELECT c FROM ContaAvulsa c"),
    @NamedQuery(name = "ContaAvulsa.findById", query = "SELECT c FROM ContaAvulsa c WHERE c.id = :id"),
    @NamedQuery(name = "ContaAvulsa.findByValor", query = "SELECT c FROM ContaAvulsa c WHERE c.valor = :valor"),
    @NamedQuery(name = "ContaAvulsa.findByIsGerarIndicacao", query = "SELECT c FROM ContaAvulsa c WHERE c.isGerarIndicacao = :isGerarIndicacao")})
public class ContaAvulsa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Size(max = 2)
    @Column(name = "isGerarIndicacao")
    private String isGerarIndicacao;
    @JoinColumn(name = "idProcedimento", referencedColumnName = "id")
    @ManyToOne
    private Procedimento idProcedimento;
    @JoinColumn(name = "idEspecialidade", referencedColumnName = "id")
    @ManyToOne
    private Especialidade idEspecialidade;
    @JoinColumn(name = "idPaciente", referencedColumnName = "id")
    @ManyToOne
    private Paciente idPaciente;

    public ContaAvulsa() {
    }

    public ContaAvulsa(Integer id) {
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

    public String getIsGerarIndicacao() {
        return isGerarIndicacao;
    }

    public void setIsGerarIndicacao(String isGerarIndicacao) {
        this.isGerarIndicacao = isGerarIndicacao;
    }

    public Procedimento getIdProcedimento() {
        return idProcedimento;
    }

    public void setIdProcedimento(Procedimento idProcedimento) {
        this.idProcedimento = idProcedimento;
    }

    public Especialidade getIdEspecialidade() {
        return idEspecialidade;
    }

    public void setIdEspecialidade(Especialidade idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
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
        if (!(object instanceof ContaAvulsa)) {
            return false;
        }
        ContaAvulsa other = (ContaAvulsa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.ContaAvulsa[ id=" + id + " ]";
    }
    
}
