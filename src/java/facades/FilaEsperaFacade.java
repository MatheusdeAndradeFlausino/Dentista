/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.FilaEspera;
import filters.FilaEsperaFilter;
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
public class FilaEsperaFacade extends AbstractFacade<FilaEspera> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FilaEsperaFacade() {
        super(FilaEspera.class);
    }
    
    public List<FilaEspera> findByFilter(FilaEsperaFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT fe FROM FilaEspera fe WHERE 1=1");
        
        if(filtro.getPaciente() != null)
            sql.append(" AND fe.idPaciente = :paciente");
        
        if(filtro.getProfissional()!= null)
            sql.append(" AND fe.idProfissional = :profissional");
        
        if(filtro.getDataChegada() != null)
            sql.append(" AND CAST(fe.dataChegada AS DATE) = :dataChegada");
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(filtro.getPaciente() != null)
            query.setParameter("paciente", filtro.getPaciente());
        
        if(filtro.getProfissional() != null)
            query.setParameter("profissional", filtro.getProfissional());
        
        if(filtro.getDataChegada() != null)
            query.setParameter("dataChegada", filtro.getDataChegada());
        
        return query.getResultList();
    }
}
