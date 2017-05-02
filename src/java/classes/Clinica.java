/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.Serializable;
import java.util.Collection;
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
@Table(name = "CLINICA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clinica.findAll", query = "SELECT c FROM Clinica c"),
    @NamedQuery(name = "Clinica.findById", query = "SELECT c FROM Clinica c WHERE c.id = :id"),
    @NamedQuery(name = "Clinica.findByIsCpf", query = "SELECT c FROM Clinica c WHERE c.isCpf = :isCpf"),
    @NamedQuery(name = "Clinica.findByHoraAbertura", query = "SELECT c FROM Clinica c WHERE c.horaAbertura = :horaAbertura"),
    @NamedQuery(name = "Clinica.findByHoraFechamento", query = "SELECT c FROM Clinica c WHERE c.horaFechamento = :horaFechamento"),
    @NamedQuery(name = "Clinica.findByTempoAtendimento", query = "SELECT c FROM Clinica c WHERE c.tempoAtendimento = :tempoAtendimento"),
    @NamedQuery(name = "Clinica.findByTimezone", query = "SELECT c FROM Clinica c WHERE c.timezone = :timezone"),
    @NamedQuery(name = "Clinica.findByTipoPagamentoProfissional", query = "SELECT c FROM Clinica c WHERE c.tipoPagamentoProfissional = :tipoPagamentoProfissional"),
    @NamedQuery(name = "Clinica.findByTipoLancamentoCartaoCredito", query = "SELECT c FROM Clinica c WHERE c.tipoLancamentoCartaoCredito = :tipoLancamentoCartaoCredito"),
    @NamedQuery(name = "Clinica.findByValorIndicacao", query = "SELECT c FROM Clinica c WHERE c.valorIndicacao = :valorIndicacao"),
    @NamedQuery(name = "Clinica.findByNumFichaAutomatico", query = "SELECT c FROM Clinica c WHERE c.numFichaAutomatico = :numFichaAutomatico")})
public class Clinica implements Serializable {
    @OneToMany(mappedBy = "idClinica")
    private Collection<Profissional> profissionalCollection;
    @OneToMany(mappedBy = "idClinica")
    private Collection<RegraComissao> regraComissaoCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 3)
    @Column(name = "isCpf")
    private String isCpf;
    @Size(max = 5)
    @Column(name = "horaAbertura")
    private String horaAbertura;
    @Size(max = 5)
    @Column(name = "horaFechamento")
    private String horaFechamento;
    @Size(max = 5)
    @Column(name = "tempoAtendimento")
    private String tempoAtendimento;
    @Size(max = 50)
    @Column(name = "timezone")
    private String timezone;
    @Size(max = 50)
    @Column(name = "tipoPagamentoProfissional")
    private String tipoPagamentoProfissional;
    @Size(max = 50)
    @Column(name = "tipoLancamentoCartaoCredito")
    private String tipoLancamentoCartaoCredito;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorIndicacao")
    private Double valorIndicacao;
    @Size(max = 3)
    @Column(name = "numFichaAutomatico")
    private String numFichaAutomatico;
    @JoinColumn(name = "idContato", referencedColumnName = "id")
    @ManyToOne
    private Contato idContato;
    @JoinColumn(name = "idEndereco", referencedColumnName = "id")
    @ManyToOne
    private Endereco idEndereco;
    @JoinColumn(name = "idPessoa", referencedColumnName = "id")
    @ManyToOne
    private Pessoa idPessoa;
    @JoinColumn(name = "idEmpresa", referencedColumnName = "id")
    @ManyToOne
    private Empresa idEmpresa;
    @OneToMany(mappedBy = "idClinica")
    private Collection<Usuario> usuarioCollection;
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    @Transient
    private String nome;

    public Clinica() {
    }

    public Clinica(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsCpf() {
        return isCpf;
    }

    public void setIsCpf(String isCpf) {
        this.isCpf = isCpf;
    }

    public String getHoraAbertura() {
        return horaAbertura;
    }

    public void setHoraAbertura(String horaAbertura) {
        this.horaAbertura = horaAbertura;
    }

    public String getHoraFechamento() {
        return horaFechamento;
    }

    public void setHoraFechamento(String horaFechamento) {
        this.horaFechamento = horaFechamento;
    }

    public String getTempoAtendimento() {
        return tempoAtendimento;
    }

    public void setTempoAtendimento(String tempoAtendimento) {
        this.tempoAtendimento = tempoAtendimento;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTipoPagamentoProfissional() {
        return tipoPagamentoProfissional;
    }

    public void setTipoPagamentoProfissional(String tipoPagamentoProfissional) {
        this.tipoPagamentoProfissional = tipoPagamentoProfissional;
    }

    public String getTipoLancamentoCartaoCredito() {
        return tipoLancamentoCartaoCredito;
    }

    public void setTipoLancamentoCartaoCredito(String tipoLancamentoCartaoCredito) {
        this.tipoLancamentoCartaoCredito = tipoLancamentoCartaoCredito;
    }

    public Double getValorIndicacao() {
        return valorIndicacao;
    }

    public void setValorIndicacao(Double valorIndicacao) {
        this.valorIndicacao = valorIndicacao;
    }

    public String getNumFichaAutomatico() {
        return numFichaAutomatico;
    }

    public void setNumFichaAutomatico(String numFichaAutomatico) {
        this.numFichaAutomatico = numFichaAutomatico;
    }

    public Contato getIdContato() {
        return idContato;
    }

    public void setIdContato(Contato idContato) {
        this.idContato = idContato;
    }

    public Endereco getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Endereco idEndereco) {
        this.idEndereco = idEndereco;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Transient
    public String getNome() {
        if (this.getIsCpf() != null && this.getIsCpf().equals("SIM")) {
            if (getIdPessoa() != null) {
                return this.getIdPessoa().getNome();
            } else {
                return null;
            }
        } else {
            if (getIdEmpresa() != null) {
                return this.getIdEmpresa().getNomeFantasia();
            } else {
                return null;
            }
        }
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
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
        if (!(object instanceof Clinica)) {
            return false;
        }
        Clinica other = (Clinica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if (isCpf.equals("SIM")) {
            return idPessoa.getNome();
        } else {
            return idEmpresa.getNomeFantasia();
        }
    }

    @XmlTransient
    public Collection<RegraComissao> getRegraComissaoCollection() {
        return regraComissaoCollection;
    }

    public void setRegraComissaoCollection(Collection<RegraComissao> regraComissaoCollection) {
        this.regraComissaoCollection = regraComissaoCollection;
    }

    @XmlTransient
    public Collection<Profissional> getProfissionalCollection() {
        return profissionalCollection;
    }

    public void setProfissionalCollection(Collection<Profissional> profissionalCollection) {
        this.profissionalCollection = profissionalCollection;
    }

}
