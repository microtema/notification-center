package de.seven.fate.message.service;

import de.seven.fate.message.dao.MessageDAO;
import de.seven.fate.message.model.Message;
import de.seven.fate.person.dao.PersonDAO;
import de.seven.fate.person.enums.MessageType;
import de.seven.fate.person.model.Person;
import de.seven.fate.util.CollectionUtil;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mario on 15.02.2016.
 */
public class MessageService {

    private static final Logger logger = Logger.getLogger(XmlMessageService.class);

    @Inject
    private MessageDAO dao;

    @Inject
    private PersonDAO personDAO;


    public Message getMessage(Message message) {

        return dao.get(message);
    }

    public List<Message> findMessagesByPerson(String ldapId) {

        return dao.findMessagesByPerson(ldapId);
    }

    public List<Message> findMessagesByPersonAndType(String userName, MessageType messageType) {

        return dao.findMessagesByPersonAndType(userName, messageType);
    }

    public void removeMessage(Message message) {

        dao.remove(message);
    }

    public void removeMessage(Long messageId) {

        dao.remove(dao.get(messageId));
    }

    public void saveMessage(Message message) {

        saveMessage(Arrays.asList(message), message.getPerson());
    }

    public void saveMessage(List<Message> messages) {

        for (Message message : messages) {
            saveMessage(message);
        }
    }

    public Message updateMessage(Message message) {
        return dao.saveOrUpdate(message);
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

    public void removeAllMessage(String personLdapId) {
        Validate.notNull(personLdapId);

        Person person = personDAO.getByLdapId(personLdapId);

        dao.removeAll(person);
    }


    public void markMassage(List<Long> messageIds, MessageType messageType) {
        Validate.notNull(messageType);

        if (CollectionUtil.isEmpty(messageIds)) {
            return;
        }

        int executeUpdate = dao.createNamedQuery(Message.UPDATE_TYPE, "ids", messageIds, "messageType", messageType).executeUpdate();

        logger.warn("update " + executeUpdate + " messages to type: " + messageType);
    }
}
