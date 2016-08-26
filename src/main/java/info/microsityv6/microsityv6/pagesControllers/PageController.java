/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Facility;
import info.microsityv6.microsityv6.entitysControllers.UserController;
import info.microsityv6.microsityv6.facades.FacilityFacade;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author Panker-RDP
 */
public class PageController {
    @Inject
    private UserController userController;
    private Facility currentFacility;
    @EJB
    private FacilityFacade ff;
    
    
    
    
    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public Facility getCurrentFacility() {
        return currentFacility;
    }

    public void setCurrentFacility(Facility currentFacility) {
        this.currentFacility = currentFacility;
    }

    public FacilityFacade getFf() {
        return ff;
    }

    public void setFf(FacilityFacade ff) {
        this.ff = ff;
    }
    
}
