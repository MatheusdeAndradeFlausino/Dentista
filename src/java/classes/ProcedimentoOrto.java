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
@Table(name = "PROCEDIMENTO_ORTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProcedimentoOrto.findAll", query = "SELECT p FROM ProcedimentoOrto p"),
    @NamedQuery(name = "ProcedimentoOrto.findById", query = "SELECT p FROM ProcedimentoOrto p WHERE p.id = :id"),
    @NamedQuery(name = "ProcedimentoOrto.findByVencimento", query = "SELECT p FROM ProcedimentoOrto p WHERE p.vencimento = :vencimento"),
    @NamedQuery(name = "ProcedimentoOrto.findByDataAtendimento", query = "SELECT p FROM ProcedimentoOrto p WHERE p.dataAtendimento = :dataAtendimento"),
    @NamedQuery(name = "ProcedimentoOrto.findByObservacao", query = "SELECT p FROM ProcedimentoOrto p WHERE p.observacao = :observacao")})
public class ProcedimentoOrto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "vencimento")
    @Temporal(TemporalType.DATE)
    private Date vencimento;
    @Column(name = "dataAtendimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtendimento;
    @Size(max = 20)
    @Column(name = "observacao")
    private String observacao;
    @JoinColumn(name = "idProcedimento", referencedColumnName = "id")
    @ManyToOne
    private Procedimento idProcedimento;
    @JoinColumn(name = "idOrto", referencedColumnName = "id")
    @ManyToOne
    private Orto idOrto;
    @JoinColumn(name = "idProfissional", referencedColumnName = "id")
    @ManyToOne
    private Profissional idProfissional;

    public ProcedimentoOrto() {
    }

    public ProcedimentoOrto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public Date getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(Date dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Procedimento getIdProcedimento() {
        return idProcedimento;
    }

    public void setIdProcedimento(Procedimento idProcedimento) {
        this.idProcedimento = idProcedimento;
    }

    public Orto getIdOrto() {
        return idOrto;
    }

    public void setIdOrto(Orto idOrto) {
        this.idOrto = idOrto;
    }

    public Profissional getIdProfissional() {
        return idProfissional;
    }

    public void setIdProfissional(Profissional idProfissional) {
        this.idProfissional = idProfissional;
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
        if (!(object instanceof ProcedimentoOrto)) {
            return false;
        }
        ProcedimentoOrto other = (ProcedimentoOrto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.ProcedimentoOrto[ id=" + id + " ]";
    }
    
}
