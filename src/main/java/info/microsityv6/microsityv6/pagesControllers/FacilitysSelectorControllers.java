package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Facility;
import info.microsityv6.microsityv6.entitysControllers.UserController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

@Named(value = "facilitysSelectorControllers")
@SessionScoped
public class FacilitysSelectorControllers implements Serializable {

    @Inject
    private UserController userController;
    private Facility selected;
    private boolean hasFacilitys;
    private List<Facility> facilitys;

    public FacilitysSelectorControllers() {
    }

    public boolean isHasFacilitys() {
        return !userController.getCurrent().getFasilitys().isEmpty();
    }

    public void setHasFacilitys(boolean hasFacilitys) {
        this.hasFacilitys = hasFacilitys;
    }

    public Facility getSelected() {
        if (selected == null && isHasFacilitys()) {
            selected = userController.getCurrent().getFasilitys().get(0);
        }
        return selected;
    }

    public void setSelected(Facility selected) {
        this.selected = selected;
    }

    public List<Facility> getFacilitys() {
        if (isHasFacilitys()) {
            return userController.getCurrent().getFasilitys();
        }
        return new ArrayList<Facility>();
    }

    public void setFacilitys(List<Facility> facilitys) {
        this.facilitys = facilitys;
    }
    
    public void removeSelected(Facility facility){
        userController.getCurrent().getFasilitys().remove(facility);
        userController.saveCurrent();
    }
}
