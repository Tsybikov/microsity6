package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Counter;
import info.microsityv6.microsityv6.enums.CounterType;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named(value = "counterPageController")
@SessionScoped
public class CounterPageController extends PageController implements Serializable {

    private List<Counter> wattMeters;
    private List<Counter> gasMeters;
    private List<Counter> waterMeters;
    private List<Counter> warmMeters;
    private boolean hasWattMeter;
    private boolean hasGasMeter;
    private boolean hasWaterMeter;
    private boolean hasWarmMeter;
    private boolean show;

    public CounterPageController() {
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
    
    
}
