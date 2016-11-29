package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Counter;
import info.microsityv6.microsityv6.entitys.Log;
import info.microsityv6.microsityv6.entitys.Sensor;
import info.microsityv6.microsityv6.enums.LoggerLevel;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Panker-RDP
 */
@Named(value = "setupSensorController")
@SessionScoped
public class SetupSensorController extends PageController implements Serializable {
    
    private Sensor setupSensor;
    public SetupSensorController() {
    }

    public Sensor getSetupSensor() {
        return setupSensor;
    }

    public void setSetupSensor(Sensor setupSensor) {
        this.setupSensor = setupSensor;
    }
    
    public String saveCounterSettings() {
        int index = -1;
        for (Sensor sensor : super.getCurrentFacility().getSensors()) {
            if (sensor.equals(setupSensor)) {
                index++;
                break;
            }
            index++;
        }
        if (index != -1) {
            super.getCurrentFacility().getSensors().set(index, setupSensor);
            super.saveChanges();
            logFacade.create(new Log(LoggerLevel.INFO,super.getCurrent().getMainEmail()+ " change settings Sensor ID:"+index));
            return "counters.xhtml?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Что-то не так :( "));
        return "setupcounter.xhtml?faces-redirect=true";
    }

    public String deleteCounter() {
        super.getCurrentFacility().getSensors().remove(setupSensor);
        setupSensor = new Sensor();
        return "sensors.xhtml?faces-redirect=true";
    }

    public String cancelCounterSetup() {
        setupSensor = new Sensor();
        return "sensors.xhtml?faces-redirect=true";
    }
    
    
}
