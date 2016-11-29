package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Kit;
import info.microsityv6.microsityv6.entitys.Log;
import info.microsityv6.microsityv6.entitys.Smartsys;
import info.microsityv6.microsityv6.enums.LoggerLevel;
import info.microsityv6.microsityv6.facades.KitFacade;
import info.microsityv6.microsityv6.facades.LogFacade;
import info.microsityv6.microsityv6.facades.SmartsysFacade;
import info.microsityv6.microsityv6.support.GMailService;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;

@Named(value = "adminPageController")
@SessionScoped
public class AdminPageController implements Serializable {

    @EJB
    private LogFacade lf;
    @EJB
    private KitFacade kitFacade;
    @EJB
    SmartsysFacade smsService;
    private List<Log> logs;
    private List<Log> filteredLogs;
    private List<Kit> kits;
    private List<Smartsys> smss;
    private String mailTo;
    private String messageMail;
    private String smsTo;
    private String messageSms;

    @Inject
    private GMailService gms;

    public AdminPageController() {
    }

    public List<Log> getLogs() {
        logs = lf.findAll();
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    public List<LoggerLevel> getLevels() {
        List<LoggerLevel> levels = new ArrayList<>();
        levels.addAll(Arrays.asList(LoggerLevel.values()));
        return levels;
    }

    public String getDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        if (date != null) {
            return sdf.format(date);
        }
        return "нет данных";
    }

    public List<Log> getFilteredLogs() {
        return filteredLogs;
    }

    public void setFilteredLogs(List<Log> filteredLogs) {
        this.filteredLogs = filteredLogs;
    }

    public List<Kit> getKits() {
        return kitFacade.findAll();
    }

    public void setKits(List<Kit> kits) {
        this.kits = kits;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getMessageMail() {
        return messageMail;
    }

    public void setMessageMail(String messageMail) {
        this.messageMail = messageMail;
    }

    public String getSmsTo() {
        return smsTo;
    }

    public void setSmsTo(String smsTo) {
        this.smsTo = smsTo;
    }

    public String getMessageSms() {
        return messageSms;
    }

    public void setMessageSms(String messageSms) {
        this.messageSms = messageSms;
    }

    public void sendMail() {
        gms.sendEmail(mailTo, messageMail);
        lf.create(new Log(LoggerLevel.INFO, "Отправлено письмо пользователю " + mailTo));
    }

    public void sendSms() {
//          smsService.create(new Smartsys(smsTo, "Microsity", messageSms, null, false));
        Connection conn = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://94.249.146.189:3306/users", "smartsys", "613193");
            String add = "insert into smartsys(NUMBER,SIGN,MESSAGE) VALUES(\"" + smsTo + "\",\"Microsity\",\"" + messageSms + "\")";
            statement = conn.createStatement();
            statement.execute(add);
            lf.create(new Log(LoggerLevel.INFO, "Отправлено смс пользователю " + smsTo));
        } catch (ClassNotFoundException | SQLException ex) {
            lf.create(new Log(LoggerLevel.ERROR, ex.getLocalizedMessage(), ex));
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    lf.create(new Log(LoggerLevel.ERROR, ex.getLocalizedMessage(), ex));
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    lf.create(new Log(LoggerLevel.ERROR, ex.getLocalizedMessage(), ex));
                }
            }
        }

    }

    public List<Smartsys> getSmss() {
        try {
            smss = smsService.findAll();
        } catch (Exception ex) {
            lf.create(new Log(LoggerLevel.ERROR,ex.getLocalizedMessage(),ex));
        }
        return new ArrayList<>();
    }

    public void setSmss(List<Smartsys> smss) {
        this.smss = smss;
    }

    public String getStatusSms(String status) {
        switch (status) {
            case "NULL":
                return "Сообщение ещё не обработано";
            case "ACCEPTD":
                return "Сообщение принято в обработку";
            case "ENROUTE":
                return "Сообщение отправлено в мобильную сеть";
            case "DELIVRD":
                return "Сообщение доставлено получателю";
            case "EXPIRED":
                return "Истек срок сообщения";
            case "DELETED":
                return "Удалено оператором";
            case "UNDELIV":
                return "Не доставлено";
            case "REJECTD":
                return "Сообщение отклонено";
            default:
                return "Неизвестный статус";
        }
    }

}
