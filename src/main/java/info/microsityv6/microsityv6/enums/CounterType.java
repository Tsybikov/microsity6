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
public enum CounterType {
    WATT("Электросчетчик"),GAS("Газовый счетчик"),WATER("Водяной счетчик"),WARM("Тепловой счетчик"),NOT_SET_UP("Не настроено");
    
    private String about;

    private CounterType(String about) {
        this.about = about;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
    
    
}
