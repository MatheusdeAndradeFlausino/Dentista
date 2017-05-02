/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Procedimento;
import classes.ProcedimentoClinica;
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
public class ProcedimentoClinicaFacade extends AbstractFacade<ProcedimentoClinica> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProcedimentoClinicaFacade() {
        super(ProcedimentoClinica.class);
    }
    
    public List<ProcedimentoClinica> findByProcedimento(Procedimento procedimento){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT pc FROM ProcedimentoClinica pc WHERE pc.idProcedimento = :procedimento");
        
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("procedimento", procedimento);
        
        return query.getResultList();
    }
    
}
