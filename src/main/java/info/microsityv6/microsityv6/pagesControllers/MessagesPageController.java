/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.pagesControllers;

import info.microsityv6.microsityv6.entitys.Message;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Panker-RDP
 */
@Named(value = "messagesPageController")
@SessionScoped
public class MessagesPageController extends PageController implements Serializable {
    
    private List<Message> messages;
    
    public MessagesPageController() {
    }

    public List<Message> getMessages() {
        messages=super.getCurrent().getMessages();
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
    
    
    
}
