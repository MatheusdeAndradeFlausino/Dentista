/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Indicacao;
import filters.IndicacaoFilter;
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
public class IndicacaoFacade extends AbstractFacade<Indicacao> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IndicacaoFacade() {
        super(Indicacao.class);
    }
    
    public List<Indicacao> findByFilter(IndicacaoFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT i FROM Indicacao i WHERE 1=1");
        
        if(filtro.getPaciente() != null)
            sql.append(" AND i.idPaciente = :paciente");
        
        if(filtro.getProfissional()!= null)
            sql.append(" AND i.idProfissional = :profissional");
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(filtro.getPaciente() != null)
            query.setParameter("paciente", filtro.getPaciente());
        
        if(filtro.getProfissional()!= null)
            query.setParameter("profissional", filtro.getProfissional());
        
        return query.getResultList();
    }
}
