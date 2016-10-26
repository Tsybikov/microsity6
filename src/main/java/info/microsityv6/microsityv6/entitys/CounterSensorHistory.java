package info.microsityv6.microsityv6.entitys;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CounterSensorHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "recordDate", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar recordDate=Calendar.getInstance();
    private double recordValue;
    @Column(name = "j_State")
    private boolean state;
            
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getRecordValue() {
        return recordValue;
    }

    public void setRecordValue(double recordValue) {
        this.recordValue = recordValue;
        recordDate=Calendar.getInstance();
    }
    public void setRecordValue(double recordValue,Date recordDate) {
        this.recordValue = recordValue;
        this.recordDate.setTime(recordDate);
    }

    public Calendar getRecordDate() {
        return recordDate;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
        recordDate=Calendar.getInstance();
    }
    
    public void setState(boolean state,Date recordDate) {
        this.state = state;
        this.recordDate.setTime(recordDate);
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
        if (!(object instanceof CounterSensorHistory)) {
            return false;
        }
        CounterSensorHistory other = (CounterSensorHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ""+recordDate.toString()+recordValue;
    }
    
    
}
