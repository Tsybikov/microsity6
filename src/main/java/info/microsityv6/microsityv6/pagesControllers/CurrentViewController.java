package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Facility;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@Named(value = "currentViewController")
@SessionScoped
public class CurrentViewController extends PageController implements Serializable {

    public CurrentViewController() {
    }
    
    private Facility currentView;
    
    
    public Facility getCurrentView() {
        if(currentView==null){
            if(!super.getUserController().getCurrent().getFasilitys().isEmpty()){
                currentView=super.getUserController().getCurrent().getFasilitys().get(0);
            }
        }
        return currentView;
    }

    public void setCurrentView(Facility currentView) {
        this.currentView = currentView;
    }
    
}
