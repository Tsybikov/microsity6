/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.support;

import info.microsityv6.microsityv6.entitys.Sensor;
import java.util.List;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Panker-RDP
 */
public class Chart<T> {

    private ChartModel chartModel;
    private String chartType;
    private String title;

//Business Logic
    //Charts for Counters
    public Chart(TariffSummInfo tsi, String title) {
        LineChartModel chartModel = new LineChartModel();
        chartModel.setTitle(title + " Динамика потребления");
        chartModel.addSeries(tsi.getChartSeries());
        chartModel.setLegendPosition("n");
        chartModel.setAnimate(true);
        chartModel.setShowPointLabels(false);
        this.chartModel=chartModel;
        chartType="line";
    }

    public Chart(TariffViewClass tvc,String title) {
        PieChartModel chartModel = new PieChartModel();
        chartModel.setTitle(title+" Сравнение потребления");
        for (TariffSummInfo tsi : tvc.getTsis()) {
            chartModel.set(tsi.getTariffName(), tsi.getSummValue());
        }
        chartModel.setTitle(tvc.getMonth());
        chartModel.setLegendPosition("w");
        chartModel.setDiameter(100);
        this.chartModel = chartModel;
        chartType="pie";
    }

    public Chart(List<TariffViewClass> tvcs) {
        BarChartModel chartModel = new BarChartModel();
        for (TariffViewClass tvc : tvcs) {
            if (tvc.getTsis().size() > 1) {
                chartModel = new HorizontalBarChartModel();
                for (TariffSummInfo tsi : tvc.getTsis()) {
                    ChartSeries cs = new ChartSeries();
                    cs.set(tvcs.indexOf(tvc), (tsi.getSummValue() - tsi.getStartValue()));
                    chartModel.addSeries(cs);
                }
                chartModel.setStacked(true);
            } else {
                TariffSummInfo tsi=tvc.getTsis().get(0);
                ChartSeries cs = new ChartSeries();
                cs.set(tvcs.indexOf(tvc), (tsi.getSummValue() - tsi.getStartValue()));
                chartModel.addSeries(cs);
            }
        }
        this.chartModel=chartModel;
        chartType="bar";
    }
    //Charts for Sensors
    public Chart(Sensor sensor, String title){
        LineChartModel chartModel= new LineChartModel();
        
    }
//Getters&Setters
    public ChartModel getChartModel() {
        return chartModel;
    }

    public void setChartModel(ChartModel chartModel) {
        this.chartModel = chartModel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }
    
    
}
