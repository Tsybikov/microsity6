/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Panker-RDP
 */
@Entity
@Table(name = "sensors_data")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SensorsData.findAll", query = "SELECT s FROM SensorsData s"),
    @NamedQuery(name = "SensorsData.findById", query = "SELECT s FROM SensorsData s WHERE s.id = :id"),
    @NamedQuery(name = "SensorsData.findByDtype", query = "SELECT s FROM SensorsData s WHERE s.dtype = :dtype"),
    @NamedQuery(name = "SensorsData.findByIsBool", query = "SELECT s FROM SensorsData s WHERE s.isBool = :isBool"),
    @NamedQuery(name = "SensorsData.findByDt", query = "SELECT s FROM SensorsData s WHERE s.dt = :dt"),
    @NamedQuery(name = "SensorsData.findByIsAction", query = "SELECT s FROM SensorsData s WHERE s.isAction = :isAction"),
    @NamedQuery(name = "SensorsData.findByPinNum", query = "SELECT s FROM SensorsData s WHERE s.pinNum = :pinNum"),
    @NamedQuery(name = "SensorsData.findBySensorId", query = "SELECT s FROM SensorsData s WHERE s.sensorId = :sensorId"),
    @NamedQuery(name = "SensorsData.findByTiming", query = "SELECT s FROM SensorsData s WHERE s.timing = :timing"),
    @NamedQuery(name = "SensorsData.findByValue", query = "SELECT s FROM SensorsData s WHERE s.value = :value"),
    @NamedQuery(name = "SensorsData.findByWasRead", query = "SELECT s FROM SensorsData s WHERE s.wasRead = :wasRead")})
public class SensorsData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Size(max = 31)
    @Column(name = "DTYPE")
    private String dtype;
    @Column(name = "IS_BOOL")
    private Boolean isBool;
    @Column(name = "DT", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dt;
    @Column(name = "IS_ACTION")
    private Boolean isAction;
    @Column(name = "PIN_NUM")
    private Short pinNum;
    @Size(max = 255)
    @Column(name = "SENSOR_ID")
    private String sensorId;
    @Column(name = "TIMING")
    private Integer timing;
    @Size(max = 255)
    @Column(name = "VALUE")
    private String value;
    @Column(name = "WAS_READ")
    private Boolean wasRead;

    public SensorsData() {
    }

    public SensorsData(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public Boolean getIsBool() {
        return isBool;
    }

    public void setIsBool(Boolean isBool) {
        this.isBool = isBool;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public Boolean getIsAction() {
        return isAction;
    }

    public void setIsAction(Boolean isAction) {
        this.isAction = isAction;
    }

    public Short getPinNum() {
        return pinNum;
    }

    public void setPinNum(Short pinNum) {
        this.pinNum = pinNum;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public Integer getTiming() {
        return timing;
    }

    public void setTiming(Integer timing) {
        this.timing = timing;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getWasRead() {
        return wasRead;
    }

    public void setWasRead(Boolean wasRead) {
        this.wasRead = wasRead;
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
        if (!(object instanceof SensorsData)) {
            return false;
        }
        SensorsData other = (SensorsData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "info.microsityv6.microsityv6.entitys.SensorsData[ id=" + id + " ]";
    }
    
}
