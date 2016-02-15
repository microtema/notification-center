package de.seven.fate.person.service;

import de.seven.fate.person.dao.PersonDAO;
import de.seven.fate.person.model.Person;

import javax.inject.Inject;

/**
 * Created by Mario on 15.02.2016.
 */
public class PersonService {

    @Inject
    private PersonDAO dao;


    public Person getPerson(Person model) {
        return dao.get(model);
    }

    public void removePerson(Person model) {
        dao.remove(model);
    }
}
