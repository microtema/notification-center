package de.seven.fate.person.converter;

import de.seven.fate.message.builder.MessageBuilder;
import de.seven.fate.person.bo.PersonBO;
import de.seven.fate.person.builder.PersonBuilder;
import de.seven.fate.person.model.Person;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Mario on 17.02.2016.
 */
public class Person2PersonBOConverterTest {

    Person2PersonBOConverter sut = new Person2PersonBOConverter();
    PersonBuilder builder = new PersonBuilder(new MessageBuilder());
    Person bo = builder.min();

    @Test
    public void testGetDestinationType() throws Exception {
        Assert.assertSame(PersonBO.class, sut.getDestinationType());
    }

    @Test
    public void testConvert() throws Exception {
        PersonBO person = sut.convert(bo);

        Assert.assertNotNull(person);
        Assert.assertEquals(bo.getLdapId(), person.getLdapId());
    }

    @Test
    public void testGetOriginalType() throws Exception {
        Assert.assertSame(Person.class, sut.getOriginalType());
    }
}
