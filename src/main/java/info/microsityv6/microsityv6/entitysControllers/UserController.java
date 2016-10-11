package info.microsityv6.microsityv6.entitysControllers;

import info.microsityv6.microsityv6.entitys.Log;
import info.microsityv6.microsityv6.entitys.TemporalRequests;
import info.microsityv6.microsityv6.entitys.User;
import info.microsityv6.microsityv6.enums.LoggerLevel;
import info.microsityv6.microsityv6.enums.RequestValidation;
import info.microsityv6.microsityv6.enums.Role;
import info.microsityv6.microsityv6.facades.LogFacade;
import info.microsityv6.microsityv6.facades.TemporalRequestsFacade;
import info.microsityv6.microsityv6.facades.UserFacade;
import info.microsityv6.microsityv6.support.GMailService;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Named(value = "userController")
@SessionScoped
public class UserController implements Serializable {

    @EJB
    private UserFacade uf;
    @EJB
    private LogFacade lf;
    @EJB
    private TemporalRequestsFacade trf;
    @Inject
    private GMailService gMailService;

    private User current = new User();
    private User created = new User();
    private String repeatedPassword = "";
    private String newPassword = "";
    private boolean entered = false;
    private boolean admin = false;
    private boolean remMe = false;
    private String userMail = "";

    @PostConstruct
    public void init() {
        setUpDefaultUsers();
        Cookie[] cs = ((HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest())).getCookies();
        if (cs != null && cs.length > 0) {
            for (Cookie c : cs) {
                if (c.getName().equals("MicroSityLastSession")) {
                    List<User> users = uf.findAll();
                    for (User user : users) {
                        if (user.isRememberMe() || user.getLastSession().equals(c.getValue())) {
                            current = user;
                            entered = true;
                            user.setLastVisit(Calendar.getInstance());
                            lf.create(new Log(LoggerLevel.INFO, current.getMainEmail() + " entered"));

                            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                            session.setAttribute("current", current);

                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Добро пожаловать, " + current.getMainEmail()));

                            if (remMe || user.isRememberMe()) {
                                String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
                                Cookie lastSession = new Cookie("MicroSityLastSession", sessionId);
                                user.setLastSession(sessionId);
                                lastSession.setMaxAge(155520000);
                                ((HttpServletResponse) (FacesContext.getCurrentInstance().getExternalContext().getResponse())).addCookie(lastSession);
                            }

                            uf.edit(user);
                        }
                    }
                }
            }
        }
    }

    public UserController() {
    }

