/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import classes.EspecialidadeClinica;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author valderlei
 */
@Stateless
public class EspecialidadeClinicaFacade extends AbstractFacade<EspecialidadeClinica> {
    @PersistenceContext(unitName = "DentistPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EspecialidadeClinicaFacade() {
        super(EspecialidadeClinica.class);
    }
    
}
