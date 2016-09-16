/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.timers;

import info.microsityv6.microsityv6.entitys.Counter;
import info.microsityv6.microsityv6.entitys.ESPBase;
import info.microsityv6.microsityv6.entitys.Facility;
import info.microsityv6.microsityv6.entitys.SensorsData;
import info.microsityv6.microsityv6.entitys.SensorsDataReaded;
import info.microsityv6.microsityv6.entitys.TariffZone;
import info.microsityv6.microsityv6.entitys.User;
import info.microsityv6.microsityv6.enums.CounterType;
import info.microsityv6.microsityv6.facades.ESPBaseFacade;
import info.microsityv6.microsityv6.facades.SensorsDataFacade;
import info.microsityv6.microsityv6.facades.SensorsDataReadedFacade;
import info.microsityv6.microsityv6.facades.UserFacade;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Named;

/**
 *
 * @author Panker-RDP
 */
@Named("Timer")
@Singleton
@Startup
public class ParseData implements Serializable {

    @EJB
    UserFacade uf;
    @EJB
    SensorsDataFacade sdf;
    @EJB
    ESPBaseFacade espbf;
    @EJB
    SensorsDataReadedFacade readedFacade;
    List<ESPBase> espbs;

    @PostConstruct
    public void init() {
        System.out.println("Started");
    }

    @PreDestroy
    public void deInit() {
        System.out.println("Stoped");
    }

    @Schedule(minute = "*")
    public void parseSensorsData() {
        System.out.println("Server started");

        Iterator sds = sdf.findAll().iterator();
        while (sds.hasNext()) {
            SensorsData sd = (SensorsData) sds.next();
            if (!sd.getWasRead()) {
                for (User user : uf.findAll()) {
                    for (Facility fasility : user.getFasilitys()) {
                        for (Counter counter : fasility.getCounters()) {
                            if (counter.getEsp_id().equals(sd.getSensorId())) {
                                if (sd.getIsBool()) {
                                    counter.addValue(sd.getValue().equals("1"));
                                    sd.setWasRead(true);
                                } else {
                                    counter.addValue(Double.parseDouble(sd.getValue()));
                                    sd.setWasRead(true);
                                }
                            }
                            uf.edit(user);
                        }
                    }
                    
                }
                sdf.edit(sd);
                /*Iterator users = uf.findAll().iterator();
                while (users.hasNext()) {
                    User user = (User) users.next();
                    Iterator facilitys = user.getFasilitys().iterator();
                    while (facilitys.hasNext()) {
                        Facility facility = (Facility) facilitys.next();
                        Iterator counters = facility.getCounters().iterator();
                        while (counters.hasNext()) {
                            Counter counter = (Counter) counters.next();
                            if (counter.getEsp_id().equals(sd.getSensorId())) {
                                if (sd.getIsBool()) {
                                    counter.addValue(sd.getValue().equals("1"));
                                    sd.setWasRead(true);
                                } else {
                                    counter.addValue(Double.parseDouble(sd.getValue()));
                                    sd.setWasRead(true);
                                }
                            }
                            int counterIndex = facility.getCounters().indexOf(counter);
                            facility.getCounters().set(counterIndex, counter);
                        }
                        int facilityIndex = user.getFasilitys().indexOf(facility);
                        user.getFasilitys().set(facilityIndex, facility);
                    }
                    uf.edit(user);
                }*/
            } else {
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
            }
        }
        System.out.println("Parse data finished");
    }

    @Schedule(dayOfWeek = "*", hour = "23")
    public void cleanSensorsData() {
        System.out.println("Started data clean");
        int mount = 0;
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
            if (sd.getDt().before(xDay.getTime())) {
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
                mount++;
            }
        }
        System.out.println("Cleaned " + mount + " records");
    }

    // Not use in future
    private Counter generateStartData(Counter counter) {
        if (counter.getCounterType().equals(CounterType.WATT)) {
            TariffZone day = new TariffZone();
            day.setNameTariff("День");
            day.setHourStart(7);
            day.setMinuteStart(0);
            day.setHourEnd(23);
            day.setMinuteEnd(59);
            Random rnd = new Random();
            day.setStartValue(rnd.nextInt(13500));
            counter.getTariffZones().add(day);
            TariffZone night = new TariffZone();
            night.setNameTariff("Ноч");
            night.setHourStart(23);
            night.setMinuteStart(00);
            night.setHourEnd(7);
            night.setMinuteEnd(59);
            night.setStartValue(rnd.nextInt(13500));
            counter.getTariffZones().add(night);
        }
        if (counter.getCounterType().equals(CounterType.WATER)) {
            TariffZone allDay = new TariffZone();
            allDay.setNameTariff("Весь день");
            allDay.setHourStart(0);
            allDay.setMinuteStart(0);
            allDay.setHourEnd(24);
            allDay.setMinuteEnd(59);
            Random rnd = new Random();
            allDay.setMinuteStart(rnd.nextInt(13500));
            counter.getTariffZones().add(allDay);
        }
        if (counter.getCounterType().equals(CounterType.GAS)) {
            TariffZone allDay = new TariffZone();
            allDay.setNameTariff("Весь день");
            allDay.setHourStart(0);
            allDay.setMinuteStart(0);
            allDay.setHourEnd(24);
            allDay.setMinuteEnd(59);
            Random rnd = new Random();
            allDay.setMinuteStart(rnd.nextInt(13500));
            counter.getTariffZones().add(allDay);
        }
        if (counter.getCounterType().equals(CounterType.WARM)) {
            TariffZone allDay = new TariffZone();
            allDay.setNameTariff("Весь день");
            allDay.setHourStart(0);
            allDay.setMinuteStart(0);
            allDay.setHourEnd(24);
            allDay.setMinuteEnd(59);
            Random rnd = new Random();
            allDay.setMinuteStart(rnd.nextInt(13500));
            counter.getTariffZones().add(allDay);
        }
        return counter;
    }

    private Counter generateNextData(Counter counter) {
        Random rnd = new Random();
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.SECOND) == 1
                || cal.get(Calendar.SECOND) == 15
                || cal.get(Calendar.SECOND) == 30
                || cal.get(Calendar.SECOND) == 45) {
            counter.addValue((rnd.nextBoolean() && rnd.nextBoolean() ? rnd.nextInt(5) : 0));
            System.out.println("Added new data");
        }
        return counter;
    }

}
