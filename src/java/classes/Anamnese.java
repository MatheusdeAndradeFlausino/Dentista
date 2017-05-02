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
import javax.persistence.Lob;
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
@Table(name = "ANAMNESE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Anamnese.findAll", query = "SELECT a FROM Anamnese a"),
    @NamedQuery(name = "Anamnese.findById", query = "SELECT a FROM Anamnese a WHERE a.id = :id"),
    @NamedQuery(name = "Anamnese.findByIsHemorragia", query = "SELECT a FROM Anamnese a WHERE a.isHemorragia = :isHemorragia"),
    @NamedQuery(name = "Anamnese.findByIsDiabetico", query = "SELECT a FROM Anamnese a WHERE a.isDiabetico = :isDiabetico"),
    @NamedQuery(name = "Anamnese.findByIsPressaoAlta", query = "SELECT a FROM Anamnese a WHERE a.isPressaoAlta = :isPressaoAlta"),
    @NamedQuery(name = "Anamnese.findByIsCardiaco", query = "SELECT a FROM Anamnese a WHERE a.isCardiaco = :isCardiaco"),
    @NamedQuery(name = "Anamnese.findByIsGestante", query = "SELECT a FROM Anamnese a WHERE a.isGestante = :isGestante"),
    @NamedQuery(name = "Anamnese.findByIsPressaoBaixa", query = "SELECT a FROM Anamnese a WHERE a.isPressaoBaixa = :isPressaoBaixa"),
    @NamedQuery(name = "Anamnese.findByStatus", query = "SELECT a FROM Anamnese a WHERE a.status = :status")})
public class Anamnese implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 3)
    @Column(name = "isHemorragia")
    private String isHemorragia;
    @Size(max = 3)
    @Column(name = "isDiabetico")
    private String isDiabetico;
    @Size(max = 3)
    @Column(name = "isPressaoAlta")
    private String isPressaoAlta;
    @Size(max = 3)
    @Column(name = "isCardiaco")
    private String isCardiaco;
    @Size(max = 3)
    @Column(name = "isGestante")
    private String isGestante;
    @Size(max = 3)
    @Column(name = "isPressaoBaixa")
    private String isPressaoBaixa;
    @Lob
    @Size(max = 65535)
    @Column(name = "observacao")
    private String observacao;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
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

    public Anamnese() {
    }

    public Anamnese(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsHemorragia() {
        return isHemorragia;
    }

    public void setIsHemorragia(String isHemorragia) {
        this.isHemorragia = isHemorragia;
    }

    public String getIsDiabetico() {
        return isDiabetico;
    }

    public void setIsDiabetico(String isDiabetico) {
        this.isDiabetico = isDiabetico;
    }

    public String getIsPressaoAlta() {
        return isPressaoAlta;
    }

    public void setIsPressaoAlta(String isPressaoAlta) {
        this.isPressaoAlta = isPressaoAlta;
    }

    public String getIsCardiaco() {
        return isCardiaco;
    }

    public void setIsCardiaco(String isCardiaco) {
        this.isCardiaco = isCardiaco;
    }

    public String getIsGestante() {
        return isGestante;
    }

    public void setIsGestante(String isGestante) {
        this.isGestante = isGestante;
    }

    public String getIsPressaoBaixa() {
        return isPressaoBaixa;
    }

    public void setIsPressaoBaixa(String isPressaoBaixa) {
        this.isPressaoBaixa = isPressaoBaixa;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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
        if (!(object instanceof Anamnese)) {
            return false;
        }
        Anamnese other = (Anamnese) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.Anamnese[ id=" + id + " ]";
    }
    
}
