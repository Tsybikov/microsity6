package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Counter;
import info.microsityv6.microsityv6.entitys.CounterSensorHistory;
import info.microsityv6.microsityv6.entitys.TariffZone;
import info.microsityv6.microsityv6.enums.CounterType;
import info.microsityv6.microsityv6.support.TariffSummInfo;
import info.microsityv6.microsityv6.support.TariffViewClass;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Named(value = "counterPageController")
@SessionScoped
public class CounterPageController extends PageController implements Serializable {

    private List<Counter> wattMeters;
    private List<Counter> gasMeters;
    private List<Counter> waterMeters;
    private List<Counter> warmMeters;
    private int firstVisible;
    private boolean hasWattMeter;
    private boolean hasGasMeter;
    private boolean hasWaterMeter;
    private boolean hasWarmMeter;
    private boolean show;
    private boolean showCharts;
    private List<TariffSummInfo> tsis;

    public CounterPageController() {
    }

    public List<TariffViewClass> getTariffValues(Counter counter) {
        List<TariffViewClass> tariffViewClasses = new ArrayList<>();
        Iterator tzIt=counter.getTariffZones().iterator();
        while(tzIt.hasNext()) {
            TariffZone tariffZone=(TariffZone)tzIt.next();
            Iterator cshs = tariffZone.getCounterSensorHistorys().iterator();
            while (cshs.hasNext()) {
                CounterSensorHistory csh = (CounterSensorHistory) cshs.next();
                
                if (tariffViewClasses.isEmpty()) {
                    TariffViewClass tvc = new TariffViewClass(csh);
                    tariffViewClasses.add(tvc);
                }
                ListIterator tvcIt = tariffViewClasses.listIterator();
                while (tvcIt.hasNext()) {
                    TariffViewClass tvc = (TariffViewClass) tvcIt.next();
                    String controlMark = csh.getRecordDate().get(Calendar.YEAR) + "" + csh.getRecordDate().get(Calendar.MONTH);
                    if (tvc.getControlMark().equals(controlMark)) {
                        try{
                        tvc.sendData(csh, tariffZone.getNameTariff());}
                        catch(ConcurrentModificationException ex){
                            System.out.println(csh.toString()+tariffZone.getNameTariff());
                        }
                        tvcIt.set(tvc);
                    } else {
                        boolean find = false;
                        for (TariffViewClass tariffViewClasse : tariffViewClasses) {
                            if (tariffViewClasse.getControlMark().equals(controlMark)) {
                                find = true;
                                break;
                            }
                        }
                        if (!find) {
                            TariffViewClass tvcPlus = new TariffViewClass(csh);
                            tvcPlus.sendData(csh, tariffZone.getNameTariff());
                            tariffViewClasses.add(tvcPlus);
                        }
                    }
                }
            }

        }
        for (TariffZone tariffZone : counter.getTariffZones()) {
            int startValue=tariffZone.getStartValue();
            for (TariffViewClass tvc : tariffViewClasses) {
                for (TariffSummInfo tsi : tvc.getTsis()) {
                    if(tsi.getTariffName().equals(tariffZone.getNameTariff())){
                        if(tsi.getStartValue()==0)tsi.setStartValue(startValue);
                        startValue+=tsi.getSummValue();
                    }
                }
            }
        }
        setFirstVisible(tariffViewClasses.size()-1);
        return tariffViewClasses;
    }

    public List<Counter> getWattMeters() {
        wattMeters = new ArrayList<>();
        if (isHasWattMeter()) {
            for (Counter counter : super.getCurrentFacility().getCounters()) {
                if (counter.getCounterType().equals(CounterType.WATT)) {
                    wattMeters.add(counter);
                }
            }
        }
        return wattMeters;
    }

    public void setWattMeters(List<Counter> wattMeters) {
        this.wattMeters = wattMeters;
    }

    public List<Counter> getGasMeters() {
        gasMeters = new ArrayList<>();
        if (isHasGasMeter()) {
            for (Counter counter : super.getCurrentFacility().getCounters()) {
                if (counter.getCounterType().equals(CounterType.GAS)) {
                    gasMeters.add(counter);
                }
            }
        }
        return gasMeters;
    }

    public void setGasMeters(List<Counter> gasMeters) {
        this.gasMeters = gasMeters;
    }

    public List<Counter> getWaterMeters() {
        waterMeters = new ArrayList<>();
        if (isHasWaterMeter()) {
            for (Counter counter : super.getCurrentFacility().getCounters()) {
                if (counter.getCounterType().equals(CounterType.WATER)) {
                    waterMeters.add(counter);
                }
            }
        }
        return waterMeters;
    }

    public void setWaterMeters(List<Counter> waterMeters) {
        this.waterMeters = waterMeters;
    }

    public List<Counter> getWarmMeters() {
        warmMeters = new ArrayList<>();
        if (isHasWarmMeter()) {
            for (Counter counter : super.getCurrentFacility().getCounters()) {
                if (counter.getCounterType().equals(CounterType.WARM)) {
                    warmMeters.add(counter);
                }
            }
        }
        return warmMeters;
    }

    public void setWarmMeters(List<Counter> warmMeters) {
        this.warmMeters = warmMeters;
    }

    public boolean isHasWattMeter() {
        if (notNullCounterArray()) {
            for (Counter counter : super.getCurrentFacility().getCounters()) {
                if (counter.getCounterType().equals(CounterType.WATT)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setHasWattMeter(boolean hasWattMeter) {
        this.hasWattMeter = hasWattMeter;
    }

    public boolean isHasGasMeter() {
        if (notNullCounterArray()) {
            for (Counter counter : super.getCurrentFacility().getCounters()) {
                if (counter.getCounterType().equals(CounterType.GAS)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setHasGasMeter(boolean hasGasMeter) {
        this.hasGasMeter = hasGasMeter;
    }

    public boolean isHasWaterMeter() {
        if (notNullCounterArray()) {
            for (Counter counter : super.getCurrentFacility().getCounters()) {
                if (counter.getCounterType().equals(CounterType.WATER)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setHasWaterMeter(boolean hasWaterMeter) {
        this.hasWaterMeter = hasWaterMeter;
    }

    public boolean isHasWarmMeter() {
        if (notNullCounterArray()) {
            for (Counter counter : super.getCurrentFacility().getCounters()) {
                if (counter.getCounterType().equals(CounterType.WARM)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setHasWarmMeter(boolean hasWarmMeter) {
        this.hasWarmMeter = hasWarmMeter;
    }

    private boolean notNullCounterArray() {
        try {
            if (super.getCurrentFacility().getCounters() != null) {
                return true;
            }
        } catch (NullPointerException ex) {
            return false;
        }
        return false;

    }

    public boolean isShow() {
        return notNullCounterArray();
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public List<TariffSummInfo> getTsis(TariffViewClass tvc) {
        tsis=new ArrayList<>();
        for (TariffSummInfo tsi : tvc.getTsis()) {
            tsis.add(tsi);
        }
        return tsis;
    }
    
    private void setTsis(TariffViewClass tvc){
        
    }

    public int getFirstVisible() {
        return firstVisible;
    }

    public void setFirstVisible(int firstVisible) {
        this.firstVisible = firstVisible;
    }

    public boolean isShowCharts(Counter counter) {
        if(counter==null)return false;
        if(counter.getTariffZones().get(0).getCounterSensorHistorys().size()<4320)return false;
        return true;
    }

    public void setShowCharts(boolean showCharts) {
        this.showCharts = showCharts;
    }
    
    

    
    
    

}
