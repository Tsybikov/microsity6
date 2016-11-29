package info.microsityv6.microsityv6.timers;

import info.microsityv6.microsityv6.entitys.SensorsData;
import info.microsityv6.microsityv6.facades.LogFacade;
import info.microsityv6.microsityv6.facades.SensorsDataFacade;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.inject.Named;

@Named("DummyGenerator")
@Singleton
@Startup
public class DummyDateGenerator {

    @Inject
    ParseData parseData;
    @EJB
    SensorsDataFacade sdf;
    @EJB
    LogFacade logFacade;
    private List<SensorsData> sensors = new ArrayList<>();
    SensorsData wattMeter1 = new SensorsData();
    SensorsData wattMeter2 = new SensorsData();
    SensorsData waterMeter = new SensorsData();
    SensorsData gasMeter = new SensorsData();
    SensorsData warmMeter = new SensorsData();
    SensorsData temperatureSensor = new SensorsData();
    SensorsData humiditySensor = new SensorsData();
    SensorsData motionSensor = new SensorsData();
    SensorsData doorOpenSensor = new SensorsData();
    SensorsData engine1Controller = new SensorsData();
    SensorsData engine2Controller = new SensorsData();
    SensorsData engine3Controller = new SensorsData();
    private String temp = "22";
    private String hum = "80";

    @Schedule(dayOfWeek = "*", month = "*", hour = "*", dayOfMonth = "*", year = "*", minute = "*", second = "0", persistent = false)

    public void myTimer() {
        if (parseData.isLock()) {
            return;
        } else {
            parseData.setLock(true);
        }
        generateSensorsData();
        parseData.setLock(false);
    }

    private void generateSensorsData() {
        Calendar dateToSet = Calendar.getInstance();
        Date shiftDate = dateToSet.getTime();
        int iteration = 1;
        for (int minute = 0; minute < iteration; minute++) {
            SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
            initiateSensors();
            for (SensorsData sensor : sensors) {
                sensor.setDt(shiftDate);
                if (sensor.getSensorId().contains("Meter")) {
                    generateNewCounterData(sensor);
                }
                if (sensor.getSensorId().contains("Sensor")) {
                    generateNewSensorData(sensor);
                }
                if (sensor.getSensorId().contains("Controller")) {
                    generateNewControllerData(sensor);
                }
            }
        }
    }

    private Date getNextDate(Date incom) {
        long time = incom.getTime() + (long) 60000;
        incom.setTime(time);
        return incom;
    }

    private void initiateSensors() {
        sensors.clear();
        wattMeter1 = generateNewCounter("WattMeter1");
        wattMeter2 = generateNewCounter("WattMeter2");
        waterMeter = generateNewCounter("WaterMeter");
        gasMeter = generateNewCounter("GasMeter");
        warmMeter = generateNewCounter("WarmMeter");
        temperatureSensor = generateNewSensor("TemperatureSensor", false);
        humiditySensor = generateNewSensor("HumiditySensor", false);
        motionSensor = generateNewSensor("MotionSensor", true);
        doorOpenSensor = generateNewSensor("DoorOpenSensor", true);
        engine1Controller = generateNewController("EngineOne");
        engine2Controller = generateNewController("EngineTwo");
        engine3Controller = generateNewController("EngineThree");
    }

    private SensorsData generateNewCounter(String name) {
        SensorsData counter = new SensorsData();
        counter.setIsAction(false);
        counter.setIsBool(false);
        counter.setPinNum((short) 2);
        counter.setSensorId("ESP_Dummy_" + name);
        counter.setTiming(0);
        counter.setWasRead(false);
        sensors.add(counter);
        return counter;
    }

    private SensorsData generateNewSensor(String name, boolean isBool) {
        SensorsData sensor = new SensorsData();
        sensor.setIsAction(false);
        sensor.setIsBool(isBool);
        sensor.setPinNum((short) 3);
        sensor.setSensorId("ESP_Dummy_" + name);
        sensor.setTiming(0);
        sensor.setWasRead(false);
        sensors.add(sensor);
        return sensor;
    }

    private SensorsData generateNewController(String name) {
        SensorsData sensor = new SensorsData();
        sensor.setIsAction(true);
        sensor.setIsBool(true);
        sensor.setPinNum((short) 4);
        sensor.setSensorId("ESP_Dummy_" + name);
        sensor.setTiming(0);
        sensor.setWasRead(false);
        sensors.add(sensor);
        return sensor;
    }

    private void generateNewCounterData(SensorsData sensor) {
        if (new Random().nextBoolean()) {
            sensor.setValue(String.valueOf(new Random().nextInt(3)));
            sdf.create(sensor);
        }
    }

    private void generateNewSensorData(SensorsData sensor) {
        if (sensor.getIsBool()) {
            if (new Random().nextBoolean()) {
                sensor.setValue(String.valueOf((new Random().nextBoolean()) ? "1" : "0"));
                sdf.create(sensor);
            }
        } else if (new Random().nextBoolean()) {
            if (sensor.getSensorId().contains("Temp")) {
                sensor.setValue(String.valueOf(Integer.parseInt(temp) + new Random().nextInt(2)));
                temp = sensor.getValue();
            }
            if (sensor.getSensorId().contains("Humi")) {
                sensor.setValue(String.valueOf(Integer.parseInt(hum) + new Random().nextInt(2)));
                hum = sensor.getValue();
            }
            sdf.create(sensor);
        } else {
            if (sensor.getSensorId().contains("Temp")) {
                sensor.setValue(String.valueOf(Integer.parseInt(temp) - new Random().nextInt(2)));
                temp = sensor.getValue();
            }
            if (sensor.getSensorId().contains("Humi")) {
                sensor.setValue(String.valueOf(Integer.parseInt(hum) - new Random().nextInt(2)));
                hum = sensor.getValue();
            }
            sdf.create(sensor);
        }
    }

    private void generateNewControllerData(SensorsData sensor) {
        //Я реально не знаю как это должно работать.... Но на всякий случай
        if (new Random().nextBoolean() && new Random().nextBoolean() && new Random().nextBoolean()) {
            sensor.setValue(String.valueOf(1));
            sdf.create(sensor);
        }
    }
}
