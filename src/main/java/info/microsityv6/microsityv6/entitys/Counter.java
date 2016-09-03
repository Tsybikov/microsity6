/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.entitys;

import info.microsityv6.microsityv6.enums.CounterType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
    private String espId;
    private CounterType counterType;

    @OneToMany(cascade = CascadeType.ALL)
    private List<TariffZone> tariffZones;

    private List<String> alarmMails;
    private List<String> alarmPhones;

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

    public void addAlarmMail(String mail) {
        if (alarmMails == null) {
            alarmMails = new ArrayList<>();
        }
        alarmMails.add(mail);
    }

    public void addAlarmPhones(String phone) {
        if (alarmPhones == null) {
            alarmPhones = new ArrayList<>();
        }
        alarmPhones.add(phone);
    }

    public CounterType getCounterType() {
        return counterType;
    }

    public void setCounterType(CounterType counterType) {
        this.counterType = counterType;
    }

    public List<TariffZone> getTariffZones() {
        return tariffZones;
    }

    public void setTariffZones(List<TariffZone> tariffZones) {
        this.tariffZones = tariffZones;
    }

    public void addValue(double value) {
        for (TariffZone tariffZone : tariffZones) {
            Calendar now = Calendar.getInstance();
            if (now.get(Calendar.HOUR) >= tariffZone.getHourStart() && now.get(Calendar.HOUR) <= tariffZone.getHourEnd()
                    && now.get(Calendar.MINUTE) >= tariffZone.getMinuteStart() && now.get(Calendar.MINUTE) <= tariffZone.getMinuteEnd()) {
                tariffZone.addValue(value);                
            }
        }
    }
    public void addValue(boolean state) {
        for (TariffZone tariffZone : tariffZones) {
            Calendar now = Calendar.getInstance();
            if (now.get(Calendar.HOUR) >= tariffZone.getHourStart() && now.get(Calendar.HOUR) <= tariffZone.getHourEnd()
                    && now.get(Calendar.MINUTE) >= tariffZone.getMinuteStart() && now.get(Calendar.MINUTE) <= tariffZone.getMinuteEnd()) {
                tariffZone.addValue(state);                
            }
        }
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
