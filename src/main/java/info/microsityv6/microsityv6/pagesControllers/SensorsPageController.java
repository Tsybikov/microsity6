package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.CounterSensorHistory;
import info.microsityv6.microsityv6.entitys.Log;
import info.microsityv6.microsityv6.entitys.Sensor;
import info.microsityv6.microsityv6.enums.LoggerLevel;
import info.microsityv6.microsityv6.support.SensorOnePeriodView;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;

@Named(value = "sensorsPageController")
@SessionScoped
public class SensorsPageController extends PageController implements Serializable {

    private List<Sensor> sensors;
    private boolean show;
    private boolean showChart=true;

    public SensorsPageController() {
    }

    public String getLastValue(Sensor sensor) {
        String value = "";
        if (sensor.getCshs().isEmpty()) {
            showChart=false;
            return "Нет доступных значений";
        }
        int lastValueIndex = sensor.getCshs().size() - 1;
        try {
            value = String.valueOf(sensor.getCshs().get(lastValueIndex).getRecordValue());
        } catch (NullPointerException ex) {
            value = sensor.getCshs().get(lastValueIndex).isState() ? "Включено" : "Выключено";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm");
        return value + " от " + sdf.format(sensor.getCshs().get(lastValueIndex).getRecordDate().getTime());
    }

    public List<SensorOnePeriodView> getPeriods(Sensor sensor) {
        List<SensorOnePeriodView> sopvs = new ArrayList<>();

        try {
            for (CounterSensorHistory csh : sensor.getCshs()) {
                String contrMark = csh.getRecordDate().get(Calendar.YEAR) + "" + csh.getRecordDate().get(Calendar.MONTH);
                if (sopvs.isEmpty()) {
                    sopvs.add(new SensorOnePeriodView(csh, 0.0));
                }
                ListIterator sopvsIt = sopvs.listIterator();
                boolean foundSopv = false;
                double lastValue = 0.0;
                while (sopvsIt.hasNext() && !foundSopv) {
                    SensorOnePeriodView sopv = (SensorOnePeriodView) sopvsIt.next();
                    if (contrMark.equals(sopv.getControlMark())) {
                        if (sensor.isAsBool()) {
                            sopv.addValue(csh.isState());
                        } else {
                            sopv.addValue(csh.getRecordValue(), sensor.isAsCounter());
                            lastValue = sopv.getEndValue();
                        }
                        foundSopv = true;
                    }
                    sopvsIt.set(sopv);
                }
                if (!foundSopv) {
                    SensorOnePeriodView sopv = new SensorOnePeriodView(csh, lastValue);
                    if (sensor.isAsBool()) {
                        sopv.addValue(csh.isState());
                    } else {
                        sopv.addValue(csh.getRecordValue(), sensor.isAsCounter());
                        lastValue = sopv.getEndValue();
                    }
                    sopvsIt.add(sopv);
                }
            }
        } catch (NullPointerException ex) {
            logFacade.create(new Log(LoggerLevel.ERROR,"NullpointerException in SensorPageController.getPeriods",ex));
        }
        return sopvs;
    }

    public boolean isBoolean(Sensor sensor) {
        return sensor.isAsBool();
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
        if (notNullSensorsArray()) {
            sensors = super.getCurrentFacility().getSensors();
        }
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

    public boolean isShowChart() {
        return showChart;
    }

    public void setShowChart(boolean showChart) {
        this.showChart = showChart;
    }
    
    
}
