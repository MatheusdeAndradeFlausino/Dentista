/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Material;
import classes.Movimentacao;
import filters.MovimentacaoFilter;
import java.util.Date;
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
public class MovimentacaoFacade extends AbstractFacade<Movimentacao> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MovimentacaoFacade() {
        super(Movimentacao.class);
    }
    
    public List<Movimentacao> findByFilter(MovimentacaoFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT m FROM Movimentacao m WHERE 1=1");
                
        if(filtro.getMaterial() != null)
            sql.append(" AND m.idMaterial = :material");
        
        if(filtro.getData() != null)
            sql.append(" AND m.data = :data");
        
        if(filtro.getTipoMovimentacao() != null && !filtro.getTipoMovimentacao().isEmpty())
            sql.append(" AND m.tipoMovimentacao = :tipoMovimentacao");
        
        Query query = getEntityManager().createQuery(sql.toString());
              
        if(filtro.getMaterial() != null)
            query.setParameter("material", filtro.getMaterial());
        
        if(filtro.getData() != null)
            query.setParameter("data",filtro.getData());
        
        if(filtro.getTipoMovimentacao() != null && !filtro.getTipoMovimentacao().isEmpty())
            query.setParameter("tipoMovimentacao", filtro.getTipoMovimentacao());
        
        return query.getResultList();
    }
    
    public List<Movimentacao> findAllMovimentacaoOfMaterialAfterDate(Movimentacao movimentacao){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT m FROM Movimentacao m WHERE m.idMaterial = :material AND m.data > :data");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("material", movimentacao.getIdMaterial());
        query.setParameter("data", movimentacao.getData());
        return query.getResultList();
    }
    
}
