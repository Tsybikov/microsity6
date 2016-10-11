package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Counter;
import info.microsityv6.microsityv6.support.Chart;
import info.microsityv6.microsityv6.support.TariffSummInfo;
import info.microsityv6.microsityv6.support.TariffViewClass;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.primefaces.model.chart.LineChartModel;

@Named(value = "chartController")
@RequestScoped
public class ChartController {
    
    @Inject
    CounterPageController cpc;
    private List<Chart> charts;
    private String effect;
    
    public ChartController() {
    }
    
    public List<Chart> getCharts(Counter counter){
        charts=new ArrayList<>();
        List<TariffViewClass> tvcs=cpc.getTariffValues(counter);
        for (TariffViewClass tvc : tvcs) {
            if(tvc.getTsis().size()>1){
                charts.add(new Chart(tvc,tvc.getMonth()));//Сравнение потребления по тарифным планам
                Chart complexChart=new Chart(tvc.getTsis().get(0),tvc.getMonth());//Динамика потребления по тарифным планам
                for(int i=1;i<tvc.getTsis().size();i++){
                    ((LineChartModel)complexChart.getChartModel()).addSeries(((LineChartModel)new Chart(tvc.getTsis().get(i),tvc.getMonth()).getChartModel()).getSeries().get(0));//Ипануца сложный код
                }
                complexChart.getChartModel().setLegendPosition("se");
                charts.add(complexChart);
            }
            if(tvc.getTsis().size()==1){
                charts.add(new Chart(tvc.getTsis().get(0),tvc.getMonth()));
            }            
        }
        if(tvcs.size()>3){
            charts.add(new Chart(tvcs));
        }
        return charts;
    }

    public String getEffect() {
        Random rnd=new Random();
        switch(rnd.nextInt(3)){
            case 0: return"fade";
            case 1: return"zoom";
            case 2: return"turnDown";
            case 3: return"shuffle";
        }
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }
    
    
}
