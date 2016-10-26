package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Sensor;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Panker-RDP
 */
@Named(value = "setupSensorController")
@SessionScoped
public class SetupSensorController implements Serializable {
    
    private Sensor setupSensor;
    public SetupSensorController() {
    }

    public Sensor getSetupSensor() {
        return setupSensor;
    }

    public void setSetupSensor(Sensor setupSensor) {
        this.setupSensor = setupSensor;
    }
    
    
}
