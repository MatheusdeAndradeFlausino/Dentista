/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Motivo;
import filters.MotivoFilter;
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
public class MotivoFacade extends AbstractFacade<Motivo> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MotivoFacade() {
        super(Motivo.class);
    }
    
    public List<Motivo> findByFilter(MotivoFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT m FROM Motivo m WHERE 1=1");
        
        if(filtro.getDescricao() != null && !filtro.getDescricao().isEmpty())
            sql.append(" AND m.descricao LIKE :descricao");
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty())
            sql.append(" AND m.status = :status");
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(filtro.getDescricao() != null && !filtro.getDescricao().isEmpty())
            query.setParameter("descricao", "%" + filtro.getDescricao() + "%");
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty())
            query.setParameter("status", filtro.getStatus());
        
        return query.getResultList();
    }
}
