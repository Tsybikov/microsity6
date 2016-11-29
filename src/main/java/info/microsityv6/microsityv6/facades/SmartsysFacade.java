/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.facades;

import info.microsityv6.microsityv6.entitys.Smartsys;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class SmartsysFacade extends AbstractFacade<Smartsys> {

    @PersistenceContext(unitName = "ua.turboSMS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SmartsysFacade() {
        super(Smartsys.class);
    }
    
}
