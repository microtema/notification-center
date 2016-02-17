package de.seven.fate.person.converter;

import de.seven.fate.converter.AbstractConverter;
import de.seven.fate.person.bo.PersonBO;
import de.seven.fate.person.model.Person;

/**
 * Created by Mario on 17.02.2016.
 */
public class PersonBO2PersonConverter extends AbstractConverter<Person, PersonBO> {

    @Override
    public Class<Person> getDestinationType() {
        return Person.class;
    }
}
