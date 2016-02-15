package de.seven.fate.message.builder;

import de.seven.fate.message.dao.MessagesDTO;
import de.seven.fate.message.dto.MessageDTO;
import de.seven.fate.person.builder.PersonDTOBuilder;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Mario on 15.02.2016.
 */
public class MessagesDTOBuilderTest {


    MessagesDTOBuilder sut = new MessagesDTOBuilder(new MessageDTOBuilder(new PersonDTOBuilder()));

    @Test
    public void testMin() throws Exception {
        MessagesDTO min = sut.min();

        Assert.assertNotNull(min);
        Assert.assertNotNull(min.getMessages());
        Assert.assertFalse(min.getMessages().isEmpty());
        MessageDTO messageDTO = min.getMessages().get(0);
        Assert.assertNotNull(messageDTO.getImage());
        Assert.assertNotNull(messageDTO.getDescription());
        Assert.assertNotNull(messageDTO.getPubDate());
        Assert.assertNotNull(messageDTO.getPerson());
    }
}
