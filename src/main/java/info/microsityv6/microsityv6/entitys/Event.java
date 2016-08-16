/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.entitys;

import info.microsityv6.microsityv6.enums.EventType;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author Panker-RDP
 */
@Entity(name = "j_Event")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String espId;
    private EventType eventType;
    private boolean sendEmails;
    private boolean sendSMS;
    private boolean actionController;
    private Long controllerId;
    private double actionValue;
    private boolean actionState;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar acrionDate;
    @Temporal(javax.persistence.TemporalType.TIME)
    private Calendar acrionTime;
    private boolean complete=false;
    
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

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public boolean isSendEmails() {
        return sendEmails;
    }

    public void setSendEmails(boolean sendEmails) {
        this.sendEmails = sendEmails;
    }

    public boolean isSendSMS() {
        return sendSMS;
    }

    public void setSendSMS(boolean sendSMS) {
        this.sendSMS = sendSMS;
    }

    public boolean isActionController() {
        return actionController;
    }

    public void setActionController(boolean actionController) {
        this.actionController = actionController;
    }

    public Long getControllerId() {
        return controllerId;
    }

    public void setControllerId(Long controllerId) {
        this.controllerId = controllerId;
    }

    public double getActionValue() {
        return actionValue;
    }

    public void setActionValue(double actionValue) {
        this.actionValue = actionValue;
    }

    public boolean isActionState() {
        return actionState;
    }

    public void setActionState(boolean actionState) {
        this.actionState = actionState;
    }

    public Calendar getAcrionDate() {
        return acrionDate;
    }

    public void setAcrionDate(Calendar acrionDate) {
        this.acrionDate = acrionDate;
    }

    public Calendar getAcrionTime() {
        return acrionTime;
    }

    public void setAcrionTime(Calendar acrionTime) {
        this.acrionTime = acrionTime;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
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
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "info.microsityv6.microsityv6.entitys.Event[ id=" + id + " ]";
    }
    
}
