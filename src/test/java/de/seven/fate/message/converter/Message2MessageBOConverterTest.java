package de.seven.fate.message.converter;

import de.seven.fate.message.bo.MessageBO;
import de.seven.fate.message.builder.MessageBuilder;
import de.seven.fate.message.model.Message;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Mario on 16.02.2016.
 */

public class Message2MessageBOConverterTest {

    Message2MessageBOConverter sut = new Message2MessageBOConverter();

    MessageBuilder builder = new MessageBuilder();

    Message model;

    @Before
    public void setUp() throws Exception {
        model = builder.min();
    }

    @Test
    public void testGetDestinationType() throws Exception {
        Assert.assertSame(MessageBO.class, sut.getDestinationType());
    }

    @Test
    public void testConvert() throws Exception {

        MessageBO bo = sut.convert(model);

        Assert.assertNotNull(bo);

        Assert.assertEquals(model.getId(), bo.getId());
        Assert.assertEquals(model.getImage(), bo.getImage());
        Assert.assertEquals(model.getDescription(), bo.getDescription());
        Assert.assertEquals(model.getPubDate(), bo.getPubDate());
        Assert.assertEquals(model.getMessageType().name(), bo.getType());
    }

    @Test
    public void shouldConvertCollection() throws Exception {
        List<Message> models = builder.list();
        List<MessageBO> bos = sut.convertList(models);

        Assert.assertEquals(models.size(), bos.size());
    }

    @Test
    public void testGetOriginalType() throws Exception {
        Assert.assertSame(Message.class, sut.getOriginalType());
    }
}
