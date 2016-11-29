package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Facility;
import info.microsityv6.microsityv6.entitys.Log;
import info.microsityv6.microsityv6.entitys.User;
import info.microsityv6.microsityv6.enums.LoggerLevel;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;
import java.util.Scanner;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.json.JSONObject;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@Named(value = "gmController")
@RequestScoped
public class GoogleMapsController extends PageController {

    private MapModel mapModel;
    private String center;
    private String id;

    public GoogleMapsController() {
    }

    public MapModel getMapModel(Object obj) {
        mapModel = new DefaultMapModel();
        if (obj instanceof Facility) {
            return getMapModel((Facility) obj);
        }
        if (obj instanceof User) {
            return getMapModel((User) obj);
        }
        return mapModel;
    }

    private MapModel getMapModel(User user) {
        mapModel = new DefaultMapModel();
        for (Facility fasility : user.getFasilitys()) {
            LatLng latLng = getGMapsAdress(fasility);
            mapModel.addOverlay(new Marker(latLng, fasility.getTitle(), fasility));
        }
        return mapModel;
    }

    private MapModel getMapModel(Facility facility) {
        mapModel = new DefaultMapModel();
        LatLng latLng = getGMapsAdress(facility);
        getGMapsAdress(facility);
        mapModel.addOverlay(new Marker(latLng));
        return mapModel;
    }

    private LatLng getGMapsAdress(Facility currentFacility) {
        String address = "";
        LatLng latLng;
        String lat, lng;
        address = currentFacility.getSity() + "," + currentFacility.getStreet() + "," + currentFacility.getHome();
        try {
            String urlS = "http://maps.google.com/maps/api/geocode/json?" + "sensor=false&address=" + URLEncoder.encode(address, "UTF-8");
            URL url = new URL(urlS);
            Scanner scan = new Scanner(url.openStream());
            String str = new String();
            while (scan.hasNext()) {
                str += scan.nextLine();
            }
            scan.close();

            JSONObject obj = new JSONObject(str);
            if (!obj.getString("status").equals("OK")) {
                lat = "54.3617753";
                lng = "18.6334365";
                latLng = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Не определены координаты для объекта "+currentFacility.getTitle()));
            } else {
                JSONObject res = obj.getJSONArray("results").getJSONObject(0);
                JSONObject loc
                        = res.getJSONObject("geometry").getJSONObject("location");
                lat = String.valueOf(loc.getDouble("lat"));
                lng = String.valueOf(loc.getDouble("lng"));
                latLng = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
            }

        } catch (IOException ex) {
            lat = "54.3617753";
            lng = "18.6334365";
            latLng = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Не определены координаты для объекта "+currentFacility.getTitle()));
            logFacade.create(new Log(LoggerLevel.ERROR, "Error in GoogleMapsController", ex));
        }
        return latLng;
    }

    public String getCenter(Object obj) {
        if (obj instanceof Facility) {
            return getCenter((Facility) obj);
        }
        if (obj instanceof User) {
            return getCenter((User) obj);
        }
        return "54.3617753" + ",18.6334365";
    }

    private String getCenter(Facility facility) {
        LatLng latLng = getGMapsAdress(facility);
        center = "" + latLng.getLat() + "," + latLng.getLng();
        return center;
    }

    private String getCenter(User user) {
        double lat = 0, lng = 0;
        for (Facility fasility : user.getFasilitys()) {
            LatLng latLng = getGMapsAdress(fasility);
            lat += latLng.getLat();
            lng += latLng.getLng();

        }
        center = "" + lat / user.getFasilitys().size() + "," + lng / user.getFasilitys().size();
        return center;
    }

    private void setCenter(String center) {
        this.center = center;
    }

    public String getId(Facility facility) {
        if (facility.getId() == null) {
            return String.valueOf((int) (Math.random() * 1000));
        }
        return facility.getId().toString();
    }

    public String getId(User user) {
        return user.getId().toString();
    }
    private int count = 10;

    public MapModel getTestsMarkers() {
        MapModel mm = new DefaultMapModel();
        int start = 0;
        Double lat = 46.95694118331636;
        Double lng = 32.01210021972656;

        while (start <= count) {
            /*
                at:46.93537809500658, Lng:32.05741882324219
                46.98107216122198, Lng:31.970558166503906
             */
            Double lat1, lng1;
            if (new Random().nextBoolean()) {
                lat1 = lat + Math.random();
                lng1 = lng - Math.random();
            } else {
                lat1 = lat - Math.random();
                lng1 = lng - Math.random();
            }
            if (lat1 > 46.93537809500658 && lng1 > 31.970558166503906 && lat1 < 46.98107216122198 && lng1 < 32.05741882324219) {
                mm.addOverlay(new Marker(new LatLng(lat1, lng1)));
                start++;
            }

        }
        return mm;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
