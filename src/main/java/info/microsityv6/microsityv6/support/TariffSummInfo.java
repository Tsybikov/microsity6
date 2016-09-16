/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.support;

/**
 *
 * @author Panker-RDP
 */
public class TariffSummInfo {

    private String tariffName;
    private int startValue = 0;
    private int summValue = 0;

    public TariffSummInfo(String name) {
        this.tariffName = name;
    }

    public void addValue(int value) {
        summValue += value;
    }

    public int getSummValue() {
        return startValue + summValue;
    }

    public String getTariffName() {
        return tariffName;
    }

    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
    }

    public int getStartValue() {
        return startValue;
    }

    public void setStartValue(int startValue) {
        this.startValue = startValue;
    }
    
    
}
