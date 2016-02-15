package de.seven.fate.builder.person;

import de.seven.fate.message.builder.MessageBuilder;
import de.seven.fate.person.builder.PersonBuilder;
import de.seven.fate.person.model.Person;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Mario on 14.02.2016.
 */

public class PersonBuilderTest {


    PersonBuilder sut = new PersonBuilder(new MessageBuilder());

    @Test
    public void testMin() throws Exception {
        Person min = sut.min();

        Assert.assertNotNull(min);
        Assert.assertNotNull(min.getName());
        Assert.assertNull(min.getId());
        Assert.assertNull(min.getMessages());
    }

    @Test
    public void testMax() throws Exception {

        Person max = sut.max();

        Assert.assertNotNull(max);

        Assert.assertFalse(max.getMessages().isEmpty());
    }
}
