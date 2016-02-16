package de.seven.fate.person.converter;

import de.seven.fate.person.builder.PersonDTOBuilder;
import de.seven.fate.person.dto.PersonDTO;
import de.seven.fate.person.model.Person;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Mario on 16.02.2016.
 */
public class PersonDTO2PersonConverterTest {

    PersonDTO2PersonConverter sut = new PersonDTO2PersonConverter();

    PersonDTOBuilder builder = new PersonDTOBuilder();
    PersonDTO dto = builder.min();


    @Test
    public void testGetDestinationType() throws Exception {
        Assert.assertSame(Person.class, sut.getDestinationType());
    }

    @Test
    public void testConvert() throws Exception {
        Person person = sut.convert(dto);
        Assert.assertNotNull(person);

        Assert.assertEquals(dto.getLdapId(), person.getLdapId());
        Assert.assertNull(person.getId());
        Assert.assertNull(person.getMessages());
    }

    @Test
    public void testGetOriginalType() throws Exception {
        Assert.assertSame(PersonDTO.class, sut.getOriginalType());
    }
}
