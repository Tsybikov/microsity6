/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.support;

import java.util.Iterator;
import java.util.Map;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Panker-RDP
 */
public class TariffSummInfo {

    private String tariffName;
    private int startValue = 0;
    private int summValue = 0;
    private LineChartSeries chartSeries=new LineChartSeries();
    private int chartCounter=0;
    private int chartCounter2=0;
    private int tempSumm=0;

    public TariffSummInfo(String name) {
        this.tariffName = name;
        chartSeries.setLabel(name);
    }

    public void addValue(int value) {
        summValue += value;
        chartCounter++;
        tempSumm+=value;
        if(chartCounter==60){
            chartSeries.set(chartCounter2++, tempSumm);
            chartCounter=0;
            tempSumm=0;
        }
        
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

    public LineChartSeries getChartSeries() {
        int size=chartSeries.getData().size()/10;
        int count=0;
        int value=startValue;
        int num=1;
        LineChartSeries lcs=new LineChartSeries();
        lcs.setLabel(chartSeries.getLabel());
        for (Map.Entry<Object, Number> entry : chartSeries.getData().entrySet()) {
            if(count<=size){
                count++;value+=(int)entry.getKey();
            }else{
                lcs.set(num, value);
                num++;count=0;
            }
        }
        return lcs;
    }

    public void setChartSeries(LineChartSeries chartSeries) {
        this.chartSeries = chartSeries;
    }
    
    
    
}
