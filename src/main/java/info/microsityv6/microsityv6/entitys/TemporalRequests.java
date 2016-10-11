/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.entitys;

import info.microsityv6.microsityv6.enums.RequestValidation;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
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
public class TemporalRequests implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "for_user")
    private Long userId;
    private boolean completed=false;
    private String requestString;
    @Column(columnDefinition = "DATETIME")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateTimeExpiried;
    private RequestValidation requestValidation;

    public void generateRequestString(){
        int charNum, count = 0;
        String str = "?req=";
        while (count < 25) {
            
            Random r=new Random(System.currentTimeMillis());
           
                while (true) {
                    charNum = (int) (Math.random()*1000);
                    if (charNum >= 65 && charNum <= 90) {
                        str += (char) charNum;
                        break;
                    }
                    if (charNum >= 97 && charNum <= 122) {
                        str += (char) charNum;
                        break;
                    }
                }
            
            count++;
        }
        setRequestString(str);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getRequestString() {
                return requestString;
    }

    public void setRequestString(String requestString) {
        this.requestString=requestString;
        Calendar expired = Calendar.getInstance();
        if (expired.get(Calendar.DAY_OF_YEAR) < 365) {
            expired.set(Calendar.DAY_OF_YEAR, expired.get(Calendar.DAY_OF_YEAR) + 1);
        } else {
            expired.set(Calendar.DAY_OF_YEAR, 2);
        }
        setDateTimeExpiried(expired.getTime());
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getDateTimeExpiried() {
        return dateTimeExpiried;
    }

    private void setDateTimeExpiried(Date dateTimeExpiried) {
        this.dateTimeExpiried = dateTimeExpiried;
    }

    public RequestValidation getRequestValidation() {       
        return requestValidation;
    }

    public void setRequestValidation(RequestValidation requestValidation) {
        this.requestValidation = requestValidation;
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
        if (!(object instanceof TemporalRequests)) {
            return false;
        }
        TemporalRequests other = (TemporalRequests) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "info.microsityv6.microsityv6.entitys.TemporalRequests[ id=" + id + " ]";
    }

}
