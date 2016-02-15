package de.seven.fate.message.builder;

import de.seven.fate.builder.AbstractModelBuilder;
import de.seven.fate.message.dao.MessagesDTO;

import javax.inject.Inject;

/**
 * Created by Mario on 15.02.2016.
 */
public class MessagesDTOBuilder extends AbstractModelBuilder<MessagesDTO> {

    private final MessageDTOBuilder messageDTOBuilder;

    @Inject
    public MessagesDTOBuilder(MessageDTOBuilder messageDTOBuilder) {
        this.messageDTOBuilder = messageDTOBuilder;
    }

    @Override
    public MessagesDTO min() {
        MessagesDTO min = super.min();

        min.setMessages(messageDTOBuilder.list());

        return min;
    }
}
