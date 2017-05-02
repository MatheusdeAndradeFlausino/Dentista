/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Clinica;
import classes.ClinicaAcessoUsuario;
import classes.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author valderlei
 */
@Stateless
public class ClinicaAcessoUsuarioFacade extends AbstractFacade<ClinicaAcessoUsuario> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClinicaAcessoUsuarioFacade() {
        super(ClinicaAcessoUsuario.class);
    }
    
    public List<Clinica> findAllClinicaDeUmUsuario(Usuario usuario){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT cau.idClinica FROM ClinicaAcessoUsuario cau WHERE cau.idUsuario = :usuario");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("usuario", usuario);
        return query.getResultList();
    }
    
    public ClinicaAcessoUsuario findByClinicaAndUsuario(Clinica c, Usuario u){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT cau FROM ClinicaAcessoUsuario cau WHERE cau.idUsuario = :usuario");
        sql.append(" AND cau.idClinica = :clinica");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("usuario", u);
        query.setParameter("clinica", c);
        return (ClinicaAcessoUsuario) query.getSingleResult();
    }
    
}
