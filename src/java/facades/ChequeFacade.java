/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Cheque;
import filters.ChequeFilter;
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
public class ChequeFacade extends AbstractFacade<Cheque> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChequeFacade() {
        super(Cheque.class);
    }
    
    public List<Cheque> findByFilter(ChequeFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c FROM Cheque c WHERE 1=1");
        
        if(filtro.getPaciente() != null)
            sql.append(" AND c.idPaciente = :paciente");
        
        if(filtro.getNumCheque()!= null)
            sql.append(" AND c.numCheque = :numCheque");
        
        if(filtro.getNomeCheque()!= null && !filtro.getNomeCheque().isEmpty())
            sql.append(" AND c.nomeCheque = :nomeCheque");
        
        if(filtro.getIsChequeBaixado() != null && !filtro.getIsChequeBaixado().isEmpty())
            sql.append(" AND c.isChequeBaixado = :isChequeBaixado");
        
        if(filtro.getDataInicialVencimento()!= null && filtro.getDataFinalVencimento() != null)
            sql.append(" AND c.dataVencimento BETWEEN :dataInicialVencimento AND :dataFinalVencimento");
        
        if(filtro.getDataInicialEmissao()!= null && filtro.getDataFinalEmissao()!= null)
            sql.append(" AND c.dataEmissao BETWEEN :dataInicialEmissao AND :dataFinalEmissao");
        
        if(filtro.getDataInicialBaixa()!= null && filtro.getDataFinalBaixa()!= null)
            sql.append(" AND c.dataBaixa BETWEEN :dataInicialBaixa AND :dataFinalBaixa");
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(filtro.getPaciente() != null)
            query.setParameter("paciente", filtro.getPaciente());
        
        if(filtro.getNumCheque()!= null)
            query.setParameter("numCheque", filtro.getNumCheque());
        
        if(filtro.getNomeCheque()!= null && !filtro.getNomeCheque().isEmpty())
            query.setParameter("nomeCheque", filtro.getNomeCheque());
        
        if(filtro.getIsChequeBaixado() != null && !filtro.getIsChequeBaixado().isEmpty())
            query.setParameter("isChequeBaixado", filtro.getIsChequeBaixado());
        
        if(filtro.getDataInicialVencimento()!= null && filtro.getDataFinalVencimento() != null){
            query.setParameter("dataInicialVencimento", filtro.getDataInicialVencimento());
            query.setParameter("dataFinalVencimento", filtro.getDataFinalVencimento());
        }
        
        if(filtro.getDataInicialEmissao()!= null && filtro.getDataFinalEmissao()!= null){
            query.setParameter("dataInicialEmissao", filtro.getDataInicialEmissao());
            query.setParameter("dataFinalEmissao", filtro.getDataFinalEmissao());
        }
        
        if(filtro.getDataInicialBaixa()!= null && filtro.getDataFinalBaixa()!= null){
            query.setParameter("dataInicialBaixa", filtro.getDataInicialBaixa());
            query.setParameter("dataFinalBaixa", filtro.getDataFinalBaixa());
        }
        
        return query.getResultList();
    }
}
