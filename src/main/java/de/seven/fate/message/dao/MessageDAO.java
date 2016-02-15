package de.seven.fate.message.dao;

import de.seven.fate.dao.GenericEntityDAO;
import de.seven.fate.message.model.Message;
import de.seven.fate.person.model.Person;
import de.seven.fate.util.CollectionUtil;
import org.apache.commons.lang3.Validate;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * Created by Mario on 14.02.2016.
 */
public class MessageDAO extends GenericEntityDAO<Message, Long> {

    public Message findByPerson(Person person) {
        Validate.notNull(person);

        Query query = createNamedQuery(Message.FIND_BY_PERSON, "person", person);

        query.setMaxResults(1);

        List<Message> resultList = query.getResultList();

        return CollectionUtil.first(resultList);
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
}
