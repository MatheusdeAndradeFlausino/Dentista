/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.FormaPagamento;
import filters.FormaPagamentoFilter;
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
public class FormaPagamentoFacade extends AbstractFacade<FormaPagamento> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FormaPagamentoFacade() {
        super(FormaPagamento.class);
    }
    
    public List<FormaPagamento> findByFilter(FormaPagamentoFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT fp FROM FormaPagamento fp WHERE 1=1");
        
        if(filtro.getDescricao() != null && !filtro.getDescricao().isEmpty())
            sql.append(" AND fp.descricao LIKE :descricao");
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty())
            sql.append(" AND fp.status = :status");
        
        if(filtro.getTipoPagamento() != null && !filtro.getTipoPagamento().isEmpty())
            sql.append(" AND fp.tipoPagamento = :tipoPagamento");
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(filtro.getDescricao() != null && !filtro.getDescricao().isEmpty())
            query.setParameter("descricao", "%" + filtro.getDescricao() + "%");
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty())
            query.setParameter("status", filtro.getStatus());
        
        if(filtro.getTipoPagamento() != null && !filtro.getTipoPagamento().isEmpty())
            query.setParameter("tipoPagamento", filtro.getTipoPagamento());
        
        return query.getResultList();
    }
    
}
