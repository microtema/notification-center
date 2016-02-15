package de.seven.fate.message.builder;

import de.seven.fate.builder.AbstractModelBuilder;
import de.seven.fate.message.model.Message;
import de.seven.fate.person.enums.MessageType;
import de.seven.fate.util.NumberUtil;

/**
 * Created by Mario on 14.02.2016.
 */
public class MessageBuilder extends AbstractModelBuilder<Message> {

    @Override
    public Message min() {
        Message min = super.min();

        min.setId(null);
        min.setMessageType(MessageType.values()[NumberUtil.random(0, MessageType.values().length-1)]);

        return min;
    }
}
