/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Fornecedor;
import filters.FornecedorFilter;
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
public class FornecedorFacade extends AbstractFacade<Fornecedor> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FornecedorFacade() {
        super(Fornecedor.class);
    }
    
    public List<Fornecedor> findByFilter(FornecedorFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT f FROM Fornecedor f WHERE 1=1");
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty()){
            sql.append(" AND f.status = :status");
        }
        
        if(filtro.getTipoDespesa() != null && !filtro.getTipoDespesa().isEmpty()){
            sql.append(" AND f.tipoDespesa = :tipoDespesa");
        }
        
        if(filtro.getNome() != null && !filtro.getNome().isEmpty()){
            sql.append(" AND (f.idPessoa.nome LIKE :nome OR f.idEmpresa.nomeFantasia LIKE :nome OR f.descricao LIKE :nome)");
        }
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty()){
            query.setParameter("status", filtro.getStatus());
        }
        
        if(filtro.getTipoDespesa() != null && !filtro.getTipoDespesa().isEmpty()){
            query.setParameter("tipoDespesa", filtro.getTipoDespesa());
        }
        
        if(filtro.getNome() != null && !filtro.getNome().isEmpty()){
            query.setParameter("nome", "%" + filtro.getNome() + "%");
        }
        
        System.out.println(sql.toString());
        return query.getResultList();
    }
    
}
