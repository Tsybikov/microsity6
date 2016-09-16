/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.entitys;

import info.microsityv6.microsityv6.enums.Role;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Panker-RDP
 */
@Entity(name = "j_User")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String mainEmail="";
    private String passwordHash="";
    private List<String> otherEmails;
    private List<String> phones;
    private boolean rememberMe=false;
    private String lastSession="";
    @Column(name = "j_role")
    private Role role;
    private double balance;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar lastVisit;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Facility> facilitys;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Message> messages;
            
    public void addEmail(String email){
        if(otherEmails==null)otherEmails=new ArrayList<>();
        otherEmails.add(email);
    }
    
    public void addPhone(String phone){
        if(phones==null)phones=new ArrayList<>();
        phones.add(phone);
    }
    
    public void addFacility(Facility facility){
        if(facilitys==null)facilitys=new ArrayList<>();
        facilitys.add(facility);
    }
    
    public void addMessage(String message){
        if(messages==null)messages=new ArrayList<>();
        Message ms=new Message();
        ms.setMessage(message);
        messages.add(ms);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMainEmail() {
        return mainEmail;
    }

    public void setMainEmail(String mainEmail) {
        this.mainEmail = mainEmail;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = DigestUtils.md5Hex(password);
    }

    public List<String> getOtherEmails() {
        return otherEmails;
    }

    public void setOtherEmails(List<String> otherEmails) {
        this.otherEmails = otherEmails;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getLastSession() {
        return lastSession;
    }

    public void setLastSession(String lastSession) {
        this.lastSession = lastSession;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Calendar getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Calendar lastVisit) {
        this.lastVisit = lastVisit;
    }

    public List<Facility> getFasilitys() {
        return facilitys;
    }

    public void setFasilitys(List<Facility> fasilitys) {
        this.facilitys = fasilitys;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "info.microsityv6.microsityv6.entitys.User[ name=" + mainEmail + " ]";
    }
    
}
