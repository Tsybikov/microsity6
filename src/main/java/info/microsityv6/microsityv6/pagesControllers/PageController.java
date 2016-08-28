/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Facility;
import info.microsityv6.microsityv6.entitys.User;
import info.microsityv6.microsityv6.entitysControllers.UserController;
import info.microsityv6.microsityv6.facades.FacilityFacade;
import info.microsityv6.microsityv6.facades.UserFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Panker-RDP
 */
public class PageController implements Serializable{
    @Inject
    private UserController userController;
    @Inject
    private FacilitysSelectorControllers fsc;
    private Facility currentFacility;
    private User current;
    
    
    
    
    public Facility getCurrentFacility() {
        return fsc.getSelected();        
    }

    public void setCurrentFacility(Facility currentFacility) {
        fsc.setSelected(currentFacility);
    }
    
    public User getCurrent(){
        return userController.getCurrent();
    }
    
    public void setCurrent(User current){
        userController.setCurrent(current);
    }
    
    public void saveChanges(){
        userController.saveCurrent(getCurrent());
    }

    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }
    
    
}
