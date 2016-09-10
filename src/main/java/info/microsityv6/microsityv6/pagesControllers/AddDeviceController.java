/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Counter;
import info.microsityv6.microsityv6.entitys.ESP;
import info.microsityv6.microsityv6.entitys.Kit;
import info.microsityv6.microsityv6.entitys.Pin;
import info.microsityv6.microsityv6.enums.CounterType;
import info.microsityv6.microsityv6.enums.DeviceType;
import info.microsityv6.microsityv6.facades.KitFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Panker-RDP
 */
@Named(value = "addDeviceController")
@SessionScoped
public class AddDeviceController extends PageController implements Serializable {

    @EJB
    KitFacade kitFacade;

    private String addedKitHexId = "";
    private boolean showInfo;
    private Kit addedKit = new Kit();
    private List<Kit> kits;

    public AddDeviceController() {

    }

    public String getAddedKitHexId() {
        return addedKitHexId;
    }

    public void setAddedKitHexId(String addedKitHexId) {
        this.addedKitHexId = addedKitHexId;
    }

    public String addKit() {
        boolean added = false;
        if (kitFacade.findAll().isEmpty()) {
            makeDummy();
        } else {
            kits = kitFacade.findAll();
        }
        if (addedKitHexId.length() == 8) {
            for (Kit kit : kitFacade.findAll()) {
                if (kit.getKitHexId().equalsIgnoreCase(addedKitHexId) && !kit.isSold()) {
                    for (ESP esp : kit.getEsps()) {
                        for (Pin pin : esp.getPins()) {
                            if (pin.getType().equals(DeviceType.COUNTER)) {
                                Counter addedCounter = new Counter();
                                addedCounter.setTitle("Новый счетчик");
                                addedCounter.setCounterType(pin.getCounterType());
                                addedCounter.setEsp_id(esp.getEspId());
                                addedCounter.setPin(esp.getPins().indexOf(pin));
                                super.getCurrentFacility().getCounters().add(addedCounter);
                                super.saveChanges();
                                added = true;
                            }
                        }
                    }
                }
            }
        }
        if (added) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Набор успешно добавлен. Перейдите к настройкам устройств"));
            return "";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("ID не найден в базе, проверьте правильность ввода"));
        return "";
    }

    public void checkMatches() {
        for (Kit kit : kitFacade.findAll()) {
            if (kit.getKitHexId().equalsIgnoreCase(addedKitHexId) && !kit.isSold()) {
                showInfo = true;
                addedKit = kit;
                return;
            }
        }
        showInfo = false;
    }

    public boolean isShowInfo() {
        return showInfo;
    }

    public void setShowInfo(boolean showInfo) {
        this.showInfo = showInfo;
    }

    public Kit getAddedKit() {
        return addedKit;
    }

    public void setAddedKit(Kit addedKit) {
        this.addedKit = addedKit;
    }

    private void makeDummy() {
        kits = new ArrayList<>();
        Kit firstKit = new Kit();
        //1
        ESP esp1 = new ESP();
        esp1.setEspId("ESP_18:fe:34:a6:a0:e4");
        Pin pin1 = new Pin();
        pin1.setPin_num(2);
        pin1.setType(DeviceType.COUNTER);
        pin1.setCounterType(CounterType.WATT);
        esp1.getPins().add(pin1);
        firstKit.addEsp(esp1);
        //+
        ESP esp2 = new ESP();
        esp2.setEspId("ESP_18:fe:34:da:8c:32");
        Pin pin2 = new Pin();
        pin2.setPin_num(2);
        pin2.setType(DeviceType.COUNTER);
        pin2.setCounterType(CounterType.WATER);
        esp2.getPins().add(pin2);
        firstKit.addEsp(esp2);
        genKitId(firstKit);
        kitFacade.create(firstKit);
        //2
        Kit secondKit = new Kit();
        esp1 = new ESP();
        esp1.setEspId("ESP_18:fe:34:da:8a:77");
        pin1 = new Pin();
        pin1.setPin_num(2);
        pin1.setType(DeviceType.COUNTER);
        pin1.setCounterType(CounterType.WATT);
        esp1.getPins().add(pin1);
        secondKit.addEsp(esp1);
        genKitId(secondKit);
        kitFacade.create(secondKit);
    }

    private Kit genKitId(Kit editedKit) {
        editedKit.genKitHexId();
        boolean mach = true;
        if (kits.isEmpty()) {
            kits.add(editedKit);
            return editedKit;
        }
        while (mach) {
            for (Kit kit : kits) {
                if (kit.getKitHexId().equalsIgnoreCase(editedKit.getKitHexId())) {
                    editedKit.genKitHexId();
                    mach = true;
                    break;
                } else {
                    mach = false;
                }
            }
        }
        return editedKit;
    }
}
