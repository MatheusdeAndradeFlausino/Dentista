/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.RegraComissao;
import filters.RegraComissaoFilter;
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
public class RegraComissaoFacade extends AbstractFacade<RegraComissao> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegraComissaoFacade() {
        super(RegraComissao.class);
    }
    
    public List<RegraComissao> findByFilter(RegraComissaoFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT rc FROM RegraComissao rc WHERE 1=1");
        
        if(filtro.getClinica() != null)
            sql.append(" AND rc.idClinica = :clinica");
        
        if(!filtro.getDescricao().isEmpty())
            sql.append(" AND rc.descricao = :descricao");
        
        if(!filtro.getStatus().isEmpty())
            sql.append(" AND rc.status = :status");
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(filtro.getClinica() != null)
            query.setParameter("clinica", filtro.getClinica());
        
        if(!filtro.getDescricao().isEmpty())
            query.setParameter("descricao", filtro.getDescricao());
        
        if(!filtro.getStatus().isEmpty())
            query.setParameter("status", filtro.getStatus());
        
        return query.getResultList();        
    }
}
