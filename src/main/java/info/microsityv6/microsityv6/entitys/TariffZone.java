/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.entitys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Panker-RDP
 */
@Entity
public class TariffZone implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    int hourStart;
    int minuteStart;
    int hourEnd;
    int minuteEnd;
    String nameTariff;
    int timeZone;

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

    public void addValue(double recordValue) {
        if (counterSensorHistorys == null) {
            counterSensorHistorys = new ArrayList<>();
        }
        CounterSensorHistory addedValue = new CounterSensorHistory();
        addedValue.setRecordValue(recordValue);
    }

    public void addValue(boolean recordState) {
        if (counterSensorHistorys == null) {
            counterSensorHistorys = new ArrayList<>();
        }
        CounterSensorHistory addedValue = new CounterSensorHistory();
        addedValue.setState(recordState);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
