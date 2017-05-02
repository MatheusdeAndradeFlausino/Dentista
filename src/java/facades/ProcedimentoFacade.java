/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Procedimento;
import filters.ProcedimentoFilter;
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
public class ProcedimentoFacade extends AbstractFacade<Procedimento> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProcedimentoFacade() {
        super(Procedimento.class);
    }
    
    public List<Procedimento> findByFilter(ProcedimentoFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p FROM Procedimento p WHERE 1=1");
        
        if(!filtro.getDescricao().isEmpty())
            sql.append(" AND p.descricao LIKE :descricao");
        
        if(filtro.getEspecialidade() != null)
            sql.append(" AND p.idEspecialidade = :especialidade");
        
        if(!filtro.getStatus().isEmpty())
            sql.append(" AND p.status = :status");
        
        if(filtro.getTabelaProcedimento() != null)
            sql.append(" AND p.idTabela = :tabela");
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(!filtro.getDescricao().isEmpty())
            query.setParameter("descricao", "%" + filtro.getDescricao() + "%");
        
        if(filtro.getEspecialidade() != null)
            query.setParameter("especialidade", filtro.getEspecialidade());
        
        if(!filtro.getStatus().isEmpty())
            query.setParameter("status", filtro.getStatus());
        
        if(filtro.getTabelaProcedimento() != null)
            query.setParameter("tabela", filtro.getTabelaProcedimento());
        
        return query.getResultList();
    }
    
}
