/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.entitys;

import info.microsityv6.microsityv6.enums.LoggerLevel;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Panker-RDP
 */
@Entity
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LoggerLevel loggerLevel;
    private String mess;
    @Column(columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private Throwable exception;

    public Log() {
    }

    public Log(LoggerLevel loggerLevel, String mess) {
        this.loggerLevel = loggerLevel;
        this.mess = mess;
        this.date=new Date();
    }
    
    public Log(LoggerLevel loggerLevel, String mess, Throwable ex) {
        this.loggerLevel = loggerLevel;
        this.mess = mess;
        this.date=new Date();
        this.exception=ex;
    }
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(LoggerLevel level,String mess) {
        this.mess = mess;
        loggerLevel=level;
    }

    public LoggerLevel getLoggerLevel() {
        return loggerLevel;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getException() {
        return exception.getMessage();
    }

    public void setException(Throwable exception) {
        this.exception = exception;
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
        if (!(object instanceof Log)) {
            return false;
        }
        Log other = (Log) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "info.microsityv6.microsityv6.entitys.Log[ id=" + id + " ]";
    }
    
}