    public String recoverPasswordRequest() {
        for (User user : uf.findAll()) {
            if (user.getMainEmail().equals(userMail)) {
                TemporalRequests temporalRequest = new TemporalRequests();
                boolean again = true;
                while (again) {
                    again = false;
                    temporalRequest.setUserId(current.getId());
                    temporalRequest.setCompleted(false);
                    temporalRequest.setRequestValidation(RequestValidation.PASSWORD_RECOVERY);
                    temporalRequest.generateRequestString();
                    for (TemporalRequests tr : trf.findAll()) {
                        if (tr.getRequestString().equals(temporalRequest.getRequestString())) {
                            again = true;
                        }
                    }
                }
                trf.create(temporalRequest);
                gMailService.sendForrgotPasswordLink(user.getMainEmail(), temporalRequest.getRequestString());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Пароль отправлен на указанный почтовый ящик"));
                return "";
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Указанный почтовый ящик не зарегистрирован"));
        return "";
    }

    public String updatePassword() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");

        if (newPassword.equals(repeatedPassword)) {
            current.setPasswordHash(newPassword);
            uf.edit(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Пароль был успешно обновлен!"));
            lf.create(new Log(LoggerLevel.INFO, current.getMainEmail() + " change password from " + ipAddress));
            current = new User();
            return "";

        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Что-то не так!"));
        return "";
    }

    public String login() {
        List<User> users = uf.findAll();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        for (User user : users) {
            if (user.getMainEmail().equalsIgnoreCase(current.getMainEmail())) {
                if (user.getPasswordHash().equals(current.getPasswordHash())) {
                    current = user;
                    entered = true;
                    if (current.getRole().equals(Role.ADMIN)) {
                        admin = true;
                    }
                    user.setLastVisit(Calendar.getInstance());
                    lf.create(new Log(LoggerLevel.INFO, current.getMainEmail() + " entered from " + ipAddress));

                    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                    session.setAttribute("current", current);

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Добро пожаловать, " + current.getMainEmail()));

                    if (remMe || user.isRememberMe()) {
                        String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
                        Cookie lastSession = new Cookie("MicroSityLastSession", sessionId);
                        user.setLastSession(sessionId);
                        lastSession.setMaxAge(155520000);
                        ((HttpServletResponse) (FacesContext.getCurrentInstance().getExternalContext().getResponse())).addCookie(lastSession);
                    }

                    uf.edit(user);
                    FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("@all");
                    return "home.xhtml?faces-redirect=true";
                }
            }
        }
        lf.create(new Log(LoggerLevel.WARN, "Wrong authorization probe of"+current.getMainEmail()+" from " + ipAddress));
        return "loginerror.xhtml?faces-redirect=true";
    }

    public String createUser() {
        List<User> users = uf.findAll();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        setUpDefaultUsers();
        if (created.getMainEmail().contains("@")) {
            users = uf.findAll();
            boolean existing = false;
            for (User user : users) {
                if (user.getMainEmail().equals(created.getMainEmail())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Такой почтовый ящик уже зарегистрирован"));
                    existing = true;
                }
            }
            if (!existing) {
                if (!newPassword.equals(repeatedPassword)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Пароли не совпадают"));
                } else {
                    created.setRole(Role.USER);
                    created.setPasswordHash(newPassword);
                    created.addMessage("Добро пожаловать! Вы успешно зарегистрированны в системе MicroSity");
                    uf.create(created);
                    lf.create(new Log(LoggerLevel.INFO, created.getMainEmail() + " has succesfull created from" + ipAddress));
                    current = created;
                    created = new User();
                    boolean again = true;
                    TemporalRequests temporalRequest = new TemporalRequests();
                    while (again) {
                        again = false;
                        temporalRequest.setUserId(current.getId());
                        temporalRequest.setCompleted(false);
                        temporalRequest.setRequestValidation(RequestValidation.USER_AUTORISATION);
                        temporalRequest.generateRequestString();
                        if (!trf.findAll().isEmpty()) {
                            for (TemporalRequests tr : trf.findAll()) {
                                if (tr.getRequestString().equals(temporalRequest.getRequestString())) {
                                    again = true;
                                }
                            }
                        }
                    }
                    if (gMailService.sendAuthNotification(current.getMainEmail(), temporalRequest.getRequestString())) {
                        trf.create(temporalRequest);
                        return login();
                    }
                }
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Не верный формат почтового ящика"));
        }
        return "";
    }

    public String logout() {
        lf.create(new Log(LoggerLevel.INFO, current.getMainEmail() + " logout"));
        String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        Cookie lastSession = new Cookie("MicroSityLastSession", sessionId);
        lastSession.setMaxAge(0);
        ((HttpServletResponse) (FacesContext.getCurrentInstance().getExternalContext().getResponse())).addCookie(lastSession);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        current = new User();
        entered = false;
        admin = false;
        return ("index");
    }

    public String demo() {
        current = new User();
        current.setMainEmail("demo@microsity.info");
        current.setPasswordHash("demodemo");
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        lf.create(new Log(LoggerLevel.INFO, "New demo user loginned from " + ipAddress));
        return login();
    }

    private void setUpDefaultUsers() {
        List<User> users = uf.findAll();
        boolean finded = false;
        if (!users.isEmpty()) {
            for (User user : users) {
                if (user.getMainEmail().equalsIgnoreCase("panker@mksat.net") || user.getMainEmail().equalsIgnoreCase("demo@microsity.info")) {
                    finded = true;
                }
            }
        }
        if (!finded) {
            User newUser = new User();
            newUser.setPasswordHash("156456851");
            newUser.setMainEmail("panker@mksat.net");
            newUser.addPhone("+380664119956");
            newUser.setRole(Role.ADMIN);
            newUser.setActivated(true);
            newUser.addMessage("Опаньки!");
            uf.create(newUser);
            users.add(newUser);
            newUser = new User();
            newUser.setPasswordHash("demodemo");
            newUser.setMainEmail("demo@microsity.info");
            newUser.addPhone("+380512000000");
            newUser.setRole(Role.USER);
            newUser.setActivated(true);
            uf.create(newUser);
            users.add(newUser);
        }
    }

    public void saveCurrent() {
        uf.edit(current);        
    }

    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }

    public User getCreated() {
        return created;
    }

    public void setCreated(User created) {
        this.created = created;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public boolean isEntered() {
        return entered;
    }

    public void setEntered(boolean entered) {
        this.entered = entered;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isRemMe() {
        return remMe;
    }

    public void setRemMe(boolean remMe) {
        this.remMe = remMe;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
