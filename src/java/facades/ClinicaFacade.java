/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Clinica;
import filters.ClinicaFilter;
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
public class ClinicaFacade extends AbstractFacade<Clinica> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClinicaFacade() {
        super(Clinica.class);
    }
    
    public List<Clinica> findByNome(String nome, boolean isCPF){
        StringBuilder hql = new StringBuilder();
        
        if(isCPF)
            hql.append("Select c FROM Clinica c WHERE c.idPessoa.nome LIKE :param");
        else
            hql.append("Select c FROM Clinica c WHERE c.idEmpresa.nomeFantasia LIKE :param");
        
        Query query = getEntityManager().createQuery(hql.toString());
        query.setParameter("param","%" + nome + "%");
        
        return query.getResultList();
    }
    
    public List<Clinica> findByFilter(ClinicaFilter filtro){
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlPessoa = new StringBuilder();
        StringBuilder sqlEmpresa = new StringBuilder();
        
        sql.append("SELECT c FROM Clinica c WHERE c IN (");
        sqlPessoa.append(" SELECT cl FROM Clinica cl JOIN cl.idPessoa p WHERE 1=1");
        sqlEmpresa.append(" SELECT cl FROM Clinica cl JOIN cl.idEmpresa e WHERE 1=1");
        
        if(filtro.getNome() != null && !filtro.getNome().isEmpty()){
            sqlPessoa.append(" AND p.nome LIKE :nome");
            sqlEmpresa.append(" AND e.nomeFantasia LIKE :nome");
        }
        
        if(filtro.getCidade() != null && !filtro.getCidade().isEmpty()){
            sqlPessoa.append(" AND p.idEndereco.cidade LIKE :cidade");
            sqlEmpresa.append(" AND e.idEndereco.cidade LIKE :cidade");
        }
        
        if(filtro.getEstado()!= null && !filtro.getEstado().isEmpty()){
            sqlPessoa.append(" AND p.idEndereco.estado LIKE :estado");
            sqlEmpresa.append(" AND e.idEndereco.estado LIKE :estado");
        }
        
        sql.append(sqlEmpresa.toString());
        sql.append(" ) OR c IN (");
        sql.append(sqlPessoa.toString());
        sql.append(" )");
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty())
            sql.append(" AND c.status = :status");
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(filtro.getNome() != null && !filtro.getNome().isEmpty()){
            query.setParameter("nome", "%" + filtro.getNome() + "%");
        }
        
        if(filtro.getCidade() != null && !filtro.getCidade().isEmpty()){
            query.setParameter("cidade", "%" + filtro.getCidade() + "%");
        }
        
        if(filtro.getEstado()!= null && !filtro.getEstado().isEmpty()){
            query.setParameter("estado", "%" + filtro.getEstado() + "%");
        }
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty())
            query.setParameter("status", filtro.getStatus());
        
        return query.getResultList();
       
    }
    
}
