/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.entitys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Panker-RDP
 */
@Entity
@Table(name = "smartsys")
public class Smartsys implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;

    private static final long serialVersionUID = 1L;
    
    @Column(name = "msg_id")
    private String msgId;
    @Column(name = "number")
    private String number;
    @Column(name = "sign")
    private String sign;
    @Column(name = "message")
    private String message;
    @Column(name = "wappush")
    private String wappush;
    @Column(name = "is_flash")
    private boolean isFlash;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cost")
    private BigDecimal cost;
    @Column(name = "balance")
    private BigDecimal balance;
    @Column(name = "added")
    @Temporal(TemporalType.TIMESTAMP)
    private Date added;
    @Column(name = "send_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendTime;
    @Column(name = "sended")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sended;
    @Column(name = "received")
    @Temporal(TemporalType.TIMESTAMP)
    private Date received;
    @Size(max = 3)
    @Column(name = "error_code")
    private String errorCode;
    @Size(max = 8)
    @Column(name = "status")
    private String status;

    public Smartsys() {
    }

    public Smartsys(Integer id) {
        this.id = id;
    }

    public Smartsys(String number, String sign, String message, String wappush, boolean isFlash) {
        this.number = number;
        this.sign = sign;
        this.message = message;
        this.wappush = wappush;
        this.isFlash = isFlash;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWappush() {
        return wappush;
    }

    public void setWappush(String wappush) {
        this.wappush = wappush;
    }

    public boolean getIsFlash() {
        return isFlash;
    }

    public void setIsFlash(boolean isFlash) {
        this.isFlash = isFlash;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getAdded() {
        return added;
    }

    public void setAdded(Date added) {
        this.added = added;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getSended() {
        return sended;
    }

    public void setSended(Date sended) {
        this.sended = sended;
    }

    public Date getReceived() {
        return received;
    }

    public void setReceived(Date received) {
        this.received = received;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(object instanceof Smartsys)) {
            return false;
        }
        Smartsys other = (Smartsys) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "info.microsityv6.microsityv6.entitys.Smartsys[ id=" + id + " ]";
    }
    
    
}
