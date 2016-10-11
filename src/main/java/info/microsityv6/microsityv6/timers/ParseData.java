package info.microsityv6.microsityv6.timers;

import info.microsityv6.microsityv6.entitys.Counter;
import info.microsityv6.microsityv6.entitys.ESP;
import info.microsityv6.microsityv6.entitys.ESPBase;
import info.microsityv6.microsityv6.entitys.Facility;
import info.microsityv6.microsityv6.entitys.Log;
import info.microsityv6.microsityv6.entitys.Pin;
import info.microsityv6.microsityv6.entitys.SensorsData;
import info.microsityv6.microsityv6.entitys.SensorsDataReaded;
import info.microsityv6.microsityv6.entitys.User;
import info.microsityv6.microsityv6.enums.CounterType;
import info.microsityv6.microsityv6.enums.DeviceType;
import info.microsityv6.microsityv6.enums.LoggerLevel;
import info.microsityv6.microsityv6.facades.ESPFacade;
import info.microsityv6.microsityv6.facades.LogFacade;
import info.microsityv6.microsityv6.facades.SensorsDataFacade;
import info.microsityv6.microsityv6.facades.SensorsDataReadedFacade;
import info.microsityv6.microsityv6.facades.UserFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Named;

@Named("Timer")
@Singleton
@Startup
public class ParseData implements Serializable {

    @EJB
    UserFacade uf;
    @EJB
    SensorsDataFacade sdf;
    @EJB
    ESPFacade espf;
    @EJB
    SensorsDataReadedFacade readedFacade;
    @EJB
    LogFacade logFacade;
    List<ESPBase> espbs;
    
    @PostConstruct
    public void init() {
        System.out.println("Timer start");        
    }

    @PreDestroy
    public void deInit() {
        System.out.println("Timer stoped");
    }

    @Schedule(dayOfWeek = "*", hour = "*", minute = "*/10")
    public void parseSensorsData() {
        Iterator sds = sdf.findAll().iterator();
        while (sds.hasNext()) {
            SensorsData sd = (SensorsData) sds.next();
            if(sd.getWasRead()==null)sd.setWasRead(Boolean.FALSE);
            if (!sd.getWasRead()) {
                if (!haveOwner(sd)) {
                    checkMayBeItIsNewDevice(sd);
                }
                sdf.edit(sd);
            } else {
                setAsReadSensorsData(sd);
            }
        }
        int size = sdf.findAll().size();
        logFacade.create(new Log(LoggerLevel.INFO, "Parsed " + size + " element"));

    }
    
    private boolean haveOwner(SensorsData sd) {
        for (User user : uf.findAll()) {
            for (Facility fasility : user.getFasilitys()) {
                for (Counter counter : fasility.getCounters()) {
                    if (counter.getEsp_id().equals(sd.getSensorId())) {
                        if (sd.getIsBool()) {
                            counter.addValue(sd.getValue().equals("1"), sd.getDt());
                            sd.setWasRead(true);
                            return true;
                        } else {
                            counter.addValue(Double.parseDouble(sd.getValue()), sd.getDt());
                            sd.setWasRead(true);
                            return true;
                        }
                    }
                    uf.edit(user);
                }
            }

        }
        return false;
    }
    
    private void checkMayBeItIsNewDevice(SensorsData sd) {
        if (!espf.findAll().isEmpty()) {
            if (!existESP(sd)) {
                addNewESP(sd);                
            }else{
                sd.setWasRead(true);
                sdf.edit(sd);
            }
        }
    }

    private void setAsReadSensorsData(SensorsData sd) {
        SensorsDataReaded sdr = new SensorsDataReaded();
        sdr.setBool(sd.getIsBool());
        sdr.setDt(sd.getDt());
        sdr.setIsAction(sd.getIsAction());
        sdr.setPinNum(sd.getPinNum());
        sdr.setSensorId(sd.getSensorId());
        sdr.setTiming(sd.getTiming());
        sdr.setValue(sd.getValue());
        sdr.setWasRead(sd.getWasRead());
        sd.setWasRead(Boolean.TRUE);
        sdf.edit(sd);
        sdf.remove(sd);
        readedFacade.create(sdr);
    }

