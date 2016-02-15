package it.de.seven.fate.person.dao;

import de.seven.fate.message.builder.MessageBuilder;
import de.seven.fate.message.dao.MessageDAO;
import de.seven.fate.message.model.Message;
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
 * Created by Mario on 14.02.2016.
 */
@RunWith(Arquillian.class)
public class PersonDAOIT {

    @Inject
    PersonDAO sut;

    @Inject
    MessageDAO messageDAO;

    @Inject
    PersonBuilder builder;

    @Inject
    MessageBuilder messageBuilder;

    @Inject
    UserTransaction utx;

    @PersistenceContext(unitName = "wcmsds")
    EntityManager em;

    Person model;

    @Deployment
    public static WebArchive createDeployment() {
        return ArchiveDeployment.createDeployment();
    }

    @Before
    public void setUp() throws Exception {

        utx.begin();
        em.joinTransaction();

        model = builder.min();
        em.persist(model);

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
        Assert.assertEquals(model, sut.get(model.getId()));
    }

    @Test
    public void shouldGetByEntity() throws Exception {
        utx.begin();
        em.joinTransaction();

        Assert.assertEquals(model, sut.get(model));

        utx.commit();
    }

    @Test
    public void shouldDelete() throws Exception {

        utx.begin();
        em.joinTransaction();

        List<Message> messages = CollectionUtil.trimToEmpty(model.getMessages());

        sut.remove(model);

        Assert.assertTrue( sut.list().isEmpty());

        for(Message message : messages){
            Assert.assertNull(messageDAO.get(message));
        }

        utx.commit();
    }

    @Test
    public void shouldAddMessage() throws Exception {

        utx.begin();
        em.joinTransaction();

        model = sut.get(model);

        Message message = messageBuilder.min();
        message.setPerson(model);

        model.getMessages().add(message);

        sut.saveOrUpdate(model);

        Assert.assertEquals(model, sut.get(model));

        utx.commit();
    }

    @Test
    public void shouldSaveWithMessage() throws Exception {

        utx.begin();
        em.joinTransaction();

        model = builder.max();

        sut.save(model);

        Assert.assertEquals(model, sut.get(model));

        utx.commit();
    }

    @Test
    public void shouldGetByMessage() throws Exception {

        utx.begin();
        em.joinTransaction();

        model = builder.max();

        sut.save(model);

        Message message = CollectionUtil.random(model.getMessages());

        Assert.assertEquals(model, sut.getByMessage(message));

        utx.commit();
    }

    @Test
    public void shouldGetByLdapId() throws Exception {

        utx.begin();
        em.joinTransaction();

        Assert.assertEquals(model, sut.getByLdapId(model.getLdapId()));

        utx.commit();
    }

    @Test
    public void shouldGetByLdapIdOnNullId() throws Exception {

        utx.begin();
        em.joinTransaction();

        model.setId(null);

        Assert.assertEquals(model, sut.get(model));

        utx.commit();
    }
}
