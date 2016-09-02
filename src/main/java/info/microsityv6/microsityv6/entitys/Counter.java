/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.entitys;

import info.microsityv6.microsityv6.enums.CounterType;
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
public class Counter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String title;
    private double valueNow;
    private double earlyValue;
    private String espId;
    private CounterType counterType;
    
    private List<String> alarmMails;
    private List<String> alarmPhones;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<CounterSensorHistory> counterHistorys;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Event> events;

        
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEspId() {
        return espId;
    }

    public void setEspId(String espId) {
        this.espId = espId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getValueNow() {
        return valueNow;
    }

    public void setValueNow(double valueNow) {
        CounterSensorHistory csh=new CounterSensorHistory();
        csh.setRecordValue(valueNow);
        if(counterHistorys==null)counterHistorys=new ArrayList<>();
        counterHistorys.add(csh);
        this.valueNow = valueNow;
    }

    public double getEarlyValue() {
        return earlyValue;
    }

    public void setEarlyValue(double earlyValue) {
        this.earlyValue = earlyValue;
    }

    public List<CounterSensorHistory> getCounterHistorys() {
        return counterHistorys;
    }

    public void setCounterHistorys(List<CounterSensorHistory> counterHistorys) {
        this.counterHistorys = counterHistorys;
    }

    public List<String> getAlarmMails() {
        return alarmMails;
    }

    public void setAlarmMails(List<String> alarmMails) {
        this.alarmMails = alarmMails;
    }

    public List<String> getAlarmPhones() {
        return alarmPhones;
    }

    public void setAlarmPhones(List<String> alarmPhones) {
        this.alarmPhones = alarmPhones;
    }

    public void addAlarmMail(String mail){
        if(alarmMails==null)alarmMails=new ArrayList<>();
        alarmMails.add(mail);
    }
    
    public void addAlarmPhones(String phone){
        if(alarmPhones==null)alarmPhones=new ArrayList<>();
        alarmPhones.add(phone);
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
    
    public void addEvent(Event event){
        if(events==null)events=new ArrayList<>();
        events.add(event);
    }

    public CounterType getCounterType() {
        return counterType;
    }

    public void setCounterType(CounterType counterType) {
        this.counterType = counterType;
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
        if (!(object instanceof Counter)) {
            return false;
        }
        Counter other = (Counter) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "info.microsityv6.microsityv6.entitys.Counter[ id=" + id + " ]";
    }
    
}
