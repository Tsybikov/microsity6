package info.microsityv6.microsityv6.support;

import info.microsityv6.microsityv6.entitys.CounterSensorHistory;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class TariffViewClass {

    private String month;
    private String controlMark;
    private List<TariffSummInfo> tsis=new ArrayList<>();
    
    public TariffViewClass() {
    }

    public TariffViewClass(CounterSensorHistory csh) {
        switch (csh.getRecordDate().get(Calendar.MONTH)) {
            case 0:
                month = "Январь";
                break;
            case 1:
                month = "Февраль";
                break;
            case 2:
                month = "Март";
                break;
            case 3:
                month = "Апрель";
                break;
            case 4:
                month = "Май";
                break;
            case 5:
                month = "Июнь";
                break;
            case 6:
                month = "Июль";
                break;
            case 7:
                month = "Август";
                break;
            case 8:
                month = "Сентябрь";
                break;
            case 9:
                month = "Октябрь";
                break;
            case 10:
                month = "Ноябрь";
                break;
            case 11:
                month = "Деабрь";
                break;
            default:
                month = "Undefined";
        }
        month += " " + csh.getRecordDate().get(Calendar.YEAR);
        controlMark = csh.getRecordDate().get(Calendar.YEAR) + "" + csh.getRecordDate().get(Calendar.MONTH);

    }

    public List<TariffSummInfo> getTsis() {
        return tsis;
    }

    public void setTsis(List<TariffSummInfo> tsis) {
        this.tsis = tsis;
    }
    
    

    public String getControlMark() {
        return controlMark;
    }

    public void setControlMark(String controlMark) {
        this.controlMark = controlMark;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void sendData(CounterSensorHistory csh, String name) {
        if (tsis.isEmpty()) {
            TariffSummInfo tsi = new TariffSummInfo(name);  //Create a new element Collection
            tsi.addValue((int) csh.getRecordValue());
            tsis.add(tsi);
        }
        Iterator tsiIt = tsis.iterator();
        while (tsiIt.hasNext()) {
            TariffSummInfo tsi = (TariffSummInfo) tsiIt.next();
            if (tsi.getTariffName().equals(name)) {
                tsi.addValue((int) csh.getRecordValue()); //if exist when added value
                tsis.set(tsis.indexOf(tsi), tsi);
            }else{
                //TODO: Не забыть исключить возможность называть одинаково тарифы в одном счетчике
                boolean find=false;
                for (TariffSummInfo tsi1 : tsis) {
                    if(tsi1.getTariffName().equals(name)){//if not yet find
                        find=true;
                        break;
                    }
                }
                if(!find){
                    TariffSummInfo tsiPlus = new TariffSummInfo(name);//if not find, create new element
                    tsiPlus.addValue((int) csh.getRecordValue());
                    tsis.add(tsiPlus);
                }
            }
        }
    }

    
    
    @Override
    public String toString(){
        String str="";
        str+=month+":";
        for (TariffSummInfo tsi : tsis) {
            str+="->"+tsi.getTariffName()+"\r\n";
            str+="StartValue: "+tsi.getStartValue()+"\r\n";
            str+="EndValue: "+tsi.getSummValue();
        }
        return str;
    }

}
