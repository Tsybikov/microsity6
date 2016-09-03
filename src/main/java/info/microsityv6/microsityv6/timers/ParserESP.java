package info.microsityv6.microsityv6.timers;

import info.microsityv6.microsityv6.entitys.Counter;
import info.microsityv6.microsityv6.entitys.ESPBase;
import info.microsityv6.microsityv6.entitys.Facility;
import info.microsityv6.microsityv6.entitys.SensorsData;
import info.microsityv6.microsityv6.entitys.TariffZone;
import info.microsityv6.microsityv6.entitys.User;
import info.microsityv6.microsityv6.facades.ESPBaseFacade;
import info.microsityv6.microsityv6.facades.SensorsDataFacade;
import info.microsityv6.microsityv6.facades.UserFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;


@Stateless
public class ParserESP {

    @EJB
    UserFacade uf;
    @EJB
    SensorsDataFacade sdf;
    @EJB
    ESPBaseFacade espbf;

    @Schedule(minute = "*", second = "*/10", persistent = false)

    public void myTimer() {
        List<ESPBase> espbs = espbf.findAll();
        for (SensorsData sensorsData : sdf.findAll()) {
            for (ESPBase espb : espbs) {
                if (sensorsData.getSensorId().equals(espb.getEspId())) {
                    addValue(sensorsData);
                    sdf.remove(sensorsData);
                    break;
                }
            }
            ESPBase esp = new ESPBase();
            esp.setEspId(sensorsData.getSensorId());
            esp.setHasClient(false);
            sdf.remove(sensorsData);
        }
    }

    private void addValue(SensorsData sensorsData) {
        List<User> users = uf.findAll();
        for (User user : users) {
            for (Facility fasility : user.getFasilitys()) {
                for (Counter counter : fasility.getCounters()) {
                    if (counter.getEsp_id().equals(sensorsData.getSensorId())) {
                        if (sensorsData.isBool()) {
                            counter.addValue(sensorsData.getValue().equals("1"));
                        } else {
                            counter.addValue(Double.parseDouble(sensorsData.getValue()));
                        }
                    }
                }
            }
        }
    }

    
}
