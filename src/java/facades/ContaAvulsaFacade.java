/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.ContaAvulsa;
import filters.ContaAvulsaFilter;
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
public class ContaAvulsaFacade extends AbstractFacade<ContaAvulsa> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContaAvulsaFacade() {
        super(ContaAvulsa.class);
    }
    
    public List<ContaAvulsa> findByFilter(ContaAvulsaFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ca FROM ContaAvulsa ca WHERE 1=1");
        
        if(filtro.getPaciente() != null)
            sql.append(" AND ca.idPaciente = :paciente");
        
        if(filtro.getProcedimento() != null)
            sql.append(" AND ca.idProcedimento = :procedimento");
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(filtro.getPaciente() != null)
            query.setParameter("paciente", filtro.getPaciente());
        
        if(filtro.getProcedimento() != null)
            query.setParameter("procedimento", filtro.getProcedimento());
        
        return query.getResultList();
        
    }
    
}
