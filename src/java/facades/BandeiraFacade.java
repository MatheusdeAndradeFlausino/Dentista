/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Bandeira;
import filters.BandeiraFilter;
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
public class BandeiraFacade extends AbstractFacade<Bandeira> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BandeiraFacade() {
        super(Bandeira.class);
    }
    
    public List<Bandeira> findByFilter(BandeiraFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT b FROM Bandeira b WHERE 1=1");
        
        if(filtro.getBandeira() != null && !filtro.getBandeira().isEmpty())
            sql.append(" AND b.bandeira = :bandeira");
        
        if(filtro.getDescricao() != null && !filtro.getBandeira().isEmpty())
            sql.append(" AND b.descricao LIKE :descricao");
        
        if(filtro.getTipoCartao() != null && !filtro.getTipoCartao().isEmpty())
            sql.append(" AND b.tipoCartao = :tipoCartao");
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(filtro.getBandeira() != null && !filtro.getBandeira().isEmpty())
            query.setParameter("bandeira", filtro.getBandeira() );
        
        if(filtro.getDescricao() != null && !filtro.getBandeira().isEmpty())
            query.setParameter("descricao", "%" + filtro.getDescricao() + "%");
        
        if(filtro.getTipoCartao() != null && !filtro.getTipoCartao().isEmpty())
            query.setParameter("tipoCartao", filtro.getTipoCartao());
        
        return query.getResultList();
    }
    
}
