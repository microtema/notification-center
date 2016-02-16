package de.seven.fate.person.converter;

import de.seven.fate.converter.AbstractConverter;
import de.seven.fate.person.dto.PersonDTO;
import de.seven.fate.person.model.Person;

/**
 * Created by Mario on 16.02.2016.
 */
public class PersonDTO2PersonConverter extends AbstractConverter<Person, PersonDTO> {

    @Override
    public Class<Person> getDestinationType() {
        return Person.class;
    }
}
