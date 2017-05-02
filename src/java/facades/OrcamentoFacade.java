/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Orcamento;
import filters.OrcamentoFilter;
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
public class OrcamentoFacade extends AbstractFacade<Orcamento> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrcamentoFacade() {
        super(Orcamento.class);
    }
    
    public List<Orcamento> findByFilter(OrcamentoFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o FROM Orcamento o WHERE 1=1");
        
        if(filtro.getPaciente() != null)
            sql.append(" AND o.idPaciente = :paciente");
        
        if(filtro.getProfissional() != null)
            sql.append(" AND o.idProfissional = :profissional");
        
        if(filtro.getStatus()!= null && !filtro.getStatus().isEmpty())
            sql.append(" AND o.status = :status");
        
        if(filtro.getDataInicial()!= null && filtro.getDataFinal() != null)
            sql.append(" AND o.data BETWEEN :dataInicial AND :dataFinal");
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(filtro.getPaciente() != null)
            query.setParameter("paciente", filtro.getPaciente());
        
        if(filtro.getProfissional() != null)
            query.setParameter("profissional", filtro.getProfissional());
        
        if(filtro.getStatus()!= null && !filtro.getStatus().isEmpty())
            query.setParameter("status", filtro.getStatus());
        
        if(filtro.getDataInicial()!= null && filtro.getDataFinal() != null){
            query.setParameter("dataInicial", filtro.getDataInicial());
            query.setParameter("dataFinal", filtro.getDataFinal());
        }
        
        return query.getResultList();
    }
    
}
