/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import enums.Despesa;
import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author valderlei
 */
@Entity
@Table(name = "FORNECEDOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fornecedor.findAll", query = "SELECT f FROM Fornecedor f"),
    @NamedQuery(name = "Fornecedor.findById", query = "SELECT f FROM Fornecedor f WHERE f.id = :id"),
    @NamedQuery(name = "Fornecedor.findByTipoDespesa", query = "SELECT f FROM Fornecedor f WHERE f.tipoDespesa = :tipoDespesa"),
    @NamedQuery(name = "Fornecedor.findByStatus", query = "SELECT f FROM Fornecedor f WHERE f.status = :status")})
public class Fornecedor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "tipoDespesa")
    private String tipoDespesa;
    @Lob
    @Size(max = 65535)
    @Column(name = "descricao")
    private String descricao;
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "idFornecedor")
    private Collection<ContasPagar> contasPagarCollection;
    @JoinColumn(name = "idPessoa", referencedColumnName = "id")
    @ManyToOne
    private Pessoa idPessoa;
    @JoinColumn(name = "idEmpresa", referencedColumnName = "id")
    @ManyToOne
    private Empresa idEmpresa;
    @Transient
    private String nome;

    public Fornecedor() {
    }

    public Fornecedor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoDespesa() {
        return tipoDespesa;
    }

    public void setTipoDespesa(String tipoDespesa) {
        this.tipoDespesa = tipoDespesa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<ContasPagar> getContasPagarCollection() {
        return contasPagarCollection;
    }

    public void setContasPagarCollection(Collection<ContasPagar> contasPagarCollection) {
        this.contasPagarCollection = contasPagarCollection;
    }

    public Pessoa getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Pessoa idPessoa) {
        this.idPessoa = idPessoa;
    }

    public Empresa getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Empresa idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    @Transient
    public String getNome() {
        if (this.getTipoDespesa()!= null && this.getTipoDespesa().equals(Despesa.FORNECEDOR_DE_PRODUTOS.getTipo())) {
            if (getIdEmpresa() != null) {
                return this.getIdEmpresa().getNomeFantasia();
            } else {
                return null;
            }
        } else if(this.getTipoDespesa()!= null && this.getTipoDespesa().equals(Despesa.PESSOA.getTipo())){
            if (getIdPessoa() != null) {
                return this.getIdPessoa().getNome();
            } else {
                return null;
            }
        }else
            return getDescricao();
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
        if (!(object instanceof Fornecedor)) {
            return false;
        }
        Fornecedor other = (Fornecedor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.Fornecedor[ id=" + id + " ]";
    }
    
}
