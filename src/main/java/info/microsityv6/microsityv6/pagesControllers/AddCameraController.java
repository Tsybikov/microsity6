package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Camera;
import info.microsityv6.microsityv6.entitys.Log;
import info.microsityv6.microsityv6.enums.LoggerLevel;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named(value = "addCameraController")
@RequestScoped
public class AddCameraController extends PageController{

    private Camera addedCamera;
    
    public AddCameraController() {
    }

    public Camera getAddedCamera() {
        if(addedCamera==null)addedCamera=new Camera();
        return addedCamera;
    }

    public void setAddedCamera(Camera addedCamera) {
        this.addedCamera = addedCamera;
    }
    
    public String addCamera(){
        if(addedCamera.getName().length()>1&&addedCamera.getIp().length()>1){
            super.getCurrentFacility().getCameras().add(addedCamera);
            super.saveChanges();
            logFacade.create(new Log(LoggerLevel.INFO, super.getCurrent().getMainEmail()+ "added new camera"));
            return "cameras.xhtml?faces-redirect=true";
        }
        return "";
    }
    
}
