/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Mensalidade;
import classes.ParcelaMensalidade;
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
public class ParcelaMensalidadeFacade extends AbstractFacade<ParcelaMensalidade>{
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ParcelaMensalidadeFacade(){
        super(ParcelaMensalidade.class);
    }
    
    public List<ParcelaMensalidade> findByMensalidade(Mensalidade mensalidade){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT pm FROM ParcelaMensalidade pm WHERE pm.idMensalidade = :mensalidade");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("mensalidade", mensalidade);
        return query.getResultList();
    }
}
