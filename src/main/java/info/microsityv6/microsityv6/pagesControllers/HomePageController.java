/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Facility;
import java.io.Serializable;
import javax.annotation.PostConstruct;
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
    public HomePageController() {
        
    }

    public boolean isNeedRender() {
        if(super.getCurrentFacility()==null){
            if(!super.getUserController().getCurrent().getFasilitys().isEmpty()){
                 super.setCurrentFacility(super.getUserController().getCurrent().getFasilitys().get(0));
                 return true;
            }
        }
        return false;
    }

    public void setNeedRender(boolean needRender) {
        this.needRender = needRender;
    }
    
    
    
}
