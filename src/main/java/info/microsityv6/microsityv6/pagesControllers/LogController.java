/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Log;
import info.microsityv6.microsityv6.enums.LoggerLevel;
import info.microsityv6.microsityv6.facades.LogFacade;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Panker-RDP
 */
@Named(value = "logController")
@SessionScoped
public class LogController extends PageController implements Serializable{
    
    @EJB
    private LogFacade lf;
    private List<Log> logs;
    private List<Log> filteredLogs;
    private LoggerLevel selectedLevel;
    
    public LogController() {
    }

    public List<Log> getLogs() {
        if(selectedLevel==null){
            return lf.findAll();
        }
        logs=new ArrayList<>();
        for (Log log : lf.findAll()) {
            if(log.getLoggerLevel().equals(selectedLevel)){
                logs.add(log);
            }
        }
        return logs;
    }
    
    public List<LoggerLevel> getLevels(){
        List<LoggerLevel> levels=new ArrayList<>();
        levels.addAll(Arrays.asList(LoggerLevel.values()));
        return levels;
    }
    
    public String getDate(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy HH:mm");
        if(date!=null)return sdf.format(date);
        return "нет данных";
    }
    
    public List<Log> getFilteredLogs() {
        return filteredLogs;
    }

    public void setFilteredLogs(List<Log> filteredLogs) {
        this.filteredLogs = filteredLogs;
    }   

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    public LoggerLevel getSelectedLevel() {
        return selectedLevel;
    }

    public void setSelectedLevelAsDebug() {
        this.selectedLevel = LoggerLevel.DEBUG;
    }
    public void setSelectedLevelAsError() {
        this.selectedLevel = LoggerLevel.ERROR;
    }
    public void setSelectedLevelAsFatal() {
        this.selectedLevel = LoggerLevel.FATAL;
    }
    public void setSelectedLevelAsInfo() {
        this.selectedLevel = LoggerLevel.INFO;
    }
    public void setSelectedLevelAsWarn() {
        this.selectedLevel = LoggerLevel.WARN;
    }
    public void setSelectedLevelAsEmpty() {
        this.selectedLevel = null;
    }
    
    
    
    
    
}
