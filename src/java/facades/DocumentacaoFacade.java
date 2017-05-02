/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Documentacao;
import filters.DocumentacaoFilter;
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
public class DocumentacaoFacade extends AbstractFacade<Documentacao> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DocumentacaoFacade() {
        super(Documentacao.class);
    }
    
    public List<Documentacao> findByFilter(DocumentacaoFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT d FROM Documentacao d WHERE 1=1");
        
        if(filtro.getEspecialidade() != null)
            sql.append(" AND d.idEspecialidade = :especialidade");
        
        if(filtro.getPaciente() != null)
            sql.append(" AND d.idPaciente = :paciente");
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(filtro.getEspecialidade() != null)
            query.setParameter("especialidade", filtro.getEspecialidade());
        
        if(filtro.getPaciente() != null)
            query.setParameter("paciente", filtro.getPaciente());
        
        return query.getResultList();
    }
    
}
