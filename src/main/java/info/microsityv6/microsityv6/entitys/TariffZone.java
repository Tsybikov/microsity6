package info.microsityv6.microsityv6.entitys;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class TariffZone implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int hourStart;
    private int minuteStart;
    private int hourEnd;
    private int minuteEnd;
    private String nameTariff;
    private int timeZone;
    private int startValue;
    private int nowValue;
    
    
    

    @OneToMany(cascade = CascadeType.ALL)
    private List<CounterSensorHistory> counterSensorHistorys;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Event> events;

    public int getHourStart() {
        return hourStart;
    }

    public void setHourStart(int hourStart) {
        this.hourStart = hourStart;
    }

    public int getMinuteStart() {
        return minuteStart;
    }

    public void setMinuteStart(int minuteStart) {
        this.minuteStart = minuteStart;
    }

    public int getHourEnd() {
        return hourEnd;
    }

    public void setHourEnd(int hourEnd) {
        this.hourEnd = hourEnd;
    }

    public int getMinuteEnd() {
        return minuteEnd;
    }

    public void setMinuteEnd(int minuteEnd) {
        this.minuteEnd = minuteEnd;
    }

    public String getNameTariff() {
        return nameTariff;
    }

    public void setNameTariff(String nameTariff) {
        this.nameTariff = nameTariff;
    }

    public List<CounterSensorHistory> getCounterSensorHistorys() {
        if(counterSensorHistorys==null)counterSensorHistorys=new ArrayList<>();
        return counterSensorHistorys;
    }

    public void setCounterSensorHistorys(List<CounterSensorHistory> counterSensorHistorys) {
        this.counterSensorHistorys = counterSensorHistorys;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }

    public int getStartValue() {
        return startValue;
    }

    public void setStartValue(int startValue) {
        this.startValue = startValue;
    }
    
    public String getNowPeriodValue(int Month){
        String value="";
        for (CounterSensorHistory counterSensorHistory : counterSensorHistorys) {
            value+=counterSensorHistory.getRecordValue();
        }
        return value;
    }

    
    public void addValue(double recordValue,Date recordDate) {
        if (counterSensorHistorys == null) {
            counterSensorHistorys = new ArrayList<>();
        }
        CounterSensorHistory addedValue = new CounterSensorHistory();
        addedValue.setRecordValue(recordValue,recordDate);
        counterSensorHistorys.add(addedValue);
    }

    public void addValue(boolean recordState,Date recordDate) {
        if (counterSensorHistorys == null) {
            counterSensorHistorys = new ArrayList<>();
        }
        CounterSensorHistory addedValue = new CounterSensorHistory();
        addedValue.setState(recordState,recordDate);
        counterSensorHistorys.add(addedValue);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNowValue(Date startDate, Date endDate) {
        int value=startValue;
        for (CounterSensorHistory counterSensorHistory : counterSensorHistorys) {
            if(counterSensorHistory.getRecordDate().after(startDate)&&counterSensorHistory.getRecordDate().before(endDate)){
                value+=counterSensorHistory.getRecordValue();
            }
        }
        return value;
    }
    
    public int getNowValue(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date start=new Date();
        try {
            start=sdf.parse("2016-01-01");
        } catch (ParseException ex) {
            System.out.println("Can not parse date in getValueNow");
        }
        
        return getNowValue(start,new Date());
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TariffZone)) {
            return false;
        }
        TariffZone other = (TariffZone) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "info.microsityv6.microsityv6.entitys.TariffZone[ id=" + id + " ]";
    }

}
