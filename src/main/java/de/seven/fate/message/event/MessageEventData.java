package de.seven.fate.message.event;

import de.seven.fate.event.PersonRelatedEventData;
import de.seven.fate.message.model.Message;

/**
 * Created by Mario on 16.02.2016.
 */
public class MessageEventData extends PersonRelatedEventData<Message> {

    public MessageEventData(String ldapId, Message data) {
        super(ldapId, data);
    }
}
