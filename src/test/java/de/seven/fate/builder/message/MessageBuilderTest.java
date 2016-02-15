package de.seven.fate.builder.message;

import de.seven.fate.message.builder.MessageBuilder;
import de.seven.fate.message.model.Message;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Mario on 14.02.2016.
 */

public class MessageBuilderTest {

    MessageBuilder sut = new MessageBuilder();

    @Test
    public void testMin() throws Exception {
        Message min = sut.min();

        Assert.assertNotNull(min);
        Assert.assertNotNull(min.getDescription());
        Assert.assertNotNull(min.getImage());
        Assert.assertNotNull(min.getMessageType());

        Assert.assertNull(min.getId());
        Assert.assertNull(min.getPerson());
    }
}
