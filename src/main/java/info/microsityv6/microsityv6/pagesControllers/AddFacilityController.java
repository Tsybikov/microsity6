/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Facility;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Panker-RDP
 */
@Named(value = "addFacilityController")
@SessionScoped
public class AddFacilityController extends PageController implements Serializable{

    private Facility addedFacility = new Facility();

    public AddFacilityController() {
    }

    public String createFacility() {
        if (addedFacility != null && addedFacility.getTitle().length() > 0) {
            super.getUserController().getCurrent().addFacility(addedFacility);
            super.getUserController().saveCurrent(null);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Объект успешно добален"));
            super.getUserController().updateBean();
            return "home.xhtml?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Что-то пошло не так"));
        return "";
    }

    public void deleteFacility(Facility facility) {
        if (facility == null) {
            addedFacility = new Facility();
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("all");//TODO: Вставить ухо

        }
        int i = 0;
        for (Facility searched : super.getUserController().getCurrent().getFasilitys()) {

            if (facility.equals(searched)) {
                break;
            }
            i++;
        }
        super.getUserController().getCurrent().getFasilitys().remove(i);
        super.getUserController().saveCurrent(null);
        super.getUserController().updateBean();
    }

    public Facility getAddedFacility() {
        if (addedFacility == null) {
            addedFacility = new Facility();
        }
        return addedFacility;
    }

    public void setAddedFacility(Facility addedFacility) {
        this.addedFacility = addedFacility;
    }

}
