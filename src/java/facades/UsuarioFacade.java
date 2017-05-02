/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Usuario;
import filters.UsuarioFilter;
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
public class UsuarioFacade extends AbstractFacade<Usuario> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    public Usuario findByLogin(String login){
        Query query = getEntityManager().createNamedQuery("Usuario.findByLogin");
        query.setParameter("login", login);
        return (Usuario)query.getSingleResult();
    }
    
    public List<Usuario> findByFilter(UsuarioFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u FROM Usuario u WHERE 1=1");
        
        if(filtro.getClinica() != null)
            sql.append(" AND u IN (SELECT uc.idUsuario FROM ClinicaAcessoUsuario uc WHERE uc.idClinica = :clinica)");
        
        if(filtro.getGrupo() != null)
            sql.append(" AND u IN (SELECT gu.idUsuario FROM GrupoUsuario gu WHERE gu.idGrupo = :grupo)");
        
        if(filtro.getEmail() != null && !filtro.getEmail().isEmpty())
            sql.append(" AND u.email LIKE :email");
        
        if(filtro.getLogin() != null && !filtro.getLogin().isEmpty())
            sql.append(" AND u.login LIKE :login");
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(filtro.getClinica() != null)
            query.setParameter("clinica", filtro.getClinica());
        
        if(filtro.getGrupo() != null)
            query.setParameter("grupo", filtro.getGrupo());
        
        if(filtro.getEmail() != null && !filtro.getEmail().isEmpty())
            query.setParameter("email", filtro.getEmail());
        
        if(filtro.getLogin() != null && !filtro.getLogin().isEmpty())
            query.setParameter("login", filtro.getLogin());
        
        return query.getResultList();
    }
    
}
