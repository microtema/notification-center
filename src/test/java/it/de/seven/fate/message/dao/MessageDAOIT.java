package it.de.seven.fate.message.dao;

import de.seven.fate.message.dao.MessageDAO;
import de.seven.fate.message.model.Message;
import de.seven.fate.person.enums.MessageType;
import de.seven.fate.person.model.Person;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Mario on 14.02.2016.
 */
@RunWith(Arquillian.class)
public class MessageDAOIT {

    @Inject
    MessageDAO sut;

    @Inject
    UserTransaction utx;

    @PersistenceContext(unitName = "wcmsds")
    EntityManager em;

    Person person = new Person();
    Message message = new Message();

    @Deployment
    public static WebArchive createDeployment() {
        return ArchiveDeployment.createDeployment();
    }

    @Before
    public void setUp() throws Exception {

        utx.begin();
        em.joinTransaction();

        person.setName("Mario");

        em.persist(person);

        message.setPubDate(new Date());
        message.setMessageType(MessageType.Published);
        message.setDescription("Some description");
        message.setImage("Some image");

        //finally bind to person
        message.setPerson(person);

        sut.save(message);

        utx.commit();
    }

    @After
    public void tearDown() throws Exception {
        utx.begin();
        em.joinTransaction();

        sut.removeAll();

        utx.commit();
    }

    @Test
    public void shouldCount() {
        Assert.assertEquals(new Long(1), sut.count());
    }
    @Test
    public void shouldListAll() {
        Assert.assertFalse(sut.list().isEmpty());
    }

    @Test
    public void shouldGetById() {
        Assert.assertEquals(message, sut.get(message.getId()));
    }

    @Test
    public void shouldGetByEntity() {
        Assert.assertEquals(message, sut.get(message));
    }
}
