/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.pagesControllers;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Panker-RDP
 */
@Named(value = "camerasPageController")
@SessionScoped
public class CamerasPageController extends PageController implements Serializable{

    private boolean show;
    private boolean haveCameras;

    public CamerasPageController() {
    }

    public boolean isShow() {
        if (super.getCurrentFacility() == null) {
            return false;
        }
        return true;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public boolean isHaveCameras() {
        if(super.getCurrentFacility()!=null){
            if(!super.getCurrentFacility().getCameras().isEmpty()){
                return true;
            }
        }
        return false;
    }

    public void setHaveCameras(boolean haveCameras) {
        this.haveCameras = haveCameras;
    }
    
    

}
