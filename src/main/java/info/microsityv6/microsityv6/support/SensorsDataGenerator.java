package info.microsityv6.microsityv6.support;

import info.microsityv6.microsityv6.entitys.ESP;
import info.microsityv6.microsityv6.entitys.Kit;
import info.microsityv6.microsityv6.entitys.Log;
import info.microsityv6.microsityv6.entitys.Pin;
import info.microsityv6.microsityv6.entitys.SensorsData;
import info.microsityv6.microsityv6.enums.CounterType;
import info.microsityv6.microsityv6.enums.DeviceType;
import info.microsityv6.microsityv6.enums.LoggerLevel;
import info.microsityv6.microsityv6.facades.ESPFacade;
import info.microsityv6.microsityv6.facades.KitFacade;
import info.microsityv6.microsityv6.facades.SensorsDataFacade;
import info.microsityv6.microsityv6.pagesControllers.PageController;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named(value = "sensorsDataGenerator")
@RequestScoped
public class SensorsDataGenerator extends PageController {

    @EJB
    SensorsDataFacade sdf;
    @EJB
    ESPFacade espf;
    @EJB
    KitFacade kitFacade;
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
    List<SensorsData> sensors = new ArrayList<>();
    private int iteration = 0;
    private Calendar dateToSet = Calendar.getInstance();
    private Kit demokit = new Kit();
    private String temp = "22";
    private String hum = "80";
    private int hours = 0;
    private int days = 0;
    private long startId;

    public SensorsDataGenerator() {

    }

    public void startGenetaror() {
        GeneratorThread gt = new GeneratorThread();
    }

    private class GeneratorThread implements Runnable {

        Thread t;

        public GeneratorThread() {
            t = new Thread(this);
            t.start();
        }

        @Override
        public void run() {
            System.out.println("Thread is run");
            initiateSensors();
            if (!sensorsIsExist()) {
                for (SensorsData sensor : sensors) {
                    createESP(sensor);
                }
                demokit.setKitHexId("FFFFFFFF");
                kitFacade.create(demokit);
            }
            createVladSensors();
            System.out.println("Thread is finish");
        }

        private void createESP(SensorsData sensor) {
            if (sensor.getSensorId().contains("Meter")) {
                createNewCounterESP(sensor);
            }
            if (sensor.getSensorId().contains("Sensor")) {
                createNewSensorESP(sensor);
            }
            if (sensor.getSensorId().contains("Controller")) {
                createNewControllerESP(sensor);
            }
        }

        private void createNewCounterESP(SensorsData sensor) {
            ESP esp = new ESP();
            esp.setEspId(sensor.getSensorId());
            esp.setFirmware_id("Dummy");
            esp.setInputDate(new Date());
            esp.setLastIp("localhost");
            esp.setLastAP("DirectConnect");
            Pin pin = new Pin();
            List<Pin> pins = new ArrayList<>();
            if (sensor.getSensorId().contains("Watt")) {
                pin.setCounterType(CounterType.WATT);
            }
            if (sensor.getSensorId().contains("Water")) {
                pin.setCounterType(CounterType.WATER);
            }
            if (sensor.getSensorId().contains("Gas")) {
                pin.setCounterType(CounterType.GAS);
            }
            if (sensor.getSensorId().contains("Warm")) {
                pin.setCounterType(CounterType.WARM);
            }
            pin.setPin_num(sensor.getPinNum());
            pin.setType(DeviceType.COUNTER);
            pins.add(pin);
            esp.setPins(pins);
            demokit.addEsp(esp);
            logFacade.create(new Log(LoggerLevel.INFO, "Added new ESP. ID: " + esp.getEspId()));
        }

