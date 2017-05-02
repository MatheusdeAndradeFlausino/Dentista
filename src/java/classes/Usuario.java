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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author valderlei
 */
@Entity
@Table(name = "USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findById", query = "SELECT u FROM Usuario u WHERE u.id = :id"),
    @NamedQuery(name = "Usuario.findByLogin", query = "SELECT u FROM Usuario u WHERE u.login = :login"),
    @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email"),
    @NamedQuery(name = "Usuario.findBySenha", query = "SELECT u FROM Usuario u WHERE u.senha = :senha"),
    @NamedQuery(name = "Usuario.findByIsRestricaoHorario", query = "SELECT u FROM Usuario u WHERE u.isRestricaoHorario = :isRestricaoHorario"),
    @NamedQuery(name = "Usuario.findByHoraInicioDomingo", query = "SELECT u FROM Usuario u WHERE u.horaInicioDomingo = :horaInicioDomingo"),
    @NamedQuery(name = "Usuario.findByHoraInicioSegunda", query = "SELECT u FROM Usuario u WHERE u.horaInicioSegunda = :horaInicioSegunda"),
    @NamedQuery(name = "Usuario.findByHoraInicioTerca", query = "SELECT u FROM Usuario u WHERE u.horaInicioTerca = :horaInicioTerca"),
    @NamedQuery(name = "Usuario.findByHoraInicioQuarta", query = "SELECT u FROM Usuario u WHERE u.horaInicioQuarta = :horaInicioQuarta"),
    @NamedQuery(name = "Usuario.findByHoraInicioQuinta", query = "SELECT u FROM Usuario u WHERE u.horaInicioQuinta = :horaInicioQuinta"),
    @NamedQuery(name = "Usuario.findByHoraInicioSexta", query = "SELECT u FROM Usuario u WHERE u.horaInicioSexta = :horaInicioSexta"),
    @NamedQuery(name = "Usuario.findByHoraInicioSabado", query = "SELECT u FROM Usuario u WHERE u.horaInicioSabado = :horaInicioSabado"),
    @NamedQuery(name = "Usuario.findByHoraFinalDomingo", query = "SELECT u FROM Usuario u WHERE u.horaFinalDomingo = :horaFinalDomingo"),
    @NamedQuery(name = "Usuario.findByHoraFinalSegunda", query = "SELECT u FROM Usuario u WHERE u.horaFinalSegunda = :horaFinalSegunda"),
    @NamedQuery(name = "Usuario.findByHoraFinalTerca", query = "SELECT u FROM Usuario u WHERE u.horaFinalTerca = :horaFinalTerca"),
    @NamedQuery(name = "Usuario.findByHoraFinalQuarta", query = "SELECT u FROM Usuario u WHERE u.horaFinalQuarta = :horaFinalQuarta"),
    @NamedQuery(name = "Usuario.findByHoraFinalQuinta", query = "SELECT u FROM Usuario u WHERE u.horaFinalQuinta = :horaFinalQuinta"),
    @NamedQuery(name = "Usuario.findByHoraFinalSexta", query = "SELECT u FROM Usuario u WHERE u.horaFinalSexta = :horaFinalSexta"),
    @NamedQuery(name = "Usuario.findByHoraFinalSabado", query = "SELECT u FROM Usuario u WHERE u.horaFinalSabado = :horaFinalSabado"),
    @NamedQuery(name = "Usuario.findByStatus", query = "SELECT u FROM Usuario u WHERE u.status = :status")})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 30)
    @Column(name = "login")
    private String login;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "email")
    private String email;
    @Size(max = 50)
    @Column(name = "senha")
    private String senha;
    @Size(max = 3)
    @Column(name = "isRestricaoHorario")
    private String isRestricaoHorario;
    @Size(max = 5)
    @Column(name = "horaInicioDomingo")
    private String horaInicioDomingo;
    @Size(max = 5)
    @Column(name = "horaInicioSegunda")
    private String horaInicioSegunda;
    @Size(max = 5)
    @Column(name = "horaInicioTerca")
    private String horaInicioTerca;
    @Size(max = 5)
    @Column(name = "horaInicioQuarta")
    private String horaInicioQuarta;
    @Size(max = 5)
    @Column(name = "horaInicioQuinta")
    private String horaInicioQuinta;
    @Size(max = 5)
    @Column(name = "horaInicioSexta")
    private String horaInicioSexta;
    @Size(max = 5)
    @Column(name = "horaInicioSabado")
    private String horaInicioSabado;
    @Size(max = 5)
    @Column(name = "horaFinalDomingo")
    private String horaFinalDomingo;
    @Size(max = 5)
    @Column(name = "horaFinalSegunda")
    private String horaFinalSegunda;
    @Size(max = 5)
    @Column(name = "horaFinalTerca")
    private String horaFinalTerca;
    @Size(max = 5)
    @Column(name = "horaFinalQuarta")
    private String horaFinalQuarta;
    @Size(max = 5)
    @Column(name = "horaFinalQuinta")
    private String horaFinalQuinta;
    @Size(max = 5)
    @Column(name = "horaFinalSexta")
    private String horaFinalSexta;
    @Size(max = 5)
    @Column(name = "horaFinalSabado")
    private String horaFinalSabado;
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "usuario")
    private Collection<GrupoUsuario> grupoUsuarioCollection;
    @JoinColumn(name = "idClinica", referencedColumnName = "id")
    @ManyToOne
    private Clinica idClinica;

    public Usuario() {
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getIsRestricaoHorario() {
        return isRestricaoHorario;
    }

    public void setIsRestricaoHorario(String isRestricaoHorario) {
        this.isRestricaoHorario = isRestricaoHorario;
    }

    public String getHoraInicioDomingo() {
        return horaInicioDomingo;
    }

    public void setHoraInicioDomingo(String horaInicioDomingo) {
        this.horaInicioDomingo = horaInicioDomingo;
    }

    public String getHoraInicioSegunda() {
        return horaInicioSegunda;
    }

    public void setHoraInicioSegunda(String horaInicioSegunda) {
        this.horaInicioSegunda = horaInicioSegunda;
    }

    public String getHoraInicioTerca() {
        return horaInicioTerca;
    }

    public void setHoraInicioTerca(String horaInicioTerca) {
        this.horaInicioTerca = horaInicioTerca;
    }

    public String getHoraInicioQuarta() {
        return horaInicioQuarta;
    }

    public void setHoraInicioQuarta(String horaInicioQuarta) {
        this.horaInicioQuarta = horaInicioQuarta;
    }

    public String getHoraInicioQuinta() {
        return horaInicioQuinta;
    }

    public void setHoraInicioQuinta(String horaInicioQuinta) {
        this.horaInicioQuinta = horaInicioQuinta;
    }

    public String getHoraInicioSexta() {
        return horaInicioSexta;
    }

    public void setHoraInicioSexta(String horaInicioSexta) {
        this.horaInicioSexta = horaInicioSexta;
    }

    public String getHoraInicioSabado() {
        return horaInicioSabado;
    }

    public void setHoraInicioSabado(String horaInicioSabado) {
        this.horaInicioSabado = horaInicioSabado;
    }

    public String getHoraFinalDomingo() {
        return horaFinalDomingo;
    }

    public void setHoraFinalDomingo(String horaFinalDomingo) {
        this.horaFinalDomingo = horaFinalDomingo;
    }

    public String getHoraFinalSegunda() {
        return horaFinalSegunda;
    }

    public void setHoraFinalSegunda(String horaFinalSegunda) {
        this.horaFinalSegunda = horaFinalSegunda;
    }

    public String getHoraFinalTerca() {
        return horaFinalTerca;
    }

    public void setHoraFinalTerca(String horaFinalTerca) {
        this.horaFinalTerca = horaFinalTerca;
    }

    public String getHoraFinalQuarta() {
        return horaFinalQuarta;
    }

    public void setHoraFinalQuarta(String horaFinalQuarta) {
        this.horaFinalQuarta = horaFinalQuarta;
    }

    public String getHoraFinalQuinta() {
        return horaFinalQuinta;
    }

    public void setHoraFinalQuinta(String horaFinalQuinta) {
        this.horaFinalQuinta = horaFinalQuinta;
    }

    public String getHoraFinalSexta() {
        return horaFinalSexta;
    }

    public void setHoraFinalSexta(String horaFinalSexta) {
        this.horaFinalSexta = horaFinalSexta;
    }

    public String getHoraFinalSabado() {
        return horaFinalSabado;
    }

    public void setHoraFinalSabado(String horaFinalSabado) {
        this.horaFinalSabado = horaFinalSabado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<GrupoUsuario> getGrupoUsuarioCollection() {
        return grupoUsuarioCollection;
    }

    public void setGrupoUsuarioCollection(Collection<GrupoUsuario> grupoUsuarioCollection) {
        this.grupoUsuarioCollection = grupoUsuarioCollection;
    }

    public Clinica getIdClinica() {
        return idClinica;
    }

    public void setIdClinica(Clinica idClinica) {
        this.idClinica = idClinica;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classes.Usuario[ id=" + id + " ]";
    }
    
}
