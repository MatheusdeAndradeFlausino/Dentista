/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Regra;
import classes.RegraComissao;
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
public class RegraFacade extends AbstractFacade<Regra> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegraFacade() {
        super(Regra.class);
    }
    
    public List<Regra> findByRegraComissao(RegraComissao regraComissao){
       StringBuilder sql = new StringBuilder();
       sql.append("SELECT r FROM Regra r WHERE r.idRegraComissao = :regraComissao");
       Query query = getEntityManager().createQuery(sql.toString());
       query.setParameter("regraComissao", regraComissao);
       return query.getResultList();
    }
    
}
