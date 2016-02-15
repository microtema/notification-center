package de.seven.fate.person.dao;

import de.seven.fate.dao.GenericEntityDAO;
import de.seven.fate.message.dao.MessageDAO;
import de.seven.fate.message.model.Message;
import de.seven.fate.person.model.Person;

import static de.seven.fate.util.CollectionUtil.*;

import org.apache.commons.lang3.Validate;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mario on 14.02.2016.
 */
public class PersonDAO extends GenericEntityDAO<Person, Long> {


    private final MessageDAO messageDAO;

    @Inject
    public PersonDAO(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    @Override
    public void save(Person entity) {

        List<Message> messages = trimToEmpty(entity.getMessages());

        for (Message message : messages) {
            message.setPerson(entity);
        }

        saveImpl(entity);
    }

    @Override
    public void remove(Person entity) {
        Validate.notNull(entity, " entity should not be null");

        Person recent = get(entity);

        Iterator<Message> iterator = iterator(recent.getMessages());

        while (iterator.hasNext()) {

            Message message = iterator.next();
            message.setPerson(null);
            messageDAO.remove(message);
            iterator.remove();
        }

        removeImpl(entity);
    }
}
