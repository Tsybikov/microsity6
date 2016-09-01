/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Camera;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Panker-RDP
 */
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
            return "cameras.xhtml?faces-redirect=true";
        }
        return "";
    }
    
}
