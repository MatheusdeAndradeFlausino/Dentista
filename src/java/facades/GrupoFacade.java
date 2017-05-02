/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Grupo;
import filters.GrupoFilter;
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
public class GrupoFacade extends AbstractFacade<Grupo> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GrupoFacade() {
        super(Grupo.class);
    }
 
    public List<Grupo> findByFilter(GrupoFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT g FROM Grupo g WHERE 1=1");
        
        if(filtro.getDescricao() != null && !filtro.getDescricao().isEmpty())
            sql.append(" AND g.descricao LIKE :descricao");
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty())
            sql.append(" AND g.status = :status");
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(filtro.getDescricao() != null && !filtro.getDescricao().isEmpty())
            query.setParameter("descricao", "%" + filtro.getDescricao() + "%");
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty())
            query.setParameter("status", filtro.getStatus());
        
        return query.getResultList();
    }
    
}
