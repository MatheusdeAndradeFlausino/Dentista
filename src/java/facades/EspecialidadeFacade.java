/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Especialidade;
import filters.EspecialidadeFilter;
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
public class EspecialidadeFacade extends AbstractFacade<Especialidade> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EspecialidadeFacade() {
        super(Especialidade.class);
    }
    
    public List<Especialidade> findByFilter(EspecialidadeFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT e FROM Especialidade e WHERE 1=1");
        
        if(!filtro.getDescricao().isEmpty())
            sql.append(" AND e.descricao LIKE :descricao");
        
        if(!filtro.getStatus().isEmpty())
            sql.append(" AND e.status = :status");
        
        if(!filtro.getTipoComissionamento().isEmpty())
            sql.append(" AND e.tipoComissionamento = :tipoComissionamento");
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(!filtro.getDescricao().isEmpty())
            query.setParameter("descricao", filtro.getDescricao());
        
        if(!filtro.getStatus().isEmpty())
            query.setParameter("status", filtro.getStatus());
        
        if(!filtro.getTipoComissionamento().isEmpty())
            query.setParameter("tipoComissionamento", filtro.getTipoComissionamento());
        
        return query.getResultList();
    }
    
}
