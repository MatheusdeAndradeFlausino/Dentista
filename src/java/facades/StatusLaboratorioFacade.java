/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.StatusLaboratorio;
import filters.StatusLaboratorioFilter;
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
public class StatusLaboratorioFacade extends AbstractFacade<StatusLaboratorio> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StatusLaboratorioFacade() {
        super(StatusLaboratorio.class);
    }
    
    public List<StatusLaboratorio> findByFilter(StatusLaboratorioFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT sl FROM StatusLaboratorio sl WHERE 1=1");
        
        if(filtro.getDescricao() != null && !filtro.getDescricao().isEmpty())
            sql.append(" AND sl.descricao LIKE :descricao");
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty())
            sql.append(" AND sl.status = :status");
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(filtro.getDescricao() != null && !filtro.getDescricao().isEmpty())
            query.setParameter("descricao", "%" + filtro.getDescricao() + "%");
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty())
            query.setParameter("status", filtro.getStatus());
        
        return query.getResultList();
    }
}
