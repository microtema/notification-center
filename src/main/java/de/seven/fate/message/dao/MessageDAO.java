package de.seven.fate.message.dao;

import de.seven.fate.dao.GenericEntityDAO;
import de.seven.fate.message.model.Message;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.GenericEntity;

/**
 * Created by Mario on 14.02.2016.
 */
public class MessageDAO extends GenericEntityDAO<Message, Long> {

}
