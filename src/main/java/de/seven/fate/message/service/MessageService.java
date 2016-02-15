package de.seven.fate.message.service;

import de.seven.fate.message.dao.MessageDAO;
import de.seven.fate.message.model.Message;
import de.seven.fate.person.dao.PersonDAO;
import de.seven.fate.person.model.Person;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mario on 15.02.2016.
 */
public class MessageService {

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

    public void saveMessage(List<Message> messages, Person person) {

        Person attachedPerson = personDAO.get(person);

        for (Message message : messages) {
            message.setPerson(attachedPerson);
        }

        dao.save(messages);
    }
}
