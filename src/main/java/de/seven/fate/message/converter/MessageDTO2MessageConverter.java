package de.seven.fate.message.converter;

import de.seven.fate.converter.AbstractConverter;
import de.seven.fate.message.dto.MessageDTO;
import de.seven.fate.message.model.Message;
import de.seven.fate.person.converter.PersonDTO2PersonConverter;
import de.seven.fate.person.enums.MessageType;
import org.apache.commons.lang3.Validate;

import javax.inject.Inject;

/**
 * Created by Mario on 16.02.2016.
 */
public class MessageDTO2MessageConverter extends AbstractConverter<Message, MessageDTO> {

    private final PersonDTO2PersonConverter personDTO2PersonConverter;

    @Inject
    public MessageDTO2MessageConverter(PersonDTO2PersonConverter personDTO2PersonConverter) {
        this.personDTO2PersonConverter = personDTO2PersonConverter;
    }

    @Override
    public void update(Message dest, MessageDTO orig) {
        Validate.notNull(dest, "dest should not be null");
        if (orig == null) {
            return;
        }

        dest.setDescription(orig.getDescription());
        dest.setImage(orig.getImage());
        dest.setPubDate(orig.getPubDate());

        dest.setMessageType(MessageType.PUBLISHED);

        dest.setPerson(personDTO2PersonConverter.convert(orig.getPerson()));
    }

    @Override
    public Class<Message> getDestinationType() {
        return Message.class;
    }
}
