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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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
    private int iteration=0;
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
        createFile();
        GeneratorThread gt = new GeneratorThread();
    }
    
    public void startSmallGeneration(){
        iteration=50;
        startGenetaror();
    }

    private void createFile() {
        File file;
        if (System.getProperty("os.name").contains("Wind")) {
            file = new File("C:\\Users\\Panker-RDP\\Documents\\createdDataBase.csv");
        } else {
            file = new File("~/dataBasesave/createdDataBase.csv");
        }
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            String text = "\"ID\";\"DT\";\"IS_ACTION\";\"IS_BOOL\";\"PIN_NUM\";\"SENSOR_ID\";\"TIMING\";\"VALUE\";\"WAS_READ\"";
            fileWriter.write(text);
            fileWriter.append('\n');
            fileWriter.flush();
            while ((startId = new Random().nextLong()) <= 0) {
            }

        } catch (IOException ex) {
            System.out.println(ex);
        }
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
            generateSensorsData();
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
                return !kitFacade.findAll().isEmpty();
            } catch (EJBTransactionRolledbackException ex) {

            }
            return false;
        }

        public void generateSensorsData() {
            int hour = -1;
            int day = -1;
            int countDay = 0;

            int minusMonth = 0;
            switch (dateToSet.get(Calendar.MONTH)) {
                case 0:
                    break;
                case 1:
                    minusMonth = 1;
                    break;
                case 2:
                    minusMonth = 2;
                    break;
                default:
                    minusMonth = 3;
            }
            
            

            dateToSet.set(Calendar.MONTH, dateToSet.get(Calendar.MONTH) - minusMonth);
            while (dateToSet.before(Calendar.getInstance())) {
                if (dateToSet.get(Calendar.DAY_OF_MONTH) != day) {
                    day = dateToSet.get(Calendar.DAY_OF_MONTH);
                    days++;
                }
                if (dateToSet.get(Calendar.HOUR_OF_DAY) != hour) {
                    hour = dateToSet.get(Calendar.HOUR_OF_DAY);
                    hours++;
                }
                dateToSet.setTimeInMillis(dateToSet.getTimeInMillis() + 1800000);
            }
            dateToSet.set(Calendar.MONTH, dateToSet.get(Calendar.MONTH) - minusMonth);
            if(iteration!=0){
                dateToSet.set(Calendar.MONTH, dateToSet.get(Calendar.MONTH) - iteration*1000);
            }
            Date shiftDate = dateToSet.getTime();
            while (shiftDate.getTime() < Calendar.getInstance().getTimeInMillis()) {
                dateToSet.setTimeInMillis(shiftDate.getTime());
                if (dateToSet.get(Calendar.DAY_OF_MONTH) != day) {
                    day = dateToSet.get(Calendar.DAY_OF_MONTH);
                    countDay++;
                    double percent = 100.0 - ((double) days - (double) countDay) / (double) days * 100.0;
                    System.out.println("Left " + (days - countDay) + " from " + days + " days." + String.valueOf(percent) + "%");

                }
                shiftDate = getNextDate(shiftDate);
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
                saveToFile(sensor);
            }
        }

        private void generateNewSensorData(SensorsData sensor) {
            if (sensor.getIsBool()) {
                if (new Random().nextBoolean()) {
                    sensor.setValue(String.valueOf((new Random().nextBoolean()) ? "1" : "0"));
                    saveToFile(sensor);
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
                saveToFile(sensor);
            } else {
                if (sensor.getSensorId().contains("Temp")) {
                    sensor.setValue(String.valueOf(Integer.parseInt(temp) - new Random().nextInt(2)));
                    temp = sensor.getValue();
                }
                if (sensor.getSensorId().contains("Humi")) {
                    sensor.setValue(String.valueOf(Integer.parseInt(hum) - new Random().nextInt(2)));
                    hum = sensor.getValue();
                }
                saveToFile(sensor);
            }
        }

        private void generateNewControllerData(SensorsData sensor) {
            //Я реально не знаю как это должно работать.... Но на всякий случай
            if (new Random().nextBoolean() && new Random().nextBoolean() && new Random().nextBoolean()) {
                sensor.setValue(String.valueOf(1));
                saveToFile(sensor);
            }
        }

        private void saveToFile(SensorsData sensor) {
            File file;
            if (System.getProperty("os.name").contains("Wind")) {
                file = new File("C:\\Users\\Panker-RDP\\Documents\\createdDataBase.csv");
            } else {
                file = new File("~/dataBasesave/createdDataBase.csv");
            }
            try (FileWriter fileWriter = new FileWriter(file, true)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String txt = (startId++) + ";" + (sensor.getIsBool() ? "1" : "0") + ";" + "\"" + sdf.format(sensor.getDt()) + "\""
                        + ";" + (sensor.getIsAction() ? "1" : "0") + ";" + sensor.getPinNum() + ";\"" + sensor.getSensorId() + "\""
                        + ";" + sensor.getTiming() + ";\"" + sensor.getValue() + "\";" + (sensor.getWasRead() ? "1" : "0");
                fileWriter.write(txt);
                fileWriter.append('\n');
                fileWriter.flush();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }

    }

//    public void generateData() {
//        initiateSensors();
//        createFile();
//        if (!sensorsIsExist()) {
//            for (SensorsData sensor : sensors) {
//                createESP(sensor);
//            }
//            demokit.setKitHexId("FFFFFFFF");
//            kitFacade.create(demokit);
//        }
//        setDataToZero();
//        generateSensorsData();
//        logFacade.create(new Log(LoggerLevel.WARN, "System generated new values for test"));
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("System generated new values for test"));
//
//    }
//
//    private void initiateSensors() {
//        sensors.clear();
//        wattMeter1 = generateNewCounter("WattMeter1");
//        wattMeter2 = generateNewCounter("WattMeter2");
//        waterMeter = generateNewCounter("WaterMeter");
//        gasMeter = generateNewCounter("GasMeter");
//        warmMeter = generateNewCounter("WarmMeter");
//        temperatureSensor = generateNewSensor("TemperatureSensor", false);
//        humiditySensor = generateNewSensor("HumiditySensor", false);
//        motionSensor = generateNewSensor("MotionSensor", true);
//        doorOpenSensor = generateNewSensor("DoorOpenSensor", true);
//        engine1Controller = generateNewController("EngineOne");
//        engine2Controller = generateNewController("EngineTwo");
//        engine3Controller = generateNewController("EngineThree");
//    }
//
//    private boolean sensorsIsExist() {
//        try {
//            return !kitFacade.findAll().isEmpty();
//        } catch (EJBTransactionRolledbackException ex) {
//
//        }
//        return false;
//    }
//
//    private Date getNextDate(Date incom) {
//        long time = incom.getTime() + (long) 60000;
//        incom.setTime(time);
//        return incom;
//    }
//
//    private void setDataToZero() {
//        int minusMonth = 0;
//        switch (dateToSet.get(Calendar.MONTH)) {
//            case 0:
//                iteration = 1400 * 1;
//                break;
//            case 1:
//                minusMonth = 1;
//                iteration = 1400 * 2;
//                break;
//            case 2:
//                minusMonth = 2;
//                iteration = 1400 * 3;
//                break;
//            default:
//                minusMonth = 3;
//                iteration = 100;
//        }
//        dateToSet.set(Calendar.MONTH, dateToSet.get(Calendar.MONTH) - minusMonth);
//        int hour = -1;
//        int day = -1;
//        while (dateToSet.before(Calendar.getInstance())) {
//            if (dateToSet.get(Calendar.DAY_OF_MONTH) != day) {
//                day = dateToSet.get(Calendar.DAY_OF_MONTH);
//                days++;
//            }
//            if (dateToSet.get(Calendar.HOUR_OF_DAY) != hour) {
//                hour = dateToSet.get(Calendar.HOUR_OF_DAY);
//                hours++;
//            }
//            dateToSet.setTimeInMillis(dateToSet.getTimeInMillis() + 1800000);
//        }
//        dateToSet.set(Calendar.MONTH, dateToSet.get(Calendar.MONTH) - minusMonth);
//
//    }
//
//    private SensorsData generateNewCounter(String name) {
//        SensorsData counter = new SensorsData();
//        counter.setIsAction(false);
//        counter.setIsBool(false);
//        counter.setPinNum((short) 2);
//        counter.setSensorId("ESP_Dummy_" + name);
//        counter.setTiming(0);
//        counter.setWasRead(false);
//        sensors.add(counter);
//        return counter;
//    }
//
//    private SensorsData generateNewSensor(String name, boolean isBool) {
//        SensorsData sensor = new SensorsData();
//        sensor.setIsAction(false);
//        sensor.setIsBool(isBool);
//        sensor.setPinNum((short) 3);
//        sensor.setSensorId("ESP_Dummy_" + name);
//        sensor.setTiming(0);
//        sensor.setWasRead(false);
//        sensors.add(sensor);
//        return sensor;
//    }
//
//    private SensorsData generateNewController(String name) {
//        SensorsData sensor = new SensorsData();
//        sensor.setIsAction(true);
//        sensor.setIsBool(true);
//        sensor.setPinNum((short) 4);
//        sensor.setSensorId("ESP_Dummy_" + name);
//        sensor.setTiming(0);
//        sensor.setWasRead(false);
//        sensors.add(sensor);
//        return sensor;
//    }
//
//    private void createESP(SensorsData sensor) {
//        if (sensor.getSensorId().contains("Meter")) {
//            createNewCounterESP(sensor);
//        }
//        if (sensor.getSensorId().contains("Sensor")) {
//            createNewSensorESP(sensor);
//        }
//        if (sensor.getSensorId().contains("Controller")) {
//            createNewControllerESP(sensor);
//        }
//    }
//
//    private void createNewCounterESP(SensorsData sensor) {
//        ESP esp = new ESP();
//        esp.setEspId(sensor.getSensorId());
//        esp.setFirmware_id("Dummy");
//        esp.setInputDate(new Date());
//        esp.setLastIp("localhost");
//        esp.setLastAP("DirectConnect");
//        Pin pin = new Pin();
//        List<Pin> pins = new ArrayList<>();
//        if (sensor.getSensorId().contains("Watt")) {
//            pin.setCounterType(CounterType.WATT);
//        }
//        if (sensor.getSensorId().contains("Water")) {
//            pin.setCounterType(CounterType.WATER);
//        }
//        if (sensor.getSensorId().contains("Gas")) {
//            pin.setCounterType(CounterType.GAS);
//        }
//        if (sensor.getSensorId().contains("Warm")) {
//            pin.setCounterType(CounterType.WARM);
//        }
//        pin.setPin_num(sensor.getPinNum());
//        pin.setType(DeviceType.COUNTER);
//        pins.add(pin);
//        esp.setPins(pins);
//        demokit.addEsp(esp);
//        logFacade.create(new Log(LoggerLevel.INFO, "Added new ESP. ID: " + esp.getEspId()));
//    }
//
//    private void createNewSensorESP(SensorsData sensor) {
//        ESP esp = new ESP();
//        esp.setEspId(sensor.getSensorId());
//        esp.setFirmware_id("Dummy");
//        esp.setInputDate(new Date());
//        esp.setLastIp("localhost");
//        esp.setLastAP("DirectConnect");
//        Pin pin = new Pin();
//        pin.setCounterType(CounterType.NOT_SET_UP);
//        List<Pin> pins = new ArrayList<>();
//        if (sensor.getSensorId().contains("Temp")) {
//            pin.setType(DeviceType.DATA);
//        }
//        if (sensor.getSensorId().contains("Humidity")) {
//            pin.setType(DeviceType.DATA);
//        }
//        if (sensor.getSensorId().contains("Motion")) {
//            pin.setType(DeviceType.SECURITY);
//            pin.setRelay_id("ESP_Dummy_EngineOne");
//        }
//        if (sensor.getSensorId().contains("DoorOpen")) {
//            pin.setType(DeviceType.SECURITY);
//            pin.setRelay_id("ESP_Dummy_EngineTwo");
//        }
//        pin.setPin_num(sensor.getPinNum());
//        pins.add(pin);
//        esp.setPins(pins);
//        demokit.addEsp(esp);
//        logFacade.create(new Log(LoggerLevel.INFO, "Added new ESP. ID: " + esp.getEspId()));
//    }
//
//    private void createNewControllerESP(SensorsData sensor) {
//        ESP esp = new ESP();
//        esp.setEspId(sensor.getSensorId());
//        esp.setFirmware_id("Dummy");
//        esp.setInputDate(new Date());
//        esp.setLastIp("localhost");
//        esp.setLastAP("DirectConnect");
//        Pin pin = new Pin();
//        pin.setCounterType(CounterType.NOT_SET_UP);
//        pin.setType(DeviceType.RELAY);
//        pin.setPin_num(sensor.getPinNum());
//        List<Pin> pins = new ArrayList<>();
//        pin.setPin_num(sensor.getPinNum());
//        pins.add(pin);
//        esp.setPins(pins);
//        demokit.addEsp(esp);
//        logFacade.create(new Log(LoggerLevel.INFO, "Added new ESP. ID: " + esp.getEspId()));
//    }
//
//    private void generateSensorsData() {
//        Date shiftDate = dateToSet.getTime();
//        System.out.println("Generated " + iteration + "values");
//        int count = 0;
//        int day = -1;
//        int countDay = 0;
//        int hour = -1;
//        int countHour = 0;
//        while (shiftDate.getTime() < Calendar.getInstance().getTimeInMillis()) {
//            dateToSet.setTimeInMillis(shiftDate.getTime());
//            if (dateToSet.get(Calendar.DAY_OF_MONTH) != day) {
//                day = dateToSet.get(Calendar.DAY_OF_MONTH);
//                countDay++;
//                int percent = (int) Math.floor(100.0 - ((double) days - (double) countDay) / (double) days);
//                System.out.println("Left " + (days - countDay) + " from " + days + " days." + String.valueOf(percent) + "%");
//            }
//
//            shiftDate = getNextDate(shiftDate);
//            initiateSensors();
//            for (SensorsData sensor : sensors) {
//                sensor.setDt(shiftDate);
//                if (sensor.getSensorId().contains("Meter")) {
//                    generateNewCounterData(sensor);
//                }
//                if (sensor.getSensorId().contains("Sensor")) {
//                    generateNewSensorData(sensor);
//                }
//                if (sensor.getSensorId().contains("Controller")) {
//                    generateNewControllerData(sensor);
//                }
//            }
//        }
//    }
//
//    private void generateNewCounterData(SensorsData sensor) {
//        if (new Random().nextBoolean()) {
//            sensor.setValue(String.valueOf(new Random().nextInt(3)));
//            saveToFile(sensor);
//        }
//    }
//
//    private void generateNewSensorData(SensorsData sensor) {
//        if (sensor.getIsBool()) {
//            if (new Random().nextBoolean()) {
//                sensor.setValue(String.valueOf((new Random().nextBoolean()) ? "1" : "0"));
//                saveToFile(sensor);
//            }
//        } else if (new Random().nextBoolean()) {
//            if (sensor.getSensorId().contains("Temp")) {
//                sensor.setValue(String.valueOf(Integer.parseInt(temp) + new Random().nextInt(2)));
//                temp = sensor.getValue();
//            }
//            if (sensor.getSensorId().contains("Humi")) {
//                sensor.setValue(String.valueOf(Integer.parseInt(hum) + new Random().nextInt(2)));
//                hum = sensor.getValue();
//            }
//            saveToFile(sensor);
//        } else {
//            if (sensor.getSensorId().contains("Temp")) {
//                sensor.setValue(String.valueOf(Integer.parseInt(temp) - new Random().nextInt(2)));
//                temp = sensor.getValue();
//            }
//            if (sensor.getSensorId().contains("Humi")) {
//                sensor.setValue(String.valueOf(Integer.parseInt(hum) - new Random().nextInt(2)));
//                hum = sensor.getValue();
//            }
//            saveToFile(sensor);
//        }
//    }
//
//    private void generateNewControllerData(SensorsData sensor) {
//        //Я реально не знаю как это должно работать.... Но на всякий случай
//        if (new Random().nextBoolean() && new Random().nextBoolean() && new Random().nextBoolean()) {
//            sensor.setValue(String.valueOf(1));
//            saveToFile(sensor);
//        }
//    }
//
//    
}
