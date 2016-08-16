/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.entitys;

import info.microsityv6.microsityv6.enums.DeviceType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Panker-RDP
 */
@Entity
public class ESP implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "ESP_ID")
    private String espId;
    @Column(name = "DEVICE_TYPE")
    private DeviceType deviceType;
    @Column(name = "PIN_NUM")
    private int pinNum;
    @Column(name = "LAST_IP")
    private String lastIp;
    @Column(name="LAST_AP")
    private String lastAP;
    @Column(name="DEVICE_SERIES_ID")
    private String DeviceSeriesId;
    @Column(name = "IS_ACTION")
    private boolean isAction;

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

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public int getPinNum() {
        return pinNum;
    }

    public void setPinNum(int pinNum) {
        this.pinNum = pinNum;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public String getLastAP() {
        return lastAP;
    }

    public void setLastAP(String lastAP) {
        this.lastAP = lastAP;
    }

    public String getDeviceSeriesId() {
        return DeviceSeriesId;
    }

    public void setDeviceSeriesId(String DeviceSeriesId) {
        this.DeviceSeriesId = DeviceSeriesId;
    }

    public boolean isIsAction() {
        return isAction;
    }

    public void setIsAction(boolean isAction) {
        this.isAction = isAction;
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
        if (!(object instanceof ESP)) {
            return false;
        }
        ESP other = (ESP) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "info.microsityv6.microsityv6.entitys.ESP[ id=" + id + " ]";
    }
    
}
