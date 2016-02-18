package de.seven.fate.message.converter;

import de.seven.fate.message.builder.MessageDTOBuilder;
import de.seven.fate.message.dto.MessageDTO;
import de.seven.fate.message.model.Message;
import de.seven.fate.person.builder.PersonDTOBuilder;
import de.seven.fate.person.converter.PersonDTO2PersonConverter;
import de.seven.fate.person.enums.MessageType;
import de.seven.fate.person.model.Person;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Mario on 16.02.2016.
 */

public class MessageDTO2MessageConverterTest {

    MessageDTO2MessageConverter sut = new MessageDTO2MessageConverter(new PersonDTO2PersonConverter());

    MessageDTOBuilder builder = new MessageDTOBuilder(new PersonDTOBuilder());

    MessageDTO dto;

    @Before
    public void setUp() throws Exception {
        dto = builder.min();
    }

    @Test
    public void testGetDestinationType() throws Exception {
        Assert.assertSame(Message.class, sut.getDestinationType());
    }

    @Test
    public void testConvert() throws Exception {
        Message message = sut.convert(dto);

        Assert.assertNotNull(message);
        Assert.assertEquals(dto.getDescription(), message.getDescription());
        Assert.assertEquals(dto.getImage(), message.getImage());
        Assert.assertEquals(dto.getTitle(), message.getTitle());
        Assert.assertEquals(dto.getPubDate(), message.getPubDate());
        Assert.assertEquals(MessageType.UNREAD, message.getMessageType());

        Person person = message.getPerson();
        Assert.assertNotNull(message);
        Assert.assertEquals(dto.getPerson().getLdapId(), person.getLdapId());
    }

    @Test
    public void testGetOriginalType() throws Exception {
        Assert.assertSame(MessageDTO.class, sut.getOriginalType());
    }
}
