package info.microsityv6.microsityv6.support;

import info.microsityv6.microsityv6.entitys.Log;
import info.microsityv6.microsityv6.enums.LoggerLevel;
import info.microsityv6.microsityv6.facades.LogFacade;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

@Named(value = "gMailService")
@ApplicationScoped
public class GMailService {
    @EJB
    private LogFacade logFacade;
    
    public GMailService() {
    }

    public boolean sendEmail(String to, String textBody) {
        try {
            HtmlEmail email = new HtmlEmail();
            email.setCharset("utf-8");
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);
            email.setAuthenticator(new DefaultAuthenticator("mail.microsity@gmail.com", "156456851"));
            email.setTLS(true);
            email.setFrom("mail.microsity.info@gmail.com");
            email.setSubject("Automatic message from MicroSity.info");
            email.setHtmlMsg(textBody);
            email.addTo(to);
            email.send();
            logFacade.create(new Log(LoggerLevel.INFO,"Sended mail " +  to));
        } catch (EmailException ex) {
            System.out.println(ex);
            logFacade.create(new Log(LoggerLevel.ERROR, "Error whid mail sending", ex));
            return false;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Почтовое сообщение отправлено!"));
        return true;
    }

    public boolean sendAuthNotification(String mainEmail, String requestString) {
        String html = style + "<div class=\"clearfix borderbox\" id=\"page\"><!-- group -->\n"
                + "            <div class=\"grpelem\" id=\"u5771\"><!-- simple frame --></div>\n"
                + "            <img class=\"grpelem\" id=\"u5764\" alt=\"\" src=\"http://microsity.info/resources/images/logo.png\" data-image-width=\"107\"/><!-- rasterized frame -->\n"
                + "            <div class=\"clearfix grpelem\" id=\"u5774-13\"><!-- content -->\n"
                + "                <p>Для получения полного функционала портала необходимо подтвердить Ваш почтовый ящик. Перейдите по <a class=\"nonblock\" href=\"http://microsity.info/validationPage.xhtml"+requestString+"\">этой ссылке</a> что бы закончить регистрацию.</p>\n"
                + "                <p>&nbsp;</p>\n"
                + "                <p>Если Вы не регистрировались в портале <a class=\"nonblock\" href=\"http://microsity.info/\">microsity.info</a>, пожалуйста, проигнорируйте это письмо.</p>\n"
                + "            </div>\n"
                + "            <div class=\"clearfix grpelem\" id=\"u5777-6\"><!-- content -->\n"
                + "                <p>С уважением администрация портала <a class=\"nonblock\" href=\"http://microsity.info/\">MicroSity.info</a></p>\n"
                + "            </div>\n"
                + "            <div class=\"verticalspacer\"></div>\n"
                + "        </div>";
        return sendEmail(mainEmail, html);
    }
    
    public boolean sendForrgotPasswordLink(String mainEmail, String requestString) {
        String html = style + "<div class=\"clearfix borderbox\" id=\"page\"><!-- group -->\n"
                + "            <div class=\"grpelem\" id=\"u5771\"><!-- simple frame --></div>\n"
                + "            <img class=\"grpelem\" id=\"u5764\" alt=\"\" src=\"http://microsity.info/resources/images/logo.png\" data-image-width=\"107\"/><!-- rasterized frame -->\n"
                + "            <div class=\"clearfix grpelem\" id=\"u5774-13\"><!-- content -->\n"
                + "                <p>Вы запросили форму восстановления пароля. Перейдите по <a class=\"nonblock\" href=\"http://microsity.info/validationPage.xhtml"+requestString+"\">этой ссылке</a> что бы ввести новый пароль.</p>\n"
                + "                <p>&nbsp;</p>\n"
                + "                <p>Если Вы не регистрировались в портале <a class=\"nonblock\" href=\"http://microsity.info/\">microsity.info</a>, пожалуйста, проигнорируйте это письмо.</p>\n"
                + "            </div>\n"
                + "            <div class=\"clearfix grpelem\" id=\"u5777-6\"><!-- content -->\n"
                + "                <p>С уважением администрация портала <a class=\"nonblock\" href=\"http://microsity.info/\">MicroSity.info</a></p>\n"
                + "            </div>\n"
                + "            <div class=\"verticalspacer\"></div>\n"
                + "        </div>";
        return sendEmail(mainEmail, html);
    }
    
