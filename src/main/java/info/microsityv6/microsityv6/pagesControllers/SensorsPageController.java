package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Sensor;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;

@Named(value = "sensorsPageController")
@SessionScoped
public class SensorsPageController extends PageController implements Serializable {
    
    private List<Sensor> sensors;
    private boolean show;
    
    public SensorsPageController() {
    }
    
    
    private boolean notNullSensorsArray() {
        try {
            if (super.getCurrentFacility().getSensors() != null) {
                return true;
            }
        } catch (NullPointerException ex) {
            return false;
        }
        return false;

    }
    
    public List<Sensor> getSensors() {
        if(notNullSensorsArray())sensors=super.getCurrentFacility().getSensors();
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public boolean isShow() {
        
        return notNullSensorsArray();
    }

    public void setShow(boolean show) {
        this.show = show;
    }
    
    
}
