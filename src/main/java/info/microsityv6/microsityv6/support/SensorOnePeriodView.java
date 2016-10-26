/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.support;

import info.microsityv6.microsityv6.entitys.CounterSensorHistory;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Panker-RDP
 */
public class SensorOnePeriodView {

    private String month;
    private double startValue = -1.0;
    private double endValue = 0.0;
    private String controlMark;
    private int count = 0;
    private String chartType;
    private LineChartSeries middleValueCS = new LineChartSeries();
    private LineChartSeries realValueCS = new LineChartSeries();
    private ChartModel cm;

    public SensorOnePeriodView(CounterSensorHistory csh, double startValue) {
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
        this.startValue = startValue;

    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getStartValue() {
        return startValue;
    }

    public void setStartValue(double startValue) {
        this.startValue = startValue;
    }

    public double getEndValue() {
        return endValue;
    }

    public void setEndValue(double endValue) {
        this.endValue = endValue;
    }

    public String getControlMark() {
        return controlMark;
    }

    public void setControlMark(String controlMark) {
        this.controlMark = controlMark;
    }

    public void addValue(double value, boolean isCounter) {
        if (isCounter) {
            endValue += value;
            count++;
        } else {
            endValue = (endValue + value) / 2.0;
            count++;
        }
        addToLineChart(value, isCounter);
    }

    public void addValue(boolean state) {
        if (state) {
            endValue++;
        }
        count++;
    }

    private void addToLineChart(double value, boolean isCounter) {
        if (isCounter) {
            realValueCS.set(count, endValue);
            middleValueCS.set(count, endValue / (double) count);
        } else {
            realValueCS.set(count, value);
        }

    }

    public ChartModel getCm() {
        if (!realValueCS.getData().isEmpty()) {
            LineChartModel lcm = new LineChartModel();
            lcm.setAnimate(true);
            lcm.addSeries(remasterSeries(realValueCS));
            lcm.addSeries(remasterSeries(middleValueCS));
            chartType = "line";
            return lcm;
        } else {
            PieChartModel pcm = new PieChartModel();
            pcm.set("Тревога", endValue);
            pcm.set("Норма", count - endValue);
            chartType = "pie";
            return pcm;
        }
    }

    private ChartSeries remasterSeries(LineChartSeries chartValueCS) {
        LineChartSeries remasteredChartSeries = new LineChartSeries();
        int size = chartValueCS.getData().size();
        int middle = (int) Math.floor((double) size / 30.0);
        for (int i = 1; i <= 30; i++) {
            int innerCount = 0;
            double innerMedianValue = 0.0;
            for (Map.Entry<Object, Number> entry : chartValueCS.getData().entrySet()) {
                if (innerCount == middle) {
                    remasteredChartSeries.set(i, innerMedianValue = (innerMedianValue + (double) entry.getValue()) / 2.0);
                    innerMedianValue = 0.0;
                    innerCount = 0;
                } else {
                    innerMedianValue = (innerMedianValue + (double) entry.getValue()) / 2.0;
                    innerCount++;
                }
            }
        }
        return remasteredChartSeries;
    }

    public String getChartType() {
        if (chartType == null) {
            getCm();
        }
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

}