    public boolean sendInfoMail(String mainEmail, String requestString) {
        String html = style + "<div class=\"clearfix borderbox\" id=\"page\"><!-- group -->\n"
                + "            <div class=\"grpelem\" id=\"u5771\"><!-- simple frame --></div>\n"
                + "            <img class=\"grpelem\" id=\"u5764\" alt=\"\" src=\"http://microsity.info/resources/images/logo.png\" data-image-width=\"107\"/><!-- rasterized frame -->\n"
                + "            <div class=\"clearfix grpelem\" id=\"u5774-13\"><!-- content -->\n"
                + "                <p>"+requestString+"\"></p>\n"
                + "                <p>&nbsp;</p>\n"
                + "                \n"
                + "            </div>\n"
                + "            <div class=\"clearfix grpelem\" id=\"u5777-6\"><!-- content -->\n"
                + "                <p>С уважением администрация портала <a class=\"nonblock\" href=\"http://microsity.info/\">MicroSity.info</a></p>\n"
                + "            </div>\n"
                + "            <div class=\"verticalspacer\"></div>\n"
                + "        </div>";
        return sendEmail(mainEmail, html);
    }
    
    private String style = "<style type=\"text/css\">\n"
            + "            #page\n"
            + "            {\n"
            + "                z-index: 1;\n"
            + "                min-height: 469px;\n"
            + "                background-image: none;\n"
            + "                border-width: 0px;\n"
            + "                border-color: #000000;\n"
            + "                background-color: #6A7767;\n"
            + "                padding-bottom: 31px;\n"
            + "                width: 100%;\n"
            + "                max-width: 800px;\n"
            + "                margin-left: auto;\n"
            + "                margin-right: auto;\n"
            + "            }\n"
            + "\n"
            + "            #u5771\n"
            + "            {\n"
            + "                z-index: 4;\n"
            + "                height: 2px;\n"
            + "                background-color: #0000FF;\n"
            + "                position: relative;\n"
            + "                margin-right: -10000px;\n"
            + "                margin-top: 77px;\n"
            + "                width: 98.38%;\n"
            + "                margin-left: 0.82%;\n"
            + "                left: 0px;\n"
            + "            }\n"
            + "\n"
            + "            #u5764\n"
            + "            {\n"
            + "                z-index: 2;\n"
            + "                display: block;\n"
            + "                vertical-align: top;\n"
            + "                position: relative;\n"
            + "                margin-right: -10000px;\n"
            + "                margin-top: 13px;\n"
            + "                left: 10px;\n"
            + "                width: 26%;\n"
            + "                \n"
            + "            }\n"
            + "\n"
            + "            #u5774-13\n"
            + "            {\n"
            + "                z-index: 5;\n"
            + "                min-height: 28px;\n"
            + "                background-color: transparent;\n"
            + "                position: relative;\n"
            + "                margin-right: -10000px;\n"
            + "                margin-top: 91px;\n"
            + "                width: 87.5%;\n"
            + "                margin-left: 6.25%;\n"
            + "                left: 0px;\n"
            + "            }\n"
            + "\n"
            + "            #u5777-6\n"
            + "            {\n"
            + "                z-index: 18;\n"
            + "                min-height: 74px;\n"
            + "                background-color: transparent;\n"
            + "                position: relative;\n"
            + "                margin-right: -10000px;\n"
            + "                margin-top: 395px;\n"
            + "                width: 37.5%;\n"
            + "                margin-left: 62.5%;\n"
            + "                left: -50px;\n"
            + "            }\n"
            + "\n"
            + "            .html\n"
            + "            {\n"
            + "                background-color: #22B573;\n"
            + "            }\n"
            + "\n"
            + "            body\n"
            + "            {\n"
            + "                position: relative;\n"
            + "                min-width: 800px;\n"
            + "            }\n"
            + "\n"
            + "            html\n"
            + "            {\n"
            + "                min-height: 100%;\n"
            + "                min-width: 100%;\n"
            + "                -ms-text-size-adjust: none;\n"
            + "            }\n"
            + "\n"
            + "            body,div,dl,dt,dd,ul,ol,li,nav,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,button,textarea,p,blockquote,th,td,a\n"
            + "            {\n"
            + "                margin: 0px;\n"
            + "                padding: 0px;\n"
            + "                border-width: 0px;\n"
            + "                border-style: solid;\n"
            + "                border-color: transparent;\n"
            + "                -webkit-transform-origin: left top;\n"
            + "                -ms-transform-origin: left top;\n"
            + "                -o-transform-origin: left top;\n"
            + "                transform-origin: left top;\n"
            + "                background-repeat: no-repeat;\n"
            + "            }\n"
            + "\n"
            + "            .transition\n"
            + "            {\n"
            + "                -webkit-transition-property: background-image,background-position,background-color,border-color,border-radius,color,font-size,font-style,font-weight,letter-spacing,line-height,text-align,box-shadow,text-shadow,opacity;\n"
            + "                transition-property: background-image,background-position,background-color,border-color,border-radius,color,font-size,font-style,font-weight,letter-spacing,line-height,text-align,box-shadow,text-shadow,opacity;\n"
            + "            }\n"
            + "\n"
            + "            .transition *\n"
            + "            {\n"
            + "                -webkit-transition: inherit;\n"
            + "                transition: inherit;\n"
            + "            }\n"
            + "\n"
            + "            table\n"
            + "            {\n"
            + "                border-collapse: collapse;\n"
            + "                border-spacing: 0px;\n"
            + "            }\n"
            + "\n"
            + "            fieldset,img\n"
            + "            {\n"
            + "                border: 0px;\n"
            + "                border-style: solid;\n"
            + "                -webkit-transform-origin: left top;\n"
            + "                -ms-transform-origin: left top;\n"
            + "                -o-transform-origin: left top;\n"
            + "                transform-origin: left top;\n"
            + "            }\n"
            + "\n"
            + "            address,caption,cite,code,dfn,em,strong,th,var,optgroup\n"
            + "            {\n"
            + "                font-style: inherit;\n"
            + "                font-weight: inherit;\n"
            + "            }\n"
            + "\n"
            + "            del,ins\n"
            + "            {\n"
            + "                text-decoration: none;\n"
            + "            }\n"
            + "\n"
            + "            li\n"
            + "            {\n"
            + "                list-style: none;\n"
            + "            }\n"
            + "\n"
            + "            caption,th\n"
            + "            {\n"
            + "                text-align: left;\n"
            + "            }\n"
            + "\n"
            + "            h1,h2,h3,h4,h5,h6\n"
            + "            {\n"
            + "                font-size: 100%;\n"
            + "                font-weight: inherit;\n"
            + "            }\n"
            + "\n"
            + "            input,button,textarea,select,optgroup,option\n"
            + "            {\n"
            + "                font-family: inherit;\n"
            + "                font-size: inherit;\n"
            + "                font-style: inherit;\n"
            + "                font-weight: inherit;\n"
            + "            }\n"
            + "\n"
            + "            body\n"
            + "            {\n"
            + "                font-family: Arial, Helvetica Neue, Helvetica, sans-serif;\n"
            + "                text-align: left;\n"
            + "                font-size: 14px;\n"
            + "                line-height: 17px;\n"
            + "                word-wrap: break-word;\n"
            + "                text-rendering: optimizeLegibility;/* kerning, primarily */\n"
            + "                -moz-font-feature-settings: 'liga';\n"
            + "                -ms-font-feature-settings: 'liga';\n"
            + "                -webkit-font-feature-settings: 'liga';\n"
            + "                font-feature-settings: 'liga';\n"
            + "            }\n"
            + "\n"
            + "            a:link\n"
            + "            {\n"
            + "                color: #0000FF;\n"
            + "                text-decoration: underline;\n"
            + "            }\n"
            + "\n"
            + "            a:visited\n"
            + "            {\n"
            + "                color: #800080;\n"
            + "                text-decoration: underline;\n"
            + "            }\n"
            + "\n"
            + "            a:hover\n"
            + "            {\n"
            + "                color: #0000FF;\n"
            + "                text-decoration: underline;\n"
            + "            }\n"
            + "\n"
            + "            a:active\n"
            + "            {\n"
            + "                color: #EE0000;\n"
            + "                text-decoration: underline;\n"
            + "            }\n"
            + "\n"
            + "            a.nontext /* used to override default properties of 'a' tag */\n"
            + "            {\n"
            + "                color: black;\n"
            + "                text-decoration: none;\n"
            + "                font-style: normal;\n"
            + "                font-weight: normal;\n"
            + "            }\n"
            + "\n"
            + "            .normal_text\n"
            + "            {\n"
            + "                color: #000000;\n"
            + "                direction: ltr;\n"
            + "                font-family: Arial, Helvetica Neue, Helvetica, sans-serif;\n"
            + "                font-size: 14px;\n"
            + "                font-style: normal;\n"
            + "                font-weight: normal;\n"
            + "                letter-spacing: 0px;\n"
            + "                line-height: 17px;\n"
            + "                text-align: left;\n"
            + "                text-decoration: none;\n"
            + "                text-indent: 0px;\n"
            + "                text-transform: none;\n"
            + "                vertical-align: 0px;\n"
            + "                padding: 0px;\n"
            + "            }\n"
            + "\n"
            + "            .list0 li:before\n"
            + "            {\n"
            + "                position: absolute;\n"
            + "                right: 100%;\n"
            + "                letter-spacing: 0px;\n"
            + "                text-decoration: none;\n"
            + "                font-weight: normal;\n"
            + "                font-style: normal;\n"
            + "            }\n"
            + "\n"
            + "            .rtl-list li:before\n"
            + "            {\n"
            + "                right: auto;\n"
            + "                left: 100%;\n"
            + "            }\n"
            + "\n"
            + "            .nls-None > li:before,.nls-None .list3 > li:before,.nls-None .list6 > li:before\n"
            + "            {\n"
            + "                margin-right: 6px;\n"
            + "                content: '•';\n"
            + "            }\n"
            + "\n"
            + "            .nls-None .list1 > li:before,.nls-None .list4 > li:before,.nls-None .list7 > li:before\n"
            + "            {\n"
            + "                margin-right: 6px;\n"
            + "                content: '○';\n"
            + "            }\n"
            + "\n"
            + "            .nls-None,.nls-None .list1,.nls-None .list2,.nls-None .list3,.nls-None .list4,.nls-None .list5,.nls-None .list6,.nls-None .list7,.nls-None .list8\n"
            + "            {\n"
            + "                padding-left: 34px;\n"
            + "            }\n"
            + "\n"
            + "            .nls-None.rtl-list,.nls-None .list1.rtl-list,.nls-None .list2.rtl-list,.nls-None .list3.rtl-list,.nls-None .list4.rtl-list,.nls-None .list5.rtl-list,.nls-None .list6.rtl-list,.nls-None .list7.rtl-list,.nls-None .list8.rtl-list\n"
            + "            {\n"
            + "                padding-left: 0px;\n"
            + "                padding-right: 34px;\n"
            + "            }\n"
            + "\n"
            + "            .nls-None .list2 > li:before,.nls-None .list5 > li:before,.nls-None .list8 > li:before\n"
            + "            {\n"
            + "                margin-right: 6px;\n"
            + "                content: '-';\n"
            + "            }\n"
            + "\n"
            + "            .nls-None.rtl-list > li:before,.nls-None .list1.rtl-list > li:before,.nls-None .list2.rtl-list > li:before,.nls-None .list3.rtl-list > li:before,.nls-None .list4.rtl-list > li:before,.nls-None .list5.rtl-list > li:before,.nls-None .list6.rtl-list > li:before,.nls-None .list7.rtl-list > li:before,.nls-None .list8.rtl-list > li:before\n"
            + "            {\n"
            + "                margin-right: 0px;\n"
            + "                margin-left: 6px;\n"
            + "            }\n"
            + "\n"
            + "            .TabbedPanelsTab\n"
            + "            {\n"
            + "                white-space: nowrap;\n"
            + "            }\n"
            + "\n"
            + "            .MenuBar .MenuBarView,.MenuBar .SubMenuView /* Resets for ul and li in menus */\n"
            + "            {\n"
            + "                display: block;\n"
            + "                list-style: none;\n"
            + "            }\n"
            + "\n"
            + "            .MenuBar .SubMenu\n"
            + "            {\n"
            + "                display: none;\n"
            + "                position: absolute;\n"
            + "            }\n"
            + "\n"
            + "            .NoWrap\n"
            + "            {\n"
            + "                white-space: nowrap;\n"
            + "                word-wrap: normal;\n"
            + "            }\n"
            + "\n"
            + "            .rootelem /* the root of the artwork tree */\n"
            + "            {\n"
            + "                margin-left: auto;\n"
            + "                margin-right: auto;\n"
            + "            }\n"
            + "\n"
            + "            .colelem /* a child element of a column */\n"
            + "            {\n"
            + "                display: inline;\n"
            + "                float: left;\n"
            + "                clear: both;\n"
            + "            }\n"
            + "\n"
            + "            .clearfix:after /* force a container to fit around floated items */\n"
            + "            {\n"
            + "                content: \"\\0020\";\n"
            + "                visibility: visible;\n"
            + "                display: block;\n"
            + "                height: 0px;\n"
            + "                clear: both;\n"
            + "            }\n"
            + "\n"
            + "            *:first-child+html .clearfix /* IE7 */\n"
            + "            {\n"
            + "                zoom: 1;\n"
            + "            }\n"
            + "\n"
            + "            .clip_frame /* used to clip the contents as in the case of an image frame */\n"
            + "            {\n"
            + "                overflow: hidden;\n"
            + "            }\n"
            + "\n"
            + "            .popup_anchor /* anchors an abspos popup */\n"
            + "            {\n"
            + "                position: relative;\n"
            + "                width: 0px;\n"
            + "                height: 0px;\n"
            + "            }\n"
            + "\n"
            + "            .popup_element\n"
            + "            {\n"
            + "                z-index: 100000;\n"
            + "            }\n"
            + "\n"
            + "            .svg\n"
            + "            {\n"
            + "                display: block;\n"
            + "                vertical-align: top;\n"
            + "            }\n"
            + "\n"
            + "            span.wrap /* used to force wrap after floated array when nested inside a paragraph */\n"
            + "            {\n"
            + "                content: '';\n"
            + "                clear: left;\n"
            + "                display: block;\n"
            + "            }\n"
            + "\n"
            + "            span.actAsInlineDiv /* used to simulate a DIV with inline display when already nested inside a paragraph */\n"
            + "            {\n"
            + "                display: inline-block;\n"
            + "            }\n"
            + "\n"
            + "            .position_content,.excludeFromNormalFlow /* used when child content is larger than parent */\n"
            + "            {\n"
            + "                float: left;\n"
            + "            }\n"
            + "\n"
            + "            .preload_images /* used to preload images used in non-default states */\n"
            + "            {\n"
            + "                position: absolute;\n"
            + "                overflow: hidden;\n"
            + "                left: -9999px;\n"
            + "                top: -9999px;\n"
            + "                height: 1px;\n"
            + "                width: 1px;\n"
            + "            }\n"
            + "\n"
            + "            .preload /* used to specifiy the dimension of preload item */\n"
            + "            {\n"
            + "                height: 1px;\n"
            + "                width: 1px;\n"
            + "            }\n"
            + "\n"
            + "            .animateStates\n"
            + "            {\n"
            + "                -webkit-transition: 0.3s ease-in-out;\n"
            + "                -moz-transition: 0.3s ease-in-out;\n"
            + "                -o-transition: 0.3s ease-in-out;\n"
            + "                transition: 0.3s ease-in-out;\n"
            + "            }\n"
            + "\n"
            + "            [data-whatinput=\"mouse\"] *:focus,[data-whatinput=\"touch\"] *:focus,input:focus,textarea:focus\n"
            + "            {\n"
            + "                outline: none;\n"
            + "            }\n"
            + "\n"
            + "            textarea\n"
            + "            {\n"
            + "                resize: none;\n"
            + "                overflow: auto;\n"
            + "            }\n"
            + "\n"
            + "            .fld-prompt /* form placeholders cursor behavior */\n"
            + "            {\n"
            + "                pointer-events: none;\n"
            + "            }\n"
            + "\n"
            + "            .wrapped-input /* form inputs & placeholders let div styling show thru */\n"
            + "            {\n"
            + "                position: absolute;\n"
            + "                top: 0px;\n"
            + "                left: 0px;\n"
            + "                background: transparent;\n"
            + "                border: none;\n"
            + "            }\n"
            + "\n"
            + "            .submit-btn /* form submit buttons on top of sibling elements */\n"
            + "            {\n"
            + "                z-index: 50000;\n"
            + "                cursor: pointer;\n"
            + "            }\n"
            + "\n"
            + "            .anchor_item /* used to specify anchor properties */\n"
            + "            {\n"
            + "                width: 22px;\n"
            + "                height: 18px;\n"
            + "            }\n"
            + "\n"
            + "            .MenuBar .SubMenuVisible,.MenuBarVertical .SubMenuVisible,.MenuBar .SubMenu .SubMenuVisible,.popup_element.Active,span.actAsPara,.actAsDiv,a.nonblock.nontext,img.block\n"
            + "            {\n"
            + "                display: block;\n"
            + "            }\n"
            + "\n"
            + "            .ose_ei\n"
            + "            {\n"
            + "                visibility: visible;\n"
            + "                z-index: 0;\n"
            + "            }\n"
            + "\n"
            + "            .widget_invisible,.js .invi,.js .mse_pre_init,.js .an_invi /* used to hide the widget before loaded */\n"
            + "            {\n"
            + "                visibility: visible;\n"
            + "            }\n"
            + "\n"
            + "            .no_vert_scroll\n"
            + "            {\n"
            + "                overflow-y: hidden;\n"
            + "            }\n"
            + "\n"
            + "            .always_vert_scroll\n"
            + "            {\n"
            + "                overflow-y: scroll;\n"
            + "            }\n"
            + "\n"
            + "            .always_horz_scroll\n"
            + "            {\n"
            + "                overflow-x: scroll;\n"
            + "            }\n"
            + "\n"
            + "            .fullscreen\n"
            + "            {\n"
            + "                overflow: hidden;\n"
            + "                left: 0px;\n"
            + "                top: 0px;\n"
            + "                position: fixed;\n"
            + "                height: 100%;\n"
            + "                width: 100%;\n"
            + "                -moz-box-sizing: border-box;\n"
            + "                -webkit-box-sizing: border-box;\n"
            + "                -ms-box-sizing: border-box;\n"
            + "                box-sizing: border-box;\n"
            + "            }\n"
            + "\n"
            + "            .fullwidth\n"
            + "            {\n"
            + "                position: absolute;\n"
            + "            }\n"
            + "\n"
            + "            .borderbox\n"
            + "            {\n"
            + "                -moz-box-sizing: border-box;\n"
            + "                -webkit-box-sizing: border-box;\n"
            + "                -ms-box-sizing: border-box;\n"
            + "                box-sizing: border-box;\n"
            + "            }\n"
            + "\n"
            + "            .scroll_wrapper\n"
            + "            {\n"
            + "                position: absolute;\n"
            + "                overflow: auto;\n"
            + "                left: 0px;\n"
            + "                right: 0px;\n"
            + "                top: 0px;\n"
            + "                bottom: 0px;\n"
            + "                padding-top: 0px;\n"
            + "                padding-bottom: 0px;\n"
            + "                margin-top: 0px;\n"
            + "                margin-bottom: 0px;\n"
            + "            }\n"
            + "\n"
            + "            .browser_width > *\n"
            + "            {\n"
            + "                position: absolute;\n"
            + "                left: 0px;\n"
            + "                right: 0px;\n"
            + "            }\n"
            + "\n"
            + "            .list0 li,.MenuBar .MenuItemContainer,.SlideShowContentPanel .fullscreen img\n"
            + "            {\n"
            + "                position: relative;\n"
            + "            }\n"
            + "\n"
            + "            .grpelem,.accordion_wrapper /* a child element of a group */\n"
            + "            {\n"
            + "                display: inline;\n"
            + "                float: left;\n"
            + "            }\n"
            + "\n"
            + "            .fld-checkbox input[type=checkbox],.fld-radiobutton input[type=radio] /* Hide native checkbox */\n"
            + "            {\n"
            + "                position: absolute;\n"
            + "                overflow: hidden;\n"
            + "                clip: rect(0px, 0px, 0px, 0px);\n"
            + "                height: 1px;\n"
            + "                width: 1px;\n"
            + "                margin: -1px;\n"
            + "                padding: 0px;\n"
            + "                border: 0px;\n"
            + "            }\n"
            + "\n"
            + "            .fld-checkbox input[type=checkbox] + label,.fld-radiobutton input[type=radio] + label\n"
            + "            {\n"
            + "                display: inline-block;\n"
            + "                background-repeat: no-repeat;\n"
            + "                cursor: pointer;\n"
            + "                float: left;\n"
            + "                width: 100%;\n"
            + "                height: 100%;\n"
            + "            }\n"
            + "\n"
            + "            .pointer_cursor,.fld-recaptcha-mode,.fld-recaptcha-refresh,.fld-recaptcha-help\n"
            + "            {\n"
            + "                cursor: pointer;\n"
            + "            }\n"
            + "\n"
            + "            p,h1,h2,h3,h4,h5,h6,ol,ul,span.actAsPara /* disable Android font boosting */\n"
            + "            {\n"
            + "                max-height: 1000000px;\n"
            + "            }\n"
            + "\n"
            + "            .superscript\n"
            + "            {\n"
            + "                vertical-align: super;\n"
            + "                font-size: 66%;\n"
            + "                line-height: 0px;\n"
            + "            }\n"
            + "\n"
            + "            .subscript\n"
            + "            {\n"
            + "                vertical-align: sub;\n"
            + "                font-size: 66%;\n"
            + "                line-height: 0px;\n"
            + "            }\n"
            + "\n"
            + "            .horizontalSlideShow /* disable left-right panning on horizondal slide shows */\n"
            + "            {\n"
            + "                -ms-touch-action: pan-y;\n"
            + "                touch-action: pan-y;\n"
            + "            }\n"
            + "\n"
            + "            .verticalSlideShow /* disable up-down panning on vertical slide shows */\n"
            + "            {\n"
            + "                -ms-touch-action: pan-x;\n"
            + "                touch-action: pan-x;\n"
            + "            }\n"
            + "\n"
            + "            .colelem100,.verticalspacer /* a child element of a column that is 100% width */\n"
            + "            {\n"
            + "                clear: both;\n"
            + "            }\n"
            + "\n"
            + "            .popup_element.Inactive,.js .disn,.hidden,.breakpoint\n"
            + "            {\n"
            + "                display: none;\n"
            + "            }\n"
            + "\n"
            + "            #muse_css_mq\n"
            + "            {\n"
            + "                position: absolute;\n"
            + "                display: none;\n"
            + "                background-color: #FFFFFE;\n"
            + "            }\n"
            + "\n"
            + "            .fluid_height_spacer\n"
            + "            {\n"
            + "                position: static;\n"
            + "                width: 0.01px;\n"
            + "                float: left;\n"
            + "            }\n"
            + "           \n"
            + "        </style>";

    
}
