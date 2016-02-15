package it.de.seven.fate.message.dao;

import de.seven.fate.builder.message.MessageBuilder;
import de.seven.fate.builder.person.PersonBuilder;
import de.seven.fate.message.dao.MessageDAO;
import de.seven.fate.message.model.Message;
import de.seven.fate.person.model.Person;
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

/**
 * Created by Mario on 14.02.2016.
 */
@RunWith(Arquillian.class)
public class MessageDAOIT {

    @Inject
    MessageDAO sut;

    @Inject
    MessageBuilder builder;

    @Inject
    PersonBuilder personBuilder;

    @Inject
    UserTransaction utx;

    @PersistenceContext(unitName = "wcmsds")
    EntityManager em;

    Person person;
    Message model;

    @Deployment
    public static WebArchive createDeployment() {
        return ArchiveDeployment.createDeployment();
    }

    @Before
    public void setUp() throws Exception {

        utx.begin();
        em.joinTransaction();

        person = personBuilder.min();
        em.persist(person);

        model = builder.min();

        //finally bind to person
        model.setPerson(person);

        sut.save(model);

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
    public void shouldGetByEntity() {
        Assert.assertEquals(model, sut.get(model));
    }

    @Test
    public void shouldUpdate() throws Exception{

        utx.begin();
        em.joinTransaction();

        model.setDescription("some other description");
        model.setPerson(em.find(Person.class,person.getId()));

        sut.saveOrUpdate(model);

        utx.commit();

        Assert.assertEquals(model, sut.get(model.getId()));
    }

    @Test
    public void shouldGetByPerson() throws Exception{

        Message entity = sut.findByPerson(model.getPerson());

        Assert.assertEquals(model, entity);
    }
}