        private void createNewSensorESP(SensorsData sensor) {
            ESP esp = new ESP();
            esp.setEspId(sensor.getSensorId());
            esp.setFirmware_id("Dummy");
            esp.setInputDate(new Date());
            esp.setLastIp("localhost");
            esp.setLastAP("DirectConnect");
            Pin pin = new Pin();
            pin.setCounterType(CounterType.NOT_SET_UP);
            List<Pin> pins = new ArrayList<>();
            if (sensor.getSensorId().contains("Temp")) {
                pin.setType(DeviceType.DATA);
            }
            if (sensor.getSensorId().contains("Humidity")) {
                pin.setType(DeviceType.DATA);
            }
            if (sensor.getSensorId().contains("Motion")) {
                pin.setType(DeviceType.SECURITY);
                pin.setRelay_id("ESP_Dummy_EngineOne");
            }
            if (sensor.getSensorId().contains("DoorOpen")) {
                pin.setType(DeviceType.SECURITY);
                pin.setRelay_id("ESP_Dummy_EngineTwo");
            }
            pin.setPin_num(sensor.getPinNum());
            pins.add(pin);
            esp.setPins(pins);
            demokit.addEsp(esp);
            logFacade.create(new Log(LoggerLevel.INFO, "Added new ESP. ID: " + esp.getEspId()));
        }

        private void createNewControllerESP(SensorsData sensor) {
            ESP esp = new ESP();
            esp.setEspId(sensor.getSensorId());
            esp.setFirmware_id("Dummy");
            esp.setInputDate(new Date());
            esp.setLastIp("localhost");
            esp.setLastAP("DirectConnect");
            Pin pin = new Pin();
            pin.setCounterType(CounterType.NOT_SET_UP);
            pin.setType(DeviceType.RELAY);
            pin.setPin_num(sensor.getPinNum());
            List<Pin> pins = new ArrayList<>();
            pin.setPin_num(sensor.getPinNum());
            pins.add(pin);
            esp.setPins(pins);
            demokit.addEsp(esp);
            logFacade.create(new Log(LoggerLevel.INFO, "Added new ESP. ID: " + esp.getEspId()));
        }

        private boolean sensorsIsExist() {
            try {
                if (kitFacade.findAll().isEmpty()) {
                    return false;
                }
            } catch (EJBTransactionRolledbackException ex) {

            }
            for (Kit kit : kitFacade.findAll()) {
                if (kit.getKitHexId().equalsIgnoreCase("FFFFFFFF")) {
                    return true;
                }
            }
            return false;
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

        private void createVladSensors() {
            if (vladSesorsIsexist()) {
                return;
            }
            Kit vlad1 = new Kit();
            vlad1.setKitHexId("FFFFFFFD");
            Kit vlad2 = new Kit();
            vlad2.setKitHexId("FFFFFFFC");
            ESP moscow = new ESP();
            moscow.setEspId("ESP_18:fe:34:a6:a0:e4");
            moscow.setInputDate(new Date());
            Pin pin = new Pin();
            pin.setCounterType(CounterType.WATT);
            pin.setPin_num(2);
            pin.setType(DeviceType.COUNTER);
            moscow.getPins().add(pin);
            vlad1.addEsp(moscow);
            logFacade.create(new Log(LoggerLevel.INFO, "Добавлен счетчик для Влада с kit_id = FFFFFFFD"));
            kitFacade.create(vlad1);
            ESP nikolaev = new ESP();
            nikolaev.setEspId("ESP_18:fe:34:da:8a:77");
            nikolaev.setInputDate(new Date());
            Pin pin1 = new Pin();
            pin1.setCounterType(CounterType.WATT);
            pin1.setPin_num(2);
            pin1.setType(DeviceType.COUNTER);
            nikolaev.getPins().add(pin1);
            vlad2.addEsp(nikolaev);
            logFacade.create(new Log(LoggerLevel.INFO, "Добавлен счетчик для Влада с kit_id = FFFFFFFC"));
            kitFacade.create(vlad2);

        }

        private boolean vladSesorsIsexist() {
            if (kitFacade.findAll().isEmpty()) {
                return false;
            }
            for (Kit kit : kitFacade.findAll()) {
                if (kit.getKitHexId().equals("FFFFFFFD") || kit.getKitHexId().equals("FFFFFFFC")) {
                    return true;
                }
            }
            return false;
        }

    }
}
