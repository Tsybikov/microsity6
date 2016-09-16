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
import info.microsityv6.microsityv6.entitys.TariffZone;
import info.microsityv6.microsityv6.entitys.User;
import info.microsityv6.microsityv6.enums.CounterType;
import info.microsityv6.microsityv6.facades.ESPBaseFacade;
import info.microsityv6.microsityv6.facades.SensorsDataFacade;
import info.microsityv6.microsityv6.facades.SensorsDataReadedFacade;
import info.microsityv6.microsityv6.facades.UserFacade;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author Panker-RDP
 */
@Named(value = "zalipuhaBean")
@ApplicationScoped
public class ZalipuhaBean {

    @EJB
    UserFacade uf;
    @EJB
    SensorsDataFacade sdf;
    @EJB
    ESPBaseFacade espbf;
    @EJB
    SensorsDataReadedFacade readedFacade;
    List<ESPBase> espbs;

    public ZalipuhaBean() {
    }

    @PostConstruct

    public void init() {
        start();
        /*
        while (true) {
            espbs = espbf.findAll();
            if (espbs == null) {
                espbs = new ArrayList<>();
            }
            for (SensorsData sensorsData : sdf.findAll()) {
                if (espbs.isEmpty()) {
                    getFirst(sensorsData);
                }
                for (ESPBase espb : espbf.findAll()) {
                    if (sensorsData.getSensorId().equals(espb.getEspId())) {
                        if(!sensorsData.isWasRead()&&addValue(sensorsData)){
                            espb.setHasClient(true);
                            espbf.edit(espb);
                        }
                        sensorsData.setWasRead(true);
                        sdf.edit(sensorsData);
                        System.out.println("Data is succesfull read");
                        break;
                    } else {
                        ESPBase esp = new ESPBase();
                        esp.setEspId(sensorsData.getSensorId());
                        esp.setHasClient(false);
                        espbs.add(esp);
                        espbf.create(esp);
                    }
                }
                if(sensorsData.isWasRead()){
                    sdf.remove(sensorsData);
                    readedFacade.create((SensorsDataReaded)sensorsData);
                }

                sdf.edit(sensorsData);
            }
        }*/

    }

    private boolean addValue(SensorsData sensorsData) {
        List<User> users = uf.findAll();
        for (User user : users) {
            for (Facility fasility : user.getFasilitys()) {
                for (Counter counter : fasility.getCounters()) {
                    if (counter.getEsp_id().equals(sensorsData.getSensorId())) {
                        if (sensorsData.getIsBool()) {
                            counter.addValue(sensorsData.getValue().equals("1"));
                            System.out.println("Data added");
                            uf.edit(user);
                            return true;
                        } else {
                            counter.addValue(Double.parseDouble(sensorsData.getValue()));
                            System.out.println("Data added");
                            uf.edit(user);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void getFirst(SensorsData data) {
        ESPBase esp = new ESPBase();
        esp.setEspId(data.getSensorId());
        esp.setHasClient(false);
        espbs.add(esp);
        espbf.create(esp);
    }

    public String start() {
        System.out.println("Server started");
        while (true) {
            for (User user : uf.findAll()) {
                for (Facility fasility : user.getFasilitys()) {
                    for (Counter counter : fasility.getCounters()) {
                        if (counter.getTariffZones().isEmpty()) {
                            generateStartData(counter);
                        }
                        for (TariffZone tariffZone : counter.getTariffZones()) {
                            if (tariffZone.getCounterSensorHistorys().isEmpty()) {
                                generateStartData(counter);
                            }
                            generateNextData(counter);
                        }
                    }
                }
                uf.edit(user);
            }
        }

    }

    private void generateStartData(Counter counter) {
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
    }

    private void generateNextData(Counter counter) {
        Random rnd = new Random();
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.SECOND) == 1
                || cal.get(Calendar.SECOND) == 15
                || cal.get(Calendar.SECOND) == 30
                || cal.get(Calendar.SECOND) == 45) {
            counter.addValue((rnd.nextBoolean() && rnd.nextBoolean() ? rnd.nextInt(5) : 0));
        }
    }
    
    public String getStarted(){
        return "";
    }
}
