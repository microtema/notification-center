package it.de.seven.fate.message.service;

import de.seven.fate.message.dao.MessageDAO;
import de.seven.fate.message.model.Message;
import de.seven.fate.message.service.MessageService;
import de.seven.fate.message.service.XmlMessageService;
import de.seven.fate.person.builder.PersonBuilder;
import de.seven.fate.person.dao.PersonDAO;
import de.seven.fate.person.enums.MessageType;
import de.seven.fate.person.model.Person;
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
 * Created by Mario on 16.02.2016.
 */
@RunWith(Arquillian.class)
public class XmlMessageServiceIT {

    @Inject
    XmlMessageService sut;

    @Inject
    MessageService service;

    @Inject
    MessageDAO dao;

    @Inject
    PersonDAO personDAO;

    @Inject
    PersonBuilder personBuilder;

    @Inject
    UserTransaction utx;

    @PersistenceContext(unitName = "wcmsds")
    EntityManager em;

    Person person;

    @Deployment
    public static WebArchive createDeployment() {
        return ArchiveDeployment.createDeployment();
    }

    @Before
    public void setUp() throws Exception {

        utx.begin();
        em.joinTransaction();

        person = personBuilder.min();
        person.setLdapId("mtema");

        em.persist(person);

        utx.commit();
    }

    @After
    public void tearDown() throws Exception {
        utx.begin();
        em.joinTransaction();

        personDAO.removeAll();

        utx.commit();
    }

    @Test(expected = Exception.class)
    public void shouldThrowNullPointerExceptionOnNull() {
        sut.process(null);
    }

    @Test(expected = Exception.class)
    public void shouldThrowNullPointerExceptionOnEmpty() {
        sut.process("");
    }

    @Test
    public void shouldNotSaveMessagesOnMissingPerson() {

        sut.process("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<messages>\n" +
                "    <message>\n" +
                "        <person>\n" +
                "            <ldapId>unknown</ldapId>\n" +
                "        </person>\n" +
                "        <description>Description</description>\n" +
                "        <image>image</image>\n" +
                "        <pubDate>2016-02-15T13:22:38.521+01:00</pubDate>\n" +
                "    </message>\n" +
                "</messages>");

        Assert.assertTrue(dao.list().isEmpty());
    }

    @Test
    public void shouldNotSaveMessagesOnExistingPerson() throws Exception {

        sut.process("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<messages>\n" +
                "    <message>\n" +
                "        <person>\n" +
                "            <ldapId>mtema</ldapId>\n" +
                "        </person>\n" +
                "        <description>Description</description>\n" +
                "        <image>image.jpeg</image>\n" +
                "        <pubDate>2016-02-15T13:22:38.521+01:00</pubDate>\n" +
                "    </message>\n" +
                "</messages>");

        List<Message> messages = dao.list();
        Assert.assertEquals(1, messages.size());

        Message message = CollectionUtil.first(messages);

        Assert.assertEquals(MessageType.Published, message.getMessageType());
        Assert.assertEquals("Description", message.getDescription());
        Assert.assertEquals("image.jpeg", message.getImage());
        Assert.assertEquals(1455538958521l, message.getPubDate().getTime());

        Assert.assertEquals(person.getLdapId(), message.getPerson().getLdapId());
    }
}
