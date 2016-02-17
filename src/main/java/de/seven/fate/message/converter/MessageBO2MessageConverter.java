package de.seven.fate.message.converter;

import de.seven.fate.converter.AbstractConverter;
import de.seven.fate.message.bo.MessageBO;
import de.seven.fate.message.model.Message;
import de.seven.fate.person.enums.MessageType;

/**
 * Created by Mario on 16.02.2016.
 */
public class MessageBO2MessageConverter extends AbstractConverter<Message, MessageBO> {


    @Override
    public void update(Message dest, MessageBO orig) {
        super.update(dest, orig);

        dest.setMessageType(MessageType.valueOf(orig.getType()));
    }

    @Override
    public Class<Message> getDestinationType() {
        return Message.class;
    }
}
