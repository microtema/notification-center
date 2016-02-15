package de.seven.fate.message.dao;

import de.seven.fate.message.dto.MessageDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mario on 15.02.2016.
 */

@XmlRootElement(name = "messages")
@XmlAccessorType(XmlAccessType.FIELD)
public class MessagesDTO {

    @XmlElement(name = "message", required = true, nillable = false)
    private List<MessageDTO> messages = new ArrayList<>();

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }
}
