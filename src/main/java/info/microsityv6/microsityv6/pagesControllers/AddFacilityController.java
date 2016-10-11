package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Facility;
import info.microsityv6.microsityv6.entitys.Log;
import info.microsityv6.microsityv6.enums.LoggerLevel;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named(value = "addFacilityController")
@SessionScoped
public class AddFacilityController extends PageController implements Serializable{

    private Facility addedFacility = new Facility();

    public AddFacilityController() {
    }

    public String createFacility() {
        if (addedFacility != null && addedFacility.getTitle().length() > 0) {
            super.getUserController().getCurrent().addFacility(addedFacility);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Объект успешно добавлен"));
            super.logFacade.create(new Log(LoggerLevel.INFO, super.getCurrent().getMainEmail()+" added new facility "+addedFacility.getTitle()));
            super.saveChanges();
            addedFacility=new Facility();
            return "home.xhtml?faces-redirect=true";
            
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Что-то пошло не так"));
        return "";
    }

    public void deleteFacility(Facility facility) {
        if (facility == null) {
            addedFacility = new Facility();
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("@all");

        }
        int i = 0;
        for (Facility searched : super.getUserController().getCurrent().getFasilitys()) {

            if (facility.equals(searched)) {
                break;
            }
            i++;
        }
        super.getUserController().getCurrent().getFasilitys().remove(i);
        super.logFacade.create(new Log(LoggerLevel.INFO, super.getCurrent().getMainEmail()+" delete facility "+facility.getTitle()));
            
        super.saveChanges();        
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
