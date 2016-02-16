package de.seven.fate.message.service;

import de.seven.fate.message.dao.MessageDAO;
import de.seven.fate.message.model.Message;
import de.seven.fate.person.dao.PersonDAO;
import de.seven.fate.person.model.Person;
import org.apache.commons.lang3.Validate;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mario on 15.02.2016.
 */
public class MessageService {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(XmlMessageService.class);


    @Inject
    private MessageDAO dao;

    @Inject
    private PersonDAO personDAO;


    public Message getMessage(Message model) {

        return dao.get(model);
    }

    public void removeMessage(Message model) {

        dao.remove(model);
    }

    public void saveMessage(Message message) {

        saveMessage(Arrays.asList(message), message.getPerson());
    }

    public void saveMessage(List<Message> messages) {

        for (Message message : messages) {
            saveMessage(message);
        }
    }


    /**
     * save message only if person exist in DB
     */
    public void saveMessage(List<Message> messages, Person person) {
        Validate.notNull(messages);
        Validate.notNull(person);

        Person attachedPerson = personDAO.get(person);

        if (attachedPerson == null) {

            logger.warn("unable to find person by: " + person.getLdapId() + " message will be ignored");
            return;
        }

        for (Message message : messages) {
            message.setPerson(attachedPerson);
        }

        dao.save(messages);
    }
}
