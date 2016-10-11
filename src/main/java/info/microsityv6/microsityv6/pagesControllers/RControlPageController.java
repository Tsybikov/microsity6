/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Controller;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Panker-RDP
 */
@Named(value = "rControlPageController")
@SessionScoped
public class RControlPageController extends PageController implements Serializable {
    
    private List<Controller> controllers;
    private boolean show;
    public RControlPageController() {
    }
    
    private boolean notNullControllersArray() {
        try {
            if (super.getCurrentFacility().getControllers() != null) {
                return true;
            }
        } catch (NullPointerException ex) {
            return false;
        }
        return false;

    }

    public List<Controller> getControllers() {
        if(notNullControllersArray())controllers=super.getCurrentFacility().getControllers();
        else controllers=new ArrayList<>();
        return controllers;
    }

    public void setControllers(List<Controller> controllers) {
        this.controllers = controllers;
    }

    public boolean isShow() {
        return notNullControllersArray();
    }

    public void setShow(boolean show) {
        this.show = show;
    }
    
    
}
