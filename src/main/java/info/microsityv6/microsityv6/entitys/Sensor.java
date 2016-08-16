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
import javax.persistence.Column;
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
public class Sensor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String title;
    @Column(name="j_value")
    private String value;
    private boolean state;
    private boolean showInTheMap;
    private String espID;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<CounterSensorHistory> cshs;
    
    private List<String> alarmMails;
    private List<String> alarmPhones;
        
    @OneToMany(cascade = CascadeType.ALL)
    private List<Event> events;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if(cshs==null)cshs=new ArrayList<>();
        CounterSensorHistory csh = new CounterSensorHistory();
        try{
            csh.setRecordValue(Double.parseDouble(value));
        }catch(NumberFormatException ex){
            
        }
        this.value = value;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isShowInTheMap() {
        return showInTheMap;
    }

    public void setShowInTheMap(boolean showInTheMap) {
        this.showInTheMap = showInTheMap;
    }

    public String getEspID() {
        return espID;
    }

    public void setEspID(String espID) {
        this.espID = espID;
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

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
    
    public void addAlarmMail(String mail){
        if(alarmMails==null)alarmMails=new ArrayList<>();
        alarmMails.add(mail);
    }
    
    public void addAlarmPhones(String phone){
        if(alarmPhones==null)alarmPhones=new ArrayList<>();
        alarmPhones.add(phone);
    }
    
    public void addEvent(Event event){
        if(events==null)events=new ArrayList<>();
        events.add(event);
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
        if (!(object instanceof Sensor)) {
            return false;
        }
        Sensor other = (Sensor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "info.microsityv6.microsityv6.entitys.Sensor[ id=" + id + " ]";
    }
    
}
