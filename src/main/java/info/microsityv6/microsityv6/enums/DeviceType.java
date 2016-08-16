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
public enum DeviceType {
    SECURITY("Безопасность"),ALERT("Тревожный датчик"),RELAY("Контролер"),COUNTER("Счетчик"),DATA("Датчик");
    String itIs;

    private DeviceType(String itIs) {
        this.itIs = itIs;
    }
    
    public String getItIs(){
        return itIs;
    }
}