    private boolean existESP(SensorsData sd) {
        for (ESP esp : espf.findAll()) {
            if (esp.getEspId().equals(sd.getSensorId())) {
                if (!esp.getPins().isEmpty()) {
                    for (Pin pin : esp.getPins()) {
                        if (sd.getPinNum() == pin.getPin_num()) {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    private void addNewESP(SensorsData sd) {
        Iterator espIt = espf.findAll().iterator();
        while (espIt.hasNext()) {
            ESP checkesp = (ESP) espIt.next();
            if (!itIsPinOfExistESP(checkesp, sd)){
                ESP newEsp = new ESP();
                newEsp.setEspId(sd.getSensorId());
                newEsp.setInputDate(new Date());
                List<Pin> pins = new ArrayList<>();
                Pin nPin = new Pin();
                nPin.setPin_num(sd.getPinNum());
                nPin.setCounterType(CounterType.NOT_SET_UP);
                nPin.setType(DeviceType.NOT_SET_UP);
                pins.add(nPin);
                newEsp.setPins(pins);
                newEsp.setStatus(true);
                newEsp.setFirmware_id("Have not information");
                espf.create(newEsp);
                logFacade.create(new Log(LoggerLevel.INFO, "System found and add new esp with ID: " + newEsp.getEspId()));
            }

        }
    }

    private boolean itIsPinOfExistESP(ESP checkesp, SensorsData sd) {
        if (checkesp.getEspId().equals(sd.getSensorId())) {
            Iterator pinIt = checkesp.getPins().iterator();
            while (pinIt.hasNext()) {
                Pin pin = (Pin) pinIt.next();
                if (pin.getPin_num() == sd.getPinNum()) {
                    return true;//Pin is allready exist;
                }
            }
            if(!checkesp.getPins().isEmpty()){
                Pin nPin = new Pin();
                nPin.setPin_num(sd.getPinNum());
                nPin.setCounterType(CounterType.NOT_SET_UP);
                nPin.setType(DeviceType.NOT_SET_UP);
                checkesp.getPins().add(nPin);
                logFacade.create(new Log(LoggerLevel.INFO, "Added new Pin for ESP: "+checkesp.getEspId()));
                return true;//New Pin added
            }
            
        }
        return false;
    }

    @Schedule(dayOfWeek = "*", hour = "23")
    public void cleanSensorsData() {
        int count = 0;
        Iterator sds = sdf.findAll().iterator();
        while (sds.hasNext()) {
            SensorsData sd = (SensorsData) sds.next();
            Calendar xDay = Calendar.getInstance();
            if (xDay.get(Calendar.WEEK_OF_MONTH) == 1) {//If now a first week of month
                if (xDay.get(Calendar.MONTH) == 1) {//If now January
                    int year = xDay.get(Calendar.YEAR);
                    xDay.set(Calendar.YEAR, year - 1);
                    xDay.set(Calendar.MONTH, Calendar.DECEMBER);
                    xDay.set(Calendar.WEEK_OF_MONTH, 4);//Set as last week of previous year
                } else {//If not January
                    int month = xDay.get(Calendar.MONTH);
                    xDay.set(Calendar.MONTH, month - 1);
                    xDay.set(Calendar.WEEK_OF_MONTH, 4);//set as last week of previous month
                }
            } else {
                int week = xDay.get(Calendar.WEEK_OF_MONTH) - 1;
                xDay.set(Calendar.WEEK_OF_MONTH, week);
            }
            Calendar sdCalendar = Calendar.getInstance();
            sdCalendar.setTime(sd.getDt());
            if (sd.getDt().before(xDay.getTime()) && sd.getWasRead()) {
                SensorsDataReaded sdr = new SensorsDataReaded();
                sdr.setBool(sd.getIsBool());
                sdr.setDt(sd.getDt());
                sdr.setIsAction(sd.getIsAction());
                sdr.setPinNum(sd.getPinNum());
                sdr.setSensorId(sd.getSensorId());
                sdr.setTiming(sd.getTiming());
                sdr.setValue(sd.getValue());
                sdr.setWasRead(sd.getWasRead());
                readedFacade.create(sdr);
                sdf.remove(sd);
                count++;
            }
        }
        logFacade.create(new Log(LoggerLevel.INFO, "Cleaned " + count + " element"));
    }

    

    

}
