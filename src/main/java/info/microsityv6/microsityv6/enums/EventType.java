/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.enums;

/**
 *
 * @author Panker-RDP
 */
public enum EventType {
    VALUE("Событие по значению"),STATE("Событие по состоянию"),DATE("Событие по дате/времени");
    private String about;
    EventType(String about){
       this.about=about; 
    }
    
    public String getabout(){
        return about;
    }
}
