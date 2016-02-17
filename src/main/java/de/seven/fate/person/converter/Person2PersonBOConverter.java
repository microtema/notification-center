package de.seven.fate.person.converter;

import de.seven.fate.converter.AbstractConverter;
import de.seven.fate.person.bo.PersonBO;
import de.seven.fate.person.model.Person;

/**
 * Created by Mario on 17.02.2016.
 */
public class Person2PersonBOConverter extends AbstractConverter<PersonBO, Person> {

    @Override
    public Class<PersonBO> getDestinationType() {
        return PersonBO.class;
    }
}
