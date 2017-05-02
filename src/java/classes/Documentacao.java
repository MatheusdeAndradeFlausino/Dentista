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
@Table(name = "DOCUMENTACAO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Documentacao.findAll", query = "SELECT d FROM Documentacao d"),
    @NamedQuery(name = "Documentacao.findById", query = "SELECT d FROM Documentacao d WHERE d.id = :id"),
    @NamedQuery(name = "Documentacao.findByData", query = "SELECT d FROM Documentacao d WHERE d.data = :data"),
    @NamedQuery(name = "Documentacao.findByValor", query = "SELECT d FROM Documentacao d WHERE d.valor = :valor"),
    @NamedQuery(name = "Documentacao.findByIsGerarIndicacao", query = "SELECT d FROM Documentacao d WHERE d.isGerarIndicacao = :isGerarIndicacao")})
public class Documentacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Size(max = 3)
    @Column(name = "isGerarIndicacao")
    private String isGerarIndicacao;
    @JoinColumn(name = "idPaciente", referencedColumnName = "id")
    @ManyToOne
    private Paciente idPaciente;
    @JoinColumn(name = "idEspecialidade", referencedColumnName = "id")
    @ManyToOne
    private Especialidade idEspecialidade;
    @JoinColumn(name = "idFormaPagamento", referencedColumnName = "id")
    @ManyToOne
    private FormaPagamento idFormaPagamento;

    public Documentacao() {
    }

    public Documentacao(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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

    public Paciente getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Paciente idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Especialidade getIdEspecialidade() {
        return idEspecialidade;
    }

    public void setIdEspecialidade(Especialidade idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }

    public FormaPagamento getIdFormaPagamento() {
        return idFormaPagamento;
    }

    public void setIdFormaPagamento(FormaPagamento idFormaPagamento) {
        this.idFormaPagamento = idFormaPagamento;
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
        if (!(object instanceof Documentacao)) {
            return false;
        }
        Documentacao other = (Documentacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.Documentacao[ id=" + id + " ]";
    }
    
}
