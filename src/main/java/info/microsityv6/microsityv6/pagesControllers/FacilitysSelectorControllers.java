/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Facility;
import info.microsityv6.microsityv6.entitys.User;
import info.microsityv6.microsityv6.entitysControllers.UserController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Panker-RDP
 */
@Named(value = "facilitysSelectorControllers")
@SessionScoped
public class FacilitysSelectorControllers implements Serializable{
    
    @Inject
    private UserController userController;
    private Facility selected;
    private boolean hasFacilitys;
    private List <Facility> facilitys;
    
    public FacilitysSelectorControllers() {
    }
    
    
    public boolean isHasFacilitys() {
        if(userController.getCurrent().getFasilitys().isEmpty()) return false;
        return true;
    }

    public void setHasFacilitys(boolean hasFacilitys) {
        this.hasFacilitys = hasFacilitys;
    }

    public Facility getSelected() {
        if(selected==null&&isHasFacilitys()){
            selected=userController.getCurrent().getFasilitys().get(0);
        }
        return selected;
    }

    public void setSelected(Facility selected) {
        this.selected = selected;
    }

    public List<Facility> getFacilitys() {
        if(isHasFacilitys())
        return userController.getCurrent().getFasilitys();
        return new ArrayList<Facility>();
    }

    public void setFacilitys(List<Facility> facilitys) {
        this.facilitys = facilitys;
    }
    
    
    
    
}
