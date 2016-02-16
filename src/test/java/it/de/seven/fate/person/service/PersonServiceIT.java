package it.de.seven.fate.person.service;

import de.seven.fate.person.builder.PersonBuilder;
import de.seven.fate.person.dao.PersonDAO;
import de.seven.fate.person.model.Person;
import de.seven.fate.person.service.PersonService;
import de.seven.fate.util.CollectionUtil;
import it.de.seven.fate.ArchiveDeployment;
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
public class PersonServiceIT {

    @Inject
    PersonService sut;

    @Inject
    PersonBuilder builder;

    @Inject
    PersonDAO dao;

    @Inject
    UserTransaction utx;

    @PersistenceContext(unitName = "wcmsds")
    EntityManager em;

    List<Person> models;


    @Deployment
    public static WebArchive createDeployment() {
        return ArchiveDeployment.createDeployment();
    }

    @Before
    public void setUp() throws Exception {
        utx.begin();
        em.joinTransaction();

        models = builder.list();

        dao.save(models);

        utx.commit();
    }

    @After
    public void tearDown() throws Exception {
        utx.begin();
        em.joinTransaction();

        dao.removeAll();

        utx.commit();
    }

    @Test
    public void shouldGet() throws Exception {
        Person model = CollectionUtil.random(models);
        Assert.assertEquals(model, sut.getPerson(model));
    }

    @Test
    public void shouldRemove() throws Exception {
        utx.begin();
        em.joinTransaction();

        Person model = CollectionUtil.random(models);

        sut.removePerson(model);

        Assert.assertNull(sut.getPerson(model));

        utx.commit();
    }
}
