/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Facility;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Panker-RDP
 */
@Named(value = "currentViewController")
@SessionScoped
public class CurrentViewController extends PageController implements Serializable {

    /**
     * Creates a new instance of CurrentViewController
     */
    public CurrentViewController() {
    }
    
    private Facility currentView;
    
    
    public Facility getCurrentView() {
        if(currentView==null){
            if(!super.getUserController().getCurrent().getFasilitys().isEmpty()){
                currentView=super.getUserController().getCurrent().getFasilitys().get(0);
            }
        }
        return currentView;
    }

    public void setCurrentView(Facility currentView) {
        this.currentView = currentView;
    }
    
}
