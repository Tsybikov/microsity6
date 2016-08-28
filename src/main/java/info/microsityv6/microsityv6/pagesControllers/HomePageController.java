/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.facades.FacilityFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Panker-RDP
 */
@Named(value = "homePageController")
@SessionScoped
public class HomePageController extends PageController implements Serializable{
    
    private boolean needRender=false;
    private boolean haveCameras;
    private boolean haveControllers;
    private boolean haveCounters;
    private boolean haveSensors;
    @EJB
    private FacilityFacade facilityFacade;
    
    public HomePageController() {
        
    }

    public boolean isNeedRender() {
        
        if(super.getCurrentFacility()!=null)return true;
        return false;
    }

    public void setNeedRender(boolean needRender) {
        this.needRender = needRender;
    }

    public boolean isHaveCameras() {
        if(super.getCurrentFacility().getCameras().isEmpty()) return false;
        else{
            return true;
        }
        
    }

    public void setHaveCameras(boolean haveCameras) {
        this.haveCameras = haveCameras;
    }

    public boolean isHaveControllers() {
        if(super.getCurrentFacility().getControllers().isEmpty()) return false;
        else{
            return true;
        }
    }

    public void setHaveControllers(boolean haveControllers) {
        this.haveControllers = haveControllers;
    }

    public boolean isHaveCounters() {
        if(super.getCurrentFacility().getCounters().isEmpty()) return false;
        else{
            return true;
        }
    }

    public void setHaveCounters(boolean haveCounters) {
        this.haveCounters = haveCounters;
    }

    public boolean isHaveSensors() {
        if(super.getCurrentFacility().getSensors().isEmpty()) return false;
        else{
            return true;
        }
    }

    public void setHaveSensors(boolean haveSensors) {
        this.haveSensors = haveSensors;
    }
    
    public void saveFacilityChange(){
        super.saveChanges();
    }
    
    public void deleteFacility(){
        facilityFacade.remove(super.getCurrentFacility());
    }
    
    
    
    
}
