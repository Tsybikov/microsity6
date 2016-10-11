/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.entitys;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "j_Date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar date;
    private String title="";
    private String message="";
    private boolean readed=false;
    private boolean deleted=false;

    public Message() {
    }

    public Message(String title,String message) {
        this.title=title;
        this.message = message;
        this.date=Calendar.getInstance();
    }

    
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        this.date=Calendar.getInstance();
    }

    public Calendar getDate() {
        return date;
    }
    
    public String getDateAsString(){
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date.getTime());
    }

    public boolean isReaded() {
        return readed;
    }

    public String getTitle() {
        if(title==null)return "Без темы";
        if(title.length()<1)return "Без темы";
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "info.microsityv6.microsityv6.entitys.Message[ id=" + id + " ]";
    }
    
}
