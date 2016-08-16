/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.facades;

import info.microsityv6.microsityv6.entitys.Facility;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Panker-RDP
 */
@Stateless
public class FacilityFacade extends AbstractFacade<Facility> {

    @PersistenceContext(unitName = "info.microsityv6_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FacilityFacade() {
        super(Facility.class);
    }
    
}
