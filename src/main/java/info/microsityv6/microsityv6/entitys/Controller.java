/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.entitys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Panker-RDP
 */
@Entity
public class Controller implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String esp_id;
    private int pinNum;
    @Column(name = "J_STATE")
    private boolean state;
    private boolean showInTheMap = true;
    @Column(name = "HOME_SHOW")
    private boolean showInTheMain = false;
    private List<String> alarmMails;
    private List<String> alarmPhones;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CounterSensorHistory> controllerHistorys;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Event> events;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        String line = "";
        if (state) {
            Runtime runtime = Runtime.getRuntime();
            String cmd[] = {"mosquitto_pub", "-u", "micro", "-P", "microcity", "-t", "sensors/" + esp_id + "/0/action", "-m", "1"};
            try {
                Process process = runtime.exec(cmd);
                BufferedReader br_e = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                line = "";
                while ((line = br_e.readLine()) != null) {
                    System.out.println(line);
                }
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Команда успешно отправлена"));
                if(controllerHistorys==null)controllerHistorys=new ArrayList<>();
                CounterSensorHistory csh=new CounterSensorHistory();
                csh.setState(state);
            } catch (IOException ex) {
                System.out.println("Команда включения не прошла" + line);
            }
        } else{
            Runtime runtime = Runtime.getRuntime();
            String cmd[] = {"mosquitto_pub", "-u", "micro", "-P", "microcity", "-t", "sensors/" + esp_id + "/0/action", "-m", "0"};
            try {
                Process process = runtime.exec(cmd);
                BufferedReader br_e = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                line = "";
                while ((line = br_e.readLine()) != null) {
                    System.out.println(line);
                }
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
                if(controllerHistorys==null)controllerHistorys=new ArrayList<>();
                CounterSensorHistory csh=new CounterSensorHistory();
                csh.setState(state);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Команда успешно отправлена"));
            } catch (IOException ex) {
                System.out.println("Команда отключения не прошла" + line);
            }
        }
        this.state = state;
    }

    public List<String> getAlarmMails() {
        return alarmMails;
    }

    public void setAlarmMails(List<String> alarmMails) {
        this.alarmMails = alarmMails;
    }

    public List<String> getAlarmPhones() {
        return alarmPhones;
    }

    public void setAlarmPhones(List<String> alarmPhones) {
        this.alarmPhones = alarmPhones;
    }

    public List<CounterSensorHistory> getControllerHistorys() {
        return controllerHistorys;
    }

    public void setControllerHistorys(List<CounterSensorHistory> controllerHistorys) {
        this.controllerHistorys = controllerHistorys;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
    
    public void addAlarmMail(String mail){
        if(alarmMails==null)alarmMails=new ArrayList<>();
        alarmMails.add(mail);
    }
    
    public void addAlarmPhones(String phone){
        if(alarmPhones==null)alarmPhones=new ArrayList<>();
        alarmPhones.add(phone);
    }
    
    public void addEvent(Event event){
        if(events==null)events=new ArrayList<>();
        events.add(event);
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public String getEsp_id() {
        return esp_id;
    }

    public void setEsp_id(String esp_id) {
        this.esp_id = esp_id;
    }

    public int getPinNum() {
        return pinNum;
    }

    public void setPinNum(int pinNum) {
        this.pinNum = pinNum;
    }

    public boolean isShowInTheMap() {
        return showInTheMap;
    }

    public void setShowInTheMap(boolean showInTheMap) {
        this.showInTheMap = showInTheMap;
    }

    public boolean isShowInTheMain() {
        return showInTheMain;
    }

    public void setShowInTheMain(boolean showInTheMain) {
        this.showInTheMain = showInTheMain;
    }
    
    
    
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Controller)) {
            return false;
        }
        Controller other = (Controller) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "info.microsityv6.microsityv6.entitys.Controller[ id=" + id + " ]";
    }

}
