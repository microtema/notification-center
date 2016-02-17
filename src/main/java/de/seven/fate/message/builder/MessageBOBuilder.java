package de.seven.fate.message.builder;

import de.seven.fate.builder.AbstractModelBuilder;
import de.seven.fate.message.bo.MessageBO;
import de.seven.fate.person.enums.MessageType;
import de.seven.fate.util.NumberUtil;

/**
 * Created by Mario on 14.02.2016.
 */
public class MessageBOBuilder extends AbstractModelBuilder<MessageBO> {


    @Override
    public MessageBO min() {
        MessageBO min = super.min();

        min.setType(MessageType.values()[NumberUtil.random(0, MessageType.values().length - 1)].name());

        return min;
    }
}
