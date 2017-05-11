/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import enums.TipoConta;
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
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valderlei
 */
@Entity
@Table(name = "CONTAS_PAGAR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContasPagar.findAll", query = "SELECT c FROM ContasPagar c"),
    @NamedQuery(name = "ContasPagar.findById", query = "SELECT c FROM ContasPagar c WHERE c.id = :id"),
    @NamedQuery(name = "ContasPagar.findByDataVencimento", query = "SELECT c FROM ContasPagar c WHERE c.dataVencimento = :dataVencimento"),
    @NamedQuery(name = "ContasPagar.findByTipoConta", query = "SELECT c FROM ContasPagar c WHERE c.tipoConta = :tipoConta"),
    @NamedQuery(name = "ContasPagar.findByValor", query = "SELECT c FROM ContasPagar c WHERE c.valor = :valor"),
    @NamedQuery(name = "ContasPagar.findByNumDocumento", query = "SELECT c FROM ContasPagar c WHERE c.numDocumento = :numDocumento")})
public class ContasPagar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "dataVencimento")
    @Temporal(TemporalType.DATE)
    private Date dataVencimento;
    @Size(max = 20)
    @Column(name = "tipoConta")
    private String tipoConta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Column(name = "numDocumento")
    private Integer numDocumento;
    @Lob
    @Size(max = 65535)
    @Column(name = "observacao")
    private String observacao;
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "idClinica", referencedColumnName = "id")
    @ManyToOne
    private Clinica idClinica;
    @JoinColumn(name = "idProfissional", referencedColumnName = "id")
    @ManyToOne
    private Profissional idProfissional;
    @JoinColumn(name = "idFornecedor", referencedColumnName = "id")
    @ManyToOne
    private Fornecedor idFornecedor;
    @JoinColumn(name = "idCentroCusto", referencedColumnName = "id")
    @ManyToOne
    private CentroCusto idCentroCusto;

    public ContasPagar() {
    }

    public ContasPagar(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(Integer numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Clinica getIdClinica() {
        return idClinica;
    }

    public void setIdClinica(Clinica idClinica) {
        this.idClinica = idClinica;
    }

    public Profissional getIdProfissional() {
        return idProfissional;
    }

    public void setIdProfissional(Profissional idProfissional) {
        this.idProfissional = idProfissional;
    }

    public Fornecedor getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Fornecedor idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public CentroCusto getIdCentroCusto() {
        return idCentroCusto;
    }

    public void setIdCentroCusto(CentroCusto idCentroCusto) {
        this.idCentroCusto = idCentroCusto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Transient
    public String getNome() {
        if (this.tipoConta.equals(TipoConta.FORNECEDOR.getTipo())) {
            if (getIdFornecedor()!= null) {
                return this.getIdFornecedor().getNome();
            } else {
                return null;
            }
        } else {
            if (getIdProfissional() != null) {
                return this.getIdProfissional().getIdPessoa().getNome();
            } else {
                return null;
            }
        }
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
        if (!(object instanceof ContasPagar)) {
            return false;
        }
        ContasPagar other = (ContasPagar) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.ContasPagar[ id=" + id + " ]";
    }
    
}
