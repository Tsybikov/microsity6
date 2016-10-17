package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Log;
import info.microsityv6.microsityv6.entitys.TemporalRequests;
import info.microsityv6.microsityv6.entitys.User;
import info.microsityv6.microsityv6.enums.LoggerLevel;
import info.microsityv6.microsityv6.enums.RequestValidation;
import info.microsityv6.microsityv6.facades.TemporalRequestsFacade;
import info.microsityv6.microsityv6.facades.UserFacade;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@Named(value = "validationPageController")
@RequestScoped
public class ValidationPageController extends PageController {

    @EJB
    TemporalRequestsFacade trf;
    @EJB
    UserFacade uf;

    private boolean checked = true;
    private String message = "";

    public ValidationPageController() {
    }

    public boolean CheckIt() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        String str = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("req");
        for (TemporalRequests temporalRequests : trf.findAll()) {
            if (!temporalRequests.isCompleted()) {
                Calendar exp = Calendar.getInstance();
                exp.setTime(temporalRequests.getDateTimeExpiried());
                if (Calendar.getInstance().after(exp)) {
                    temporalRequests.setCompleted(true);
                    trf.edit(temporalRequests);
                    return false;

                } else {
                    if (temporalRequests.getRequestValidation().equals(RequestValidation.USER_AUTORISATION)) {
                        Iterator userIterator = uf.findAll().iterator();
                        while (userIterator.hasNext()) {
                            User selected = (User) userIterator.next();
                            if (selected.getId().equals(temporalRequests.getUserId()) && !selected.isActivated()) {
                                selected.setActivated(true);
                                uf.edit(selected);
                                message = "Ваш аккаунт активирован. Спасибо.";
                                temporalRequests.setCompleted(true);
                                trf.edit(temporalRequests);
                                logFacade.create(new Log(LoggerLevel.INFO, selected.getMainEmail() + " activated account from " + ipAddress));
                                return true;
                            } else {
                                message = "Ссылка более не активна. Извините.";
                                temporalRequests.setCompleted(true);
                                trf.edit(temporalRequests);
                                logFacade.create(new Log(LoggerLevel.INFO, selected.getMainEmail() + " try activated account from " + ipAddress));
                                return false;
                            }
                        }
                    }
                    if (temporalRequests.getRequestValidation().equals(RequestValidation.PASSWORD_RECOVERY)) {
                        Iterator userIterator = uf.findAll().iterator();
                        while (userIterator.hasNext()) {
                            User selected = (User) userIterator.next();
                            if (selected.getId().equals(temporalRequests.getUserId())) {
                                super.getUserController().setCurrent(selected);
                                super.getUserController().login();
                                try {
                                    FacesContext.getCurrentInstance().getExternalContext().redirect("passwordRecovery.xhtml?faces-redirect=true");
                                    temporalRequests.setCompleted(true);
                                    trf.edit(temporalRequests);
                                    logFacade.create(new Log(LoggerLevel.WARN,selected.getMainEmail()+ " change password from "+ipAddress));
                                } catch (IOException ex) {
                                    logFacade.create(new Log(LoggerLevel.ERROR, "Error then redirected from ValidationPageController", ex));
                                }

                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
