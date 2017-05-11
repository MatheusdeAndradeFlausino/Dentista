/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.ContasPagar;
import enums.StatusContasPagar;
import filters.ContasPagarFilter;
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
public class ContasPagarFacade extends AbstractFacade<ContasPagar> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContasPagarFacade() {
        super(ContasPagar.class);
    }
    
    public List<ContasPagar> findByFilter(ContasPagarFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT cp FROM ContasPagar cp WHERE 1=1");
        
        if(filtro.getCentroCusto() != null)
            sql.append(" AND cp.idCentroCusto = :centroCusto");
        
        if(filtro.getClinica() != null)
            sql.append(" AND cp.idClinica = :clinica");
        
        if(filtro.getFornecedor() != null)
            sql.append(" AND cp.idFornecedor = :fornecedor");
        
        if(filtro.getProfissional() != null)
            sql.append(" AND cp.idProfissional = :profissional");
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty())
            sql.append(" AND cp.status = :status");
        
        if(filtro.getTipoConta() != null && !filtro.getTipoConta().isEmpty())
            sql.append(" AND cp.tipoConta = :tipoConta");
        
        if(filtro.getDataInicial() != null && filtro.getDataFinal() != null)
            sql.append(" AND cp.dataVencimento BETWEEN :dataInicial AND :dataFinal");
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(filtro.getCentroCusto() != null)
            query.setParameter("centroCusto", filtro.getCentroCusto());
        
        if(filtro.getClinica() != null)
            query.setParameter("clinica", filtro.getClinica());
        
        if(filtro.getFornecedor() != null)
            query.setParameter("fornecedor", filtro.getFornecedor());
        
        if(filtro.getProfissional() != null)
            query.setParameter("profissional", filtro.getProfissional());
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty())
            query.setParameter("status", filtro.getStatus());
        
        if(filtro.getTipoConta() != null && !filtro.getTipoConta().isEmpty())
            query.setParameter("tipoConta", filtro.getTipoConta());
        
        if(filtro.getDataInicial() != null && filtro.getDataFinal() != null){
            query.setParameter("dataInicial", filtro.getDataInicial());
            query.setParameter("dataFinal", filtro.getDataFinal());
        }
        
        return query.getResultList();
    }
    
    public List<ContasPagar> findAllContasVencidas(Date dataVencimento){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT cp FROM ContasPagar cp WHERE cp.dataVencimento < :data AND cp.status = :pagar");
        
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("data", dataVencimento);
        query.setParameter("pagar", StatusContasPagar.A_PAGAR.getStatus());
        return query.getResultList();
    }
    
}
