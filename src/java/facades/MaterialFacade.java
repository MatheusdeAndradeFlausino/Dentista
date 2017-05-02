/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Material;
import filters.MaterialFilter;
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
public class MaterialFacade extends AbstractFacade<Material> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MaterialFacade() {
        super(Material.class);
    }
    
    public List<Material> findByFilter(MaterialFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT m FROM Material m WHERE 1=1");
        
        if(!filtro.getApelido().isEmpty())
            sql.append(" AND m.apelido = :apelido");
        
        if(!filtro.getDescricao().isEmpty())
            sql.append(" AND m.descricao = :descricao");
        
        if(!filtro.getStatus().isEmpty())
            sql.append(" AND m.status = :status");
        
        Query query =  getEntityManager().createQuery(sql.toString());
        
        if(!filtro.getApelido().isEmpty())
            query.setParameter("apelido", filtro.getApelido());
        
        if(!filtro.getDescricao().isEmpty())
            query.setParameter("descricao", filtro.getDescricao());
        
        if(!filtro.getStatus().isEmpty())
            query.setParameter("status", filtro.getStatus());
        
        return query.getResultList();
    }
    
    public List<Material> findByFilterWithRange(MaterialFilter filtro, int[] range){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT m FROM Material m WHERE 1=1");
        
        if(filtro.getApelido() != null && !filtro.getApelido().isEmpty())
            sql.append(" AND m.apelido = :apelido");
        
        if(filtro.getDescricao() != null && !filtro.getDescricao().isEmpty())
            sql.append(" AND m.descricao = :descricao");
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty())
            sql.append(" AND m.status = :status");
        
        Query query =  getEntityManager().createQuery(sql.toString());
        
        if(filtro.getApelido() != null && !filtro.getApelido().isEmpty())
            query.setParameter("apelido", filtro.getApelido());
        
        if(filtro.getDescricao() != null && !filtro.getDescricao().isEmpty())
            query.setParameter("descricao", filtro.getDescricao());
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty())
            query.setParameter("status", filtro.getStatus());
        
        query.setMaxResults(range[1] - range[0] + 1);
        query.setFirstResult(range[0]);
        return query.getResultList();
    }
    
}
