/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Facility;
import info.microsityv6.microsityv6.entitys.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Panker-RDP
 */
@Named(value = "facilitysSelectorControllers")
@RequestScoped
public class FacilitysSelectorControllers extends PageController{
    
    private boolean hasFacilitys;
    private List<Facility> facilitys;
    private User current;
    private boolean needScrool;
    
    public FacilitysSelectorControllers() {
    }
    
    @PostConstruct
    public void init(){
        this.current=super.getUserController().getCurrent();
        if(current.getFasilitys().isEmpty())hasFacilitys=false;
        else{
            hasFacilitys=true;
            facilitys=current.getFasilitys();
        }
    }

    public boolean isHasFacilitys() {
        return hasFacilitys;
    }

    public void setHasFacilitys(boolean hasFacilitys) {
        this.hasFacilitys = hasFacilitys;
    }

    public List<Facility> getFacilitys() {
        if(facilitys==null)facilitys=new ArrayList<>();
        return facilitys;
    }

    public void setFacilitys(List<Facility> facilitys) {
        this.facilitys = facilitys;
    }

    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }

    public boolean isNeedScrool() {
        if(!getFacilitys().isEmpty()){
            if(getFacilitys().size()>4) return true;
        }
        return false;
    }

    public void setNeedScrool(boolean needScrool) {
        this.needScrool = needScrool;
    }
    
    
    
}
