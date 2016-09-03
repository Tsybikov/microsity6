/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.timers;

import info.microsityv6.microsityv6.entitys.ESPBase;
import info.microsityv6.microsityv6.entitys.SensorsData;
import info.microsityv6.microsityv6.entitys.User;
import info.microsityv6.microsityv6.facades.ESPBaseFacade;
import info.microsityv6.microsityv6.facades.SensorsDataFacade;
import info.microsityv6.microsityv6.facades.UserFacade;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 *
 * @author Panker-RDP
 */
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
        List <User> users=uf.findAll();
        List <ESPBase> espbs=espbf.findAll();
        for (SensorsData sensorsData : sdf.findAll()) {
            for (ESPBase espb : espbs) {
                if(sensorsData.getSensorId().equals(espb.getEspId())){
                    addValue(sensorsData);
                    sdf.remove(sensorsData);
                    break;
                }
            }
            ESPBase esp=new ESPBase();
            esp.setEspId(sensorsData.getSensorId());
            esp.setHasClient(false);
        }
    }
    
    private void addValue(SensorsData sensorsData){
        
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
