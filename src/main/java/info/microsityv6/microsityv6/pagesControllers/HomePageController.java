package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.facades.FacilityFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named(value = "homePageController")
@SessionScoped
public class HomePageController extends PageController implements Serializable {

    private boolean needRender = false;
    private boolean haveCameras;
    private boolean haveControllers;
    private boolean haveCounters;
    private boolean haveSensors;
    private String oneCameraLink;
    private boolean twoCamera;
    private String twoCameraLink;
    private boolean threeCamera;
    private String threeCameraLink;
    private boolean fourCamera;
    private String fourCameraLink;
    
    @EJB
    private FacilityFacade facilityFacade;

    public HomePageController() {

    }

    public boolean isNeedRender() {

        return super.getCurrentFacility() != null;
    }

    public void setNeedRender(boolean needRender) {
        this.needRender = needRender;
    }

    public boolean isHaveCameras() {
        return !super.getCurrentFacility().getCameras().isEmpty();

    }

    public void setHaveCameras(boolean haveCameras) {
        this.haveCameras = haveCameras;
    }

    public boolean isHaveControllers() {
        return !super.getCurrentFacility().getControllers().isEmpty();
    }

    public void setHaveControllers(boolean haveControllers) {
        this.haveControllers = haveControllers;
    }

    public boolean isHaveCounters() {
        return !super.getCurrentFacility().getCounters().isEmpty();
    }

    public void setHaveCounters(boolean haveCounters) {
        this.haveCounters = haveCounters;
    }

    public boolean isHaveSensors() {
        return !super.getCurrentFacility().getSensors().isEmpty();
    }

    public void setHaveSensors(boolean haveSensors) {
        this.haveSensors = haveSensors;
    }

    public void saveFacilityChange() {
        super.saveChanges();
    }

    public void deleteFacility() {
        facilityFacade.remove(super.getCurrentFacility());
    }

    public String getOneCameraLink() {
        return super.getCurrentFacility().getCameras().get(0).getJPGLink();
    }

    public void setOneCameraLink(String oneCameraLink) {
        this.oneCameraLink = oneCameraLink;
    }

    public boolean isTwoCamera() {
        return super.getCurrentFacility().getCameras().size() > 1;
    }

    public void setTwoCamera(boolean twoCamera) {
        this.twoCamera = twoCamera;
    }

    public String getTwoCameraLink() {
        return super.getCurrentFacility().getCameras().get(1).getJPGLink();
    }

    public void setTwoCameraLink(String twoCameraLink) {
        this.twoCameraLink = twoCameraLink;
    }

    public boolean isThreeCamera() {
        return super.getCurrentFacility().getCameras().size() > 2;
    }

    public void setThreeCamera(boolean threeCamera) {
        this.threeCamera = threeCamera;
    }

    public String getThreeCameraLink() {
        return super.getCurrentFacility().getCameras().get(2).getJPGLink();
    }

    public void setThreeCameraLink(String threeCameraLink) {
        this.threeCameraLink = threeCameraLink;
    }

    public boolean isFourCamera() {
        return super.getCurrentFacility().getCameras().size() > 3;
    }

    public void setFourCamera(boolean fourCamera) {
        this.fourCamera = fourCamera;
    }

    public String getFourCameraLink() {
        return super.getCurrentFacility().getCameras().get(3).getJPGLink();
    }

    public void setFourCameraLink(String fourCameraLink) {
        this.fourCameraLink = fourCameraLink;
    }
    
}
