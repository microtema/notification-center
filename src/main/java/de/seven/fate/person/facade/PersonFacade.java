package de.seven.fate.person.facade;

import de.seven.fate.person.bo.PersonBO;
import de.seven.fate.person.converter.Person2PersonBOConverter;
import de.seven.fate.person.model.Person;
import de.seven.fate.person.service.PersonService;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.NoSuchElementException;

/**
 * Created by Mario on 17.02.2016.
 */
@Stateless
public class PersonFacade {

    private static final Logger logger = Logger.getLogger(PersonFacade.class);

    @Inject
    private PersonService service;

    @Inject
    private Person2PersonBOConverter converter;

    /**
     * @param ldapId
     * @return PersonBO
     * @Throw NoSuchElementException
     */
    public PersonBO getPerson(String ldapId) {
        Validate.notEmpty(ldapId);

        Person person = service.getPersonByLdapId(ldapId);

        if (person == null) {
            throw new NoSuchElementException("Unable to get person by ldapId: " + ldapId);
        }

        return converter.convert(person);
    }
}
