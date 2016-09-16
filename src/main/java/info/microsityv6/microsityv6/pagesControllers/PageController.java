package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Facility;
import info.microsityv6.microsityv6.entitys.User;
import info.microsityv6.microsityv6.entitysControllers.UserController;
import java.io.Serializable;
import javax.inject.Inject;

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
        userController.saveCurrent();
    }

    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }
    
    
}
