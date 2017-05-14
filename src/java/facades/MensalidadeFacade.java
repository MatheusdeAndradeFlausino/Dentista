/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Mensalidade;
import filters.MensalidadeFilter;
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
public class MensalidadeFacade extends AbstractFacade<Mensalidade> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MensalidadeFacade() {
        super(Mensalidade.class);
    }
    
    public List<Mensalidade> findByFilter(MensalidadeFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT m FROM Mensalidade m WHERE 1=1");
        
        if(filtro.getPaciente() != null)
            sql.append(" AND m.idPaciente = :paciente");
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty())
            sql.append(" AND m.status = :status");
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(filtro.getPaciente() != null)
            query.setParameter("paciente", filtro.getPaciente());
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty())
            query.setParameter("status", filtro.getStatus());
        
        return query.getResultList();
    }
    
}
