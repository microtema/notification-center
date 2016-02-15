package it.de.seven.fate.message.service;

import de.seven.fate.message.builder.MessageBuilder;
import de.seven.fate.message.dao.MessageDAO;
import de.seven.fate.message.model.Message;
import de.seven.fate.message.service.MessageService;
import de.seven.fate.person.builder.PersonBuilder;
import de.seven.fate.person.dao.PersonDAO;
import de.seven.fate.person.model.Person;
import de.seven.fate.util.CollectionUtil;
import it.de.seven.fate.message.dao.ArchiveDeployment;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.List;

/**
 * Created by Mario on 15.02.2016.
 */
@RunWith(Arquillian.class)
public class MessageServiceIT {

    @Inject
    MessageService sut;

    @Inject
    MessageDAO dao;

    @Inject
    PersonBuilder personBuilder;

    @Inject
    PersonDAO personDAO;

    @Inject
    MessageBuilder builder;

    @Inject
    UserTransaction utx;

    @PersistenceContext(unitName = "wcmsds")
    EntityManager em;

    List<Message> models;

    @Deployment
    public static WebArchive createDeployment() {
        return ArchiveDeployment.createDeployment();
    }

    @Before
    public void setUp() throws Exception {

        utx.begin();
        em.joinTransaction();

        Person person = personBuilder.max();

        models = person.getMessages();

        personDAO.save(person);

        utx.commit();
    }

    @After
    public void tearDown() throws Exception {
        utx.begin();
        em.joinTransaction();

        dao.removeAll();
        personDAO.removeAll();

        utx.commit();
    }

    @Test
    public void shouldGet() throws Exception {
        Message model = CollectionUtil.random(models);
        Assert.assertEquals(model, sut.getMessage(model));
    }

    @Test
    public void shouldRemove() throws Exception {
        utx.begin();
        em.joinTransaction();

        Message model = CollectionUtil.random(models);

        sut.removeMessage(model);

        Assert.assertNull(sut.getMessage(model));

        utx.commit();
    }

}
