/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Grupo;
import classes.PermissoesGrupo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author vanderlei
 */
@Stateless
public class PermissoesGrupoFacade extends AbstractFacade{
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    public PermissoesGrupoFacade() {
        super(PermissoesGrupoFacade.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<PermissoesGrupo> findByGrupo(Grupo grupo){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT pg FROM PermissoesGrupo pg WHERE pg.grupo = :grupo");
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        query.setParameter("grupo", grupo);
        return query.getResultList();        
    }
    
    public void deleteAllPermissionsFromGrupo(Grupo grupo){
        List<PermissoesGrupo> pgs = findByGrupo(grupo);
        for (PermissoesGrupo pg : pgs) {
            remove(pg);
        }
    }
}
