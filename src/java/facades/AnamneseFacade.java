/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Anamnese;
import filters.AnamneseFilter;
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
public class AnamneseFacade extends AbstractFacade<Anamnese> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnamneseFacade() {
        super(Anamnese.class);
    }
    
    public List<Anamnese> findByFilter(AnamneseFilter filtro){
       StringBuilder sql = new StringBuilder();
       sql.append("SELECT a FROM Anamnese a WHERE 1=1");
       
       if(filtro.getPaciente() != null)
           sql.append(" AND a.paciente = :paciente");
       
       if(filtro.getProfissional() != null)
           sql.append(" AND a.profissional = :profissional");
       
       if(filtro.getStatus() != null && !filtro.getStatus().isEmpty())
           sql.append(" AND a.status = :status");
       
       Query query = getEntityManager().createQuery(sql.toString());
       
       if(filtro.getPaciente() != null)
           query.setParameter("paciente", filtro.getPaciente());
       
       if(filtro.getProfissional() != null)
           query.setParameter("profissional", filtro.getProfissional());
       
       if(filtro.getStatus() != null && !filtro.getStatus().isEmpty())
           query.setParameter("status", filtro.getStatus());
       
       return query.getResultList();       
    }
    
}
