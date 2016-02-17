package de.seven.fate.person.converter;

import de.seven.fate.person.bo.PersonBO;
import de.seven.fate.person.builder.PersonBOBuilder;
import de.seven.fate.person.model.Person;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Mario on 17.02.2016.
 */
public class PersonBO2PersonConverterTest {

    PersonBO2PersonConverter sut = new PersonBO2PersonConverter();
    PersonBOBuilder builder = new PersonBOBuilder();
    PersonBO bo = new PersonBO();

    @Test
    public void testGetDestinationType() throws Exception {
        Assert.assertSame(Person.class, sut.getDestinationType());
    }

    @Test
    public void testConvert() throws Exception {
        Person person = sut.convert(bo);

        Assert.assertNotNull(person);
        Assert.assertEquals(bo.getLdapId(), person.getLdapId());
        Assert.assertNull(person.getMessages());
        Assert.assertNull(person.getId());
    }

    @Test
    public void testGetOriginalType() throws Exception {
        Assert.assertSame(PersonBO.class, sut.getOriginalType());
    }
}
