package de.seven.fate.person.dao;

import de.seven.fate.dao.GenericEntityDAO;
import de.seven.fate.message.model.Message;
import de.seven.fate.person.model.Person;
import de.seven.fate.util.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import javax.persistence.Query;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static de.seven.fate.util.CollectionUtil.iterator;
import static de.seven.fate.util.CollectionUtil.trimToEmpty;

/**
 * Created by Mario on 14.02.2016.
 */
public class PersonDAO extends GenericEntityDAO<Person, Long> {

    @Override
    public Person get(Person entity) {
        Validate.notNull(entity);

        if (entity.getId() != null) {
            return super.get(entity);
        }

        if (StringUtils.isNotEmpty(entity.getLdapId())) {
            return getByLdapId(entity.getLdapId());
        }

        throw new NoSuchElementException("Unable to get person: " + entity);
    }


    @Override
    public void save(Person entity) {
        Validate.notNull(entity, " entity should not be null");

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
            iterator.remove();
        }

        removeImpl(entity);
    }

    public Person getByMessage(Message message) {
        Validate.notNull(message, " message should not be null");

        Query query = createNamedQuery(Person.FIND_BY_MESSAGE, "message", message);

        query.setMaxResults(1);
        List<Person> resultList = query.getResultList();

        return CollectionUtil.first(resultList);
    }

    public Person getByLdapId(String ldapId) {
        Validate.notNull(ldapId, " ldapId should not be null");

        Query query = createNamedQuery(Person.FIND_BY_LDAPID, "ldapId", ldapId);

        query.setMaxResults(1);
        List<Person> resultList = query.getResultList();

        return CollectionUtil.first(resultList);
    }


}
