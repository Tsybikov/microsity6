/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.entitysControllers;

import info.microsityv6.microsityv6.entitys.Log;
import info.microsityv6.microsityv6.entitys.User;
import info.microsityv6.microsityv6.enums.LoggerLevel;
import info.microsityv6.microsityv6.enums.Role;
import info.microsityv6.microsityv6.facades.LogFacade;
import info.microsityv6.microsityv6.facades.UserFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Panker-RDP
 */
@Named(value = "userController")
@SessionScoped
public class UserController implements Serializable {

    @EJB
    private UserFacade uf;
    @EJB
    private LogFacade lf;
    private User current;
    private User created;
    private String repeatedPassword;
    private boolean entered = false;
    private boolean admin = false;
    private boolean remMe = false;

    @PostConstruct
    public void init() {
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

    public String login() {
        List<User> users = uf.findAll();
        if (users.isEmpty()) {
            User newUser = new User();
            newUser.setPasswordHash("156456851");
            newUser.setMainEmail("panker@mksat.net");
            newUser.addPhone("+380664119956");
            newUser.setRole(Role.ADMIN);
            uf.create(newUser);
            users.add(newUser);
            newUser = new User();
            newUser.setPasswordHash("demodemo");
            newUser.setMainEmail("demo@microsity.info");
            newUser.addPhone("+380512000000");
            newUser.setRole(Role.USER);
            uf.create(newUser);
            users.add(newUser);
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
                    FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("@all");
                    return "home.xhtml?faces-redirect=true";
                }
            }
        }
        return "loginerror.xhtml?faces-redirect=true";
    }

    public void createUser() {
        if (created.getMainEmail().contains("@")) {
            List<User> users = uf.findAll();
            boolean existing = false;
            for (User user : users) {
                if (user.getMainEmail().equals(created.getMainEmail())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Такой почтовый ящик уже зарегистрирован"));
                    existing = true;
                }
            }
            if (!existing) {
                if (created.getPasswordHash().equals(DigestUtils.md5Hex(repeatedPassword))) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Пароли не совпадают"));
                } else {
                    created.setRole(Role.USER);
                    created.addMessage("Добро пожаловать! Вы успешно зарегистрированны в системе MicroSity");
                    uf.create(created);
                    current=created;
                    created = new User();
                    //new Mail().confirmMail(enterLogMail, enterPass);
                    login();
                }
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Не верный формат почтового ящика"));
        }
        
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
    
    public void getDemo(){
        current=new User();
        current.setMainEmail("demo@microsity.info");
        current.setPasswordHash("demodemo");
        login();
    }
    
}
