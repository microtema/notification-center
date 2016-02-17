package de.seven.fate.message.converter;

import de.seven.fate.converter.AbstractConverter;
import de.seven.fate.message.bo.MessageBO;
import de.seven.fate.message.model.Message;

/**
 * Created by Mario on 16.02.2016.
 */
public class Message2MessageBOConverter extends AbstractConverter<MessageBO, Message> {


    @Override
    public void update(MessageBO dest, Message orig) {
        super.update(dest, orig);

        dest.setType(orig.getMessageType().name());
    }

    @Override
    public Class<MessageBO> getDestinationType() {
        return MessageBO.class;
    }
}
