package it.de.seven.fate.message.service;

import de.seven.fate.message.builder.MessageDTOBuilder;
import de.seven.fate.message.dao.MessageDAO;
import de.seven.fate.message.dto.MessageDTO;
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
    MessageDTOBuilder builder;

    @Inject
    PersonBuilder personBuilder;

    @Inject
    UserTransaction utx;

    @PersistenceContext(unitName = "wcmsds")
    EntityManager em;

    List<MessageDTO> messageDTOs;

    Person person;

    @Deployment
    public static WebArchive createDeployment() {
        return ArchiveDeployment.createDeployment();
    }

    @Before
    public void setUp() throws Exception {

        messageDTOs = builder.list();

        utx.begin();
        em.joinTransaction();

        person = personBuilder.min();
        person.setLdapId("mtema");

        em.persist(person);

        utx.commit();

        for (MessageDTO messageDTO : messageDTOs) {
            messageDTO.getPerson().setLdapId(person.getLdapId());
        }
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


    @Test
    public void shouldNotSaveMessagesOnMissingPerson() {

        sut.process(builder.list());

        Assert.assertTrue(dao.list().isEmpty());
    }

    @Test
    public void shouldNotSaveMessagesOnExistingPerson() throws Exception {

        sut.process(messageDTOs);

        List<Message> messages = dao.list();
        Assert.assertEquals(1, messages.size());

        Message message = CollectionUtil.first(messages);
        MessageDTO dto = CollectionUtil.first(messageDTOs);

        Assert.assertEquals(MessageType.Published, message.getMessageType());
        Assert.assertEquals(dto.getDescription(), message.getDescription());
        Assert.assertEquals(dto.getImage(), message.getImage());
        Assert.assertEquals(dto.getPubDate(), message.getPubDate());

        Assert.assertEquals(person.getLdapId(), message.getPerson().getLdapId());
    }
}
