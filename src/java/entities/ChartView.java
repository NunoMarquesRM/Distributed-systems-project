package entities;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.primefaces.model.chart.PieChartModel;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.ItemSelectEvent;

@Named
@RequestScoped
public class ChartView implements Serializable {
    
    private PieChartModel pieModel1;
 
    @PostConstruct
    public void init() {
        createPieModels();
    }
 
    public void itemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
                "Item Index: " + event.getItemIndex() + ", Series Index:" + event.getSeriesIndex());
 
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
 
    public PieChartModel getPieModel1() {
        return pieModel1;
    }
    
    
    private void createPieModels() {
        createPieModel1();
    }
 
    private void createPieModel1() {
        pieModel1 = new PieChartModel();
 
        pieModel1.set("Brand 1", 540);
        pieModel1.set("Brand 2", 325);
        pieModel1.set("Brand 3", 702);
        pieModel1.set("Brand 4", 421);
        pieModel1.set("Brand 5", 5);
        pieModel1.set("Brand 6", 78);
        pieModel1.set("Brand 7", 12);
        pieModel1.set("Brand 8", 456);
 
        pieModel1.setTitle("Estatísticas");
        pieModel1.setLegendPosition("w");
        pieModel1.setShadow(false);
    }
 
 
 
}