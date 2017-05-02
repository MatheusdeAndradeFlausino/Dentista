/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Paciente;
import filters.PacienteFilter;
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
public class PacienteFacade extends AbstractFacade<Paciente> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PacienteFacade() {
        super(Paciente.class);
    }
    
    public List<Paciente> findByFilter(PacienteFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p FROM Paciente p WHERE 1=1");
        
        if(!filtro.getNome().isEmpty()){
            sql.append(" AND p.idPessoa.nome LIKE :nome");
        }
        
        if(!filtro.getCpf().isEmpty()){
            sql.append(" AND p.idPessoa.cpf = :cpf");
        }
        
        if(!filtro.getRg().isEmpty()){
            sql.append(" AND p.idPessoa.rg = :rg");
        }
        
        if(filtro.getNumFicha() != null){
            sql.append(" AND p.numFicha = :numFicha");
        }
        
        if(!filtro.getStatus().isEmpty()){
            sql.append(" AND p.status = :status");
        }
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(filtro.getNome() != null && !filtro.getNome().isEmpty()){
            query.setParameter("nome","%" + filtro.getNome() + "%");
        }
        
        if(filtro.getCpf() != null && !filtro.getCpf().isEmpty()){
            query.setParameter("cpf",filtro.getCpf());
        }
        
        if(filtro.getRg() != null && !filtro.getRg().isEmpty()){
            query.setParameter("rg",filtro.getRg());
        }
        
        if(filtro.getNumFicha() != null){
            query.setParameter("numFicha",filtro.getNumFicha());
        }
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty()){
            query.setParameter("status",filtro.getStatus());
        }
        
        return query.getResultList();        
        
    }
    
}
