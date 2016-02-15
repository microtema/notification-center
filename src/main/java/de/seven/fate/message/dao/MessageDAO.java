package de.seven.fate.message.dao;

import de.seven.fate.dao.GenericEntityDAO;
import de.seven.fate.message.model.Message;
import de.seven.fate.person.model.Person;
import de.seven.fate.util.CollectionUtil;
import org.apache.commons.lang3.Validate;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.GenericEntity;
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
}
