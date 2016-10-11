package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Counter;
import info.microsityv6.microsityv6.entitys.Facility;
import info.microsityv6.microsityv6.entitys.Log;
import info.microsityv6.microsityv6.entitys.SensorsData;
import info.microsityv6.microsityv6.entitys.SensorsDataReaded;
import info.microsityv6.microsityv6.entitys.TariffZone;
import info.microsityv6.microsityv6.entitys.User;
import info.microsityv6.microsityv6.enums.LoggerLevel;
import info.microsityv6.microsityv6.facades.SensorsDataFacade;
import info.microsityv6.microsityv6.facades.SensorsDataReadedFacade;
import java.util.Iterator;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named(value = "setupCounterController")
@SessionScoped
public class SetupCounterController extends PageController {

    @EJB
    SensorsDataFacade sdf;
    @EJB
    SensorsDataReadedFacade sdrf;

    private Counter setupCounter;

    public SetupCounterController() {
    }
    // Busines Logic

    public String addTariffZone() {
        TariffZone tz = new TariffZone();
        tz.setNameTariff("Новая тарифная зона " + setupCounter.getTariffZones().size());
        setupCounter.getTariffZones().add(tz);
        return "setupcounter.xhtml?faces-redirect=true";
    }

    public String deleteTariffZone(TariffZone tz) {
        setupCounter.getTariffZones().remove(tz);
        return "setupcounter.xhtml?faces-redirect=true";
    }

    public String saveCounterSettings() {
        int index = -1;
        for (Counter counter : super.getCurrentFacility().getCounters()) {
            if (counter.equals(setupCounter)) {
                index++;
                break;
            }
            index++;
        }
        if (index != -1) {
            updateCounterHistory();
            super.getCurrentFacility().getCounters().set(index, setupCounter);
            super.saveChanges();
            logFacade.create(new Log(LoggerLevel.INFO,super.getCurrent().getMainEmail()+ " change settings Counter ID:"+index));
            return "counters.xhtml?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Что-то не так :( "));
        return "setupcounter.xhtml?faces-redirect=true";
    }

    public String deleteCounter() {
        super.getCurrentFacility().getCounters().remove(setupCounter);
        setupCounter = new Counter();
        return "counters.xhtml?faces-redirect=true";
    }

    public String cancelCounterSetup() {
        setupCounter = new Counter();
        return "counters.xhtml?faces-redirect=true";
    }

    private void updateCounterHistory() {
        for (TariffZone tariffZone : setupCounter.getTariffZones()) {
            System.out.println(tariffZone.toString());
            tariffZone.getCounterSensorHistorys().clear();
                    
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Внимание! Начался процесс обновления истории счетчика. Пожалуйста подождите."));
        for (SensorsData sensorsData : sdf.findAll()) {
            if (sensorsData.getSensorId().equals(setupCounter.getEsp_id())
                    && sensorsData.getPinNum() == setupCounter.getPin()
                    && sensorsData.getWasRead()) {
                sensorsData.setWasRead(false);
                sdf.edit(sensorsData);
            }
        }
        
        Iterator sdri = sdrf.findAll().iterator();
        while (sdri.hasNext()) {
            SensorsDataReaded sensorsData = (SensorsDataReaded) sdri.next();
            if (sensorsData.getSensorId().equals(setupCounter.getEsp_id())
                    && sensorsData.getPinNum() == setupCounter.getPin()) {
                sensorsData.setWasRead(false);
                sdrf.remove(sensorsData);
                SensorsData sd = new SensorsData();
                sd.setDt(sensorsData.getDt());
                sd.setIsAction(sensorsData.isIsAction());
                sd.setIsBool(sensorsData.isBool());
                sd.setPinNum(sensorsData.getPinNum());
                sd.setSensorId(sensorsData.getSensorId());
                sd.setTiming(sensorsData.getTiming());
                sd.setValue(sensorsData.getValue());
                sdf.create(sd);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("История обновлена. Начинаем процесс пересчета данных."));
        reParseData();
    }

    private void reParseData() {
        Iterator sds = sdf.findAll().iterator();
        while (sds.hasNext()) {
            SensorsData sd = (SensorsData) sds.next();
            if (!sd.getWasRead()) {
                if (setupCounter.getEsp_id().equals(sd.getSensorId())) {
                    if (sd.getIsBool()) {
                        setupCounter.addValue(sd.getValue().equals("1"), sd.getDt());
                        sd.setWasRead(true);
                    } else {
                        setupCounter.addValue(Double.parseDouble(sd.getValue()), sd.getDt());

                        sd.setWasRead(true);
                    }
                }

                sdf.edit(sd);
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
                sdrf.create(sdr);
            }
        }
        System.out.println("Parse data finished");
    }

    //Getters & Setters
    public Counter getSetupCounter() {
        return setupCounter;
    }

    public void setSetupCounter(Counter setupCounter) {
        this.setupCounter = setupCounter;
    }

}
