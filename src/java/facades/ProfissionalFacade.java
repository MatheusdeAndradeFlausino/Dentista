/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Profissional;
import filters.ProfissionalFilter;
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
public class ProfissionalFacade extends AbstractFacade<Profissional> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProfissionalFacade() {
        super(Profissional.class);
    }
    
    public List<Profissional> findByFilter(ProfissionalFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p FROM Profissional p WHERE 1=1");
        
        if(filtro.getNome() != null && !filtro.getNome().isEmpty()){
            sql.append(" AND p.idPessoa.nome LIKE :nome");
        }
        
        if(filtro.getClinica() != null){
            sql.append(" AND p.idClinica = :clinica");
        }
        
        if(filtro.getCpf() != null && !filtro.getCpf().isEmpty()){
            sql.append(" AND p.idPessoa.cpf = :cpf");
        }
        
        if(filtro.getRg() != null && !filtro.getRg().isEmpty()){
            sql.append(" AND p.idPessoa.rg = :rg");
        }
        
        if(filtro.getProfissao() != null && !filtro.getProfissao().isEmpty()){
            sql.append(" AND p.profissao = :profissao");
        }
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty()){
            sql.append(" AND p.status = :status");
        }
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(filtro.getNome() != null && !filtro.getNome().isEmpty()){
            query.setParameter("nome","%"+ filtro.getNome() +"%");
        }
        
        if(filtro.getClinica() != null){
            query.setParameter("clinica", filtro.getClinica());
        }
        
        if(filtro.getCpf() != null && !filtro.getCpf().isEmpty()){
            query.setParameter("cpf", filtro.getCpf());
        }
        
        if(filtro.getRg() != null && !filtro.getRg().isEmpty()){
            query.setParameter("rg", filtro.getRg());
        }
        
        if(filtro.getProfissao() != null && !filtro.getProfissao().isEmpty()){
            query.setParameter("profissao", filtro.getProfissao());
        }
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty()){
            query.setParameter("status", filtro.getStatus());
        }
        
        
        return query.getResultList();
    }
    
    public List<Profissional> findAllAniversariantes(Date dataAniversario){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p FROM Profissional p WHERE EXTRACT(DAY FROM p.idPessoa.dataNascimento) = EXTRACT(DAY FROM :dataAniversario )");
        sql.append(" AND EXTRACT(MONTH FROM p.idPessoa.dataNascimento) = EXTRACT(MONTH FROM :dataAniversario )");
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        query.setParameter("dataAniversario", dataAniversario);
        
        return query.getResultList();
    }
}
