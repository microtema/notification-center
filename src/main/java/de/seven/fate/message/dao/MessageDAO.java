package de.seven.fate.message.dao;

import de.seven.fate.dao.GenericEntityDAO;
import de.seven.fate.message.model.Message;
import de.seven.fate.person.enums.MessageType;
import de.seven.fate.person.model.Person;
import org.apache.commons.lang3.Validate;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * Created by Mario on 14.02.2016.
 */
public class MessageDAO extends GenericEntityDAO<Message, Long> {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MessageDAO.class);

    private static final int MAX_RESULTS_SIZE = 100;

    public List<Message> findMessagesByPerson(Person person) {
        Validate.notNull(person);

        Query query = createNamedQuery(Message.FIND_BY_PERSON, "person", person);

        query.setMaxResults(MAX_RESULTS_SIZE);

        return query.getResultList();
    }

    public List<Message> findMessagesByPerson(String ldapId) {
        Validate.notNull(ldapId);

        Query query = createNamedQuery(Message.FIND_BY_LDAP_ID, "ldapId", ldapId);

        query.setMaxResults(MAX_RESULTS_SIZE);

        return query.getResultList();
    }

    public List<Message> findAllByPubDate(Date startPubDate, Date endPubDate) {
        Validate.notNull(startPubDate);

        if (endPubDate == null) {
            endPubDate = new Date();
        }

        Query query = createNamedQuery(Message.FIND_BY_PUB_DATE, "startPubDate", startPubDate, "endPubDate", endPubDate);

        query.setMaxResults(100);

        return query.getResultList();
    }

    @Override
    public void copyProperties(Message recent, Message entity) {
        Validate.notNull(recent);
        Validate.notNull(entity);

        recent.setMessageType(entity.getMessageType());
    }

    public void removeAll(Person person) {
        Validate.notNull(person, "person should not be null");

        int executeUpdate = createNamedQuery(Message.DELETE_BY_PERSON, "person", person).executeUpdate();

        logger.debug(executeUpdate + " messages has been deleted from person: " + person.getLdapId());
    }

    public List<Message> findMessagesByPersonAndType(String ldapId, MessageType messageType) {
        Validate.notNull(ldapId, "person ldapId should not be null");
        Validate.notNull(messageType);

        Query query = createNamedQuery(Message.FIND_BY_LDAP_ID_AND_TYPE, "ldapId", ldapId, "messageType", messageType);

        query.setMaxResults(MAX_RESULTS_SIZE);

        return query.getResultList();
    }
}
