/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.Agendamento;
import classes.Profissional;
import filters.AgendamentoFilter;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import utils.DatasEHoras;

/**
 *
 * @author valderlei
 */
@Stateless
public class AgendamentoFacade extends AbstractFacade<Agendamento> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AgendamentoFacade() {
        super(Agendamento.class);
    }
    
    public List<Agendamento> findByFilter(AgendamentoFilter filtro){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a FROM Agendamento a WHERE 1=1");
        
        if(filtro.getPaciente() != null)
            sql.append(" AND a.idPaciente = :paciente");
        
        if(filtro.getProcedimento() != null)
            sql.append(" AND a.idProcedimento = :procedimento");
        
        if(filtro.getProfissional() != null)
            sql.append(" AND a.idProfissional = :profissional");
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty())
            sql.append(" AND a.status = :status");
        
        Query query = getEntityManager().createQuery(sql.toString());
        
        if(filtro.getPaciente() != null)
            query.setParameter("paciente", filtro.getPaciente());
        
        if(filtro.getProcedimento() != null)
            query.setParameter("procedimento", filtro.getProcedimento());
        
        if(filtro.getProfissional() != null)
            query.setParameter("profissional", filtro.getProfissional());
        
        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty())
            query.setParameter("status", filtro.getStatus());
        
        return query.getResultList();
    }
    
    public List<Agendamento> verificaDisponibilidade(Date dataInicial,Profissional profissional){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM AGENDAMENTO WHERE CAST(data AS DATE) = ? AND idProfissional = ?");
        sql.append(" AND status IN ('Agendado', 'Atendido', 'Encaixe', 'Remarcado','Confirmado')");
        Query query = getEntityManager().createNativeQuery(sql.toString(),Agendamento.class);
        query.setParameter(1, DatasEHoras.converterData(dataInicial));
        query.setParameter(2, profissional.getId());
        return query.getResultList();
    }
    
}
