/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.TabelaProcedimento;
import filters.TabelaProcedimentoFilter;
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
public class TabelaProcedimentoFacade extends AbstractFacade<TabelaProcedimento> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TabelaProcedimentoFacade() {
        super(TabelaProcedimento.class);
    }
    
    
    public List<TabelaProcedimento> findByFilter(TabelaProcedimentoFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT tp FROM TabelaProcedimento tp WHERE 1=1");
        
        if(filtro.getDescricao() != null && !filtro.getDescricao().isEmpty())
            sql.append(" AND tp.descricao =:descricao");
        
        if(filtro.getIsConvenio() != null && !filtro.getIsConvenio().isEmpty())
            sql.append(" AND tp.isConvenio =:isConvenio");
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty())
            sql.append(" AND tp.status =:status");
        
        Query query =  getEntityManager().createQuery(sql.toString());
        
        if(filtro.getDescricao() != null && !filtro.getDescricao().isEmpty())
            query.setParameter("descricao", filtro.getDescricao());
        
        if(filtro.getIsConvenio() != null && !filtro.getIsConvenio().isEmpty())
            query.setParameter("isConvenio", filtro.getIsConvenio());
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty())
            query.setParameter("status", filtro.getStatus());
        
        return query.getResultList();
    }
    
}
