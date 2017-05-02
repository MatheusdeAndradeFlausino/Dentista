/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.HistoricoLaboratorio;
import filters.HistoricoLaboratorioFilter;
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
public class HistoricoLaboratorioFacade extends AbstractFacade<HistoricoLaboratorio> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistoricoLaboratorioFacade() {
        super(HistoricoLaboratorio.class);
    }
    
    public List<HistoricoLaboratorio> findByFilter(HistoricoLaboratorioFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT hl FROM HistoricoLaboratorio hl WHERE 1=1");
        
        if(filtro.getPaciente() != null)
            sql.append(" AND hl.idPaciente = :paciente");
        
        if(filtro.getProcedimento() != null)
            sql.append(" AND hl.idProcedimento = :procedimento");
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(filtro.getPaciente() != null)
            query.setParameter("paciente", filtro.getPaciente());
        
        if(filtro.getProcedimento() != null)
            query.setParameter("procedimento", filtro.getProcedimento());
        
        return query.getResultList();
    }
    
}
