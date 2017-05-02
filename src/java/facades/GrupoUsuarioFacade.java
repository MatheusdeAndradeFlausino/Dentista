/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Grupo;
import classes.GrupoUsuario;
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
public class GrupoUsuarioFacade extends AbstractFacade<GrupoUsuario> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GrupoUsuarioFacade() {
        super(GrupoUsuario.class);
    }
        
    public List<Grupo> findAllGruposDeUmUsuario(Usuario usuario){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT gu.grupo FROM GrupoUsuario gu WHERE gu.usuario = :usuario");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("usuario", usuario);
        return query.getResultList();
    }
    
    public GrupoUsuario findByGrupoAndUsuario(Usuario usuario, Grupo grupo){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT gu.grupo FROM GrupoUsuario gu WHERE gu.usuario = :usuario");
        sql.append(" AND gu.grupo = :grupo");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("usuario", usuario);
        query.setParameter("grupo", grupo);
        return (GrupoUsuario)query.getSingleResult();
    }
    
}
