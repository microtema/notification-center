package de.seven.fate.message.builder;

import de.seven.fate.builder.AbstractModelBuilder;
import de.seven.fate.message.dto.MessageDTO;
import de.seven.fate.person.builder.PersonDTOBuilder;

import javax.inject.Inject;

/**
 * Created by Mario on 15.02.2016.
 */
public class MessageDTOBuilder extends AbstractModelBuilder<MessageDTO> {

    private final PersonDTOBuilder personDTOBuilder;

    @Inject
    public MessageDTOBuilder(PersonDTOBuilder personDTOBuilder) {
        this.personDTOBuilder = personDTOBuilder;
    }

    @Override
    public MessageDTO min() {
        MessageDTO min = super.min();

        min.setPerson(personDTOBuilder.min());

        return min;
    }
}
