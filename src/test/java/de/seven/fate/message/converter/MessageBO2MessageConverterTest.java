package de.seven.fate.message.converter;

import de.seven.fate.message.bo.MessageBO;
import de.seven.fate.message.builder.MessageBOBuilder;
import de.seven.fate.message.model.Message;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Mario on 17.02.2016.
 */

public class MessageBO2MessageConverterTest {

    MessageBO2MessageConverter sut = new MessageBO2MessageConverter();

    MessageBOBuilder builder = new MessageBOBuilder();

    MessageBO bo = builder.min();

    @Test
    public void testGetDestinationType() throws Exception {
        Assert.assertSame(Message.class, sut.getDestinationType());
    }

    @Test
    public void testConvert() throws Exception {
        Message message = sut.convert(bo);

        Assert.assertNotNull(message);

        Assert.assertEquals(bo.getType(), message.getMessageType().name());
        Assert.assertEquals(bo.getId(), message.getId());
        Assert.assertEquals(bo.getImage(), message.getImage());
        Assert.assertEquals(bo.getPubDate(), message.getPubDate());
        Assert.assertEquals(bo.getDescription(), message.getDescription());

    }

    @Test
    public void testGetOriginalType() throws Exception {
        Assert.assertSame(MessageBO.class, sut.getOriginalType());
    }
}
