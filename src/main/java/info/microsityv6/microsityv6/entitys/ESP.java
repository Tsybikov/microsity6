/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.entitys;

import info.microsityv6.microsityv6.enums.DeviceType;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

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
    @Column(name = "FIRMWARE_ID")
    private String firmware_id;
    @Column(name = "STATUS")
    private boolean status;
    @Column(name="INPUT_DATE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar input_date;
    @Column(name="UPDATE_DATE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar update_date;
    @Column(name="ESP_DEVICES")
    private List<Pin> pins;

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

    public String getFirmware_id() {
        return firmware_id;
    }

    public void setFirmware_id(String firmware_id) {
        this.firmware_id = firmware_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Calendar getInput_date() {
        return input_date;
    }

    public void setInput_date(Calendar input_date) {
        this.input_date = input_date;
    }

    public Calendar getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Calendar update_date) {
        this.update_date = update_date;
    }

    public List<Pin> getPins() {
        return pins;
    }

    public void setPins(List<Pin> pins) {
        this.pins = pins;
    }

    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    private class Pin{
        DeviceType type;
        int pin_num;
        String relay_id;

        public DeviceType getType() {
            return type;
        }

        public void setType(DeviceType type) {
            this.type = type;
        }

        public int getPin_num() {
            return pin_num;
        }

        public void setPin_num(int pin_num) {
            this.pin_num = pin_num;
        }

        public String getRelay_id() {
            return relay_id;
        }

        public void setRelay_id(String relay_id) {
            this.relay_id = relay_id;
        }
        
        
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
