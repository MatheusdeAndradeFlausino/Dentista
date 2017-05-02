/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.RegraComissao;
import classes.RegraEspecial;
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
public class RegraEspecialFacade extends AbstractFacade<RegraEspecial> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegraEspecialFacade() {
        super(RegraEspecial.class);
    }
    
    public List<RegraEspecial> findByRegraComissao(RegraComissao regraComissao){
       StringBuilder sql = new StringBuilder();
       sql.append("SELECT re FROM RegraEspecial re WHERE re.idRegraComissao = :regraComissao");
       Query query = getEntityManager().createQuery(sql.toString());
       query.setParameter("regraComissao", regraComissao);
       return query.getResultList();
    }
    
}
