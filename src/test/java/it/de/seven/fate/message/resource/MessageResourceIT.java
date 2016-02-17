package it.de.seven.fate.message.resource;

import de.seven.fate.message.bo.MessageBO;
import de.seven.fate.message.builder.MessageBOBuilder;
import de.seven.fate.message.model.Message;
import de.seven.fate.message.resource.MessageResource;
import de.seven.fate.person.builder.PersonBuilder;
import de.seven.fate.person.dao.PersonDAO;
import de.seven.fate.person.enums.MessageType;
import de.seven.fate.person.model.Person;
import de.seven.fate.util.CollectionUtil;
import it.de.seven.fate.ArchiveDeployment;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mario on 16.02.2016.
 */
@RunWith(Arquillian.class)
public class MessageResourceIT {

    MessageResource sut;

    @Inject
    PersonDAO personDAO;

    @Inject
    PersonBuilder builder;

    @Inject
    UserTransaction utx;

    @PersistenceContext(unitName = "wcmsds")
    EntityManager em;

    GenericType<List<MessageBO>> messageBOGenericType = new GenericType<List<MessageBO>>() {
    };

    @Deployment(testable = true)
    public static WebArchive createDeployment() {

        return ArchiveDeployment.createDeployment();
    }

    @Test
    @InSequence(1)
    public void setUp() throws Exception {
        utx.begin();
        em.joinTransaction();

        Person person = builder.max("mtema");

        //set at list one MessageType.PUBLISHED
        Message message = CollectionUtil.random(person.getMessages());
        message.setMessageType(MessageType.PUBLISHED);

        personDAO.save(person);

        utx.commit();
    }

    @Test
    @InSequence(100)
    public void tearDown() throws Exception {
        utx.begin();
        em.joinTransaction();

        personDAO.removeAll();

        utx.commit();
    }


    @Test
    @RunAsClient
    @InSequence(2)
    public void testGetUserMassages(@ArquillianResource URL baseURL) throws Exception {

        //given

        //when
        ClientRequest request = new ClientRequest(new URL(baseURL, "rest/message").toExternalForm());
        request.accept(MediaType.APPLICATION_JSON);
        request.setHttpMethod("GET");
        ClientResponse<List<MessageBO>> clientResponse = request.get(messageBOGenericType);

        //then
        assertEquals(Response.Status.OK.getStatusCode(), clientResponse.getStatus());

        List<MessageBO> messages = clientResponse.getEntity();

        Assert.assertNotNull(messages);
        Assert.assertFalse(messages.isEmpty());

    }


    @Test
    @RunAsClient
    @InSequence(3)
    public void testGetCurrentUserPublishedMassages(@ArquillianResource URL baseURL) throws Exception {

        //given

        //when
        ClientRequest request = new ClientRequest(new URL(baseURL, "rest/message/type/" + MessageType.PUBLISHED.name()).toExternalForm());
        request.accept(MediaType.APPLICATION_JSON);
        request.setHttpMethod("GET");
        ClientResponse<List<MessageBO>> clientResponse = request.get(messageBOGenericType);

        //then
        assertEquals(Response.Status.OK.getStatusCode(), clientResponse.getStatus());

        List<MessageBO> messages = clientResponse.getEntity();

        Assert.assertNotNull(messages);
        Assert.assertFalse(messages.isEmpty());

    }

    @Test
    @RunAsClient
    @InSequence(4)
    public void testPostCurrentUserMassage(@ArquillianResource URL baseURL) throws Exception {

        MessageBO messageBO = new MessageBOBuilder().min();
        messageBO.setId(1l);

        //given

        //when
        ClientRequest request = new ClientRequest(new URL(baseURL, "rest/message").toExternalForm());
        request.accept(MediaType.APPLICATION_JSON);
        request.setHttpMethod("POST");
        request.body(MediaType.APPLICATION_JSON_TYPE, messageBO);
        ClientResponse<MessageBO> clientResponse = request.post(MessageBO.class);

        //then
        assertEquals(Response.Status.OK.getStatusCode(), clientResponse.getStatus());

        MessageBO message = clientResponse.getEntity();

        Assert.assertNotNull(message);
        Assert.assertEquals(messageBO.getId(), message.getId());
        Assert.assertEquals(messageBO.getType(), message.getType());

    }

    @Test
    @RunAsClient
    @InSequence(5)
    public void testDeleteCurrentUserMassage(@ArquillianResource URL baseURL) throws Exception {

        //given

        //when
        ClientRequest request = new ClientRequest(new URL(baseURL, "rest/message/1").toExternalForm());
        request.accept(MediaType.APPLICATION_JSON);
        request.setHttpMethod("DELETE");
        ClientResponse<Boolean> clientResponse = request.delete(Boolean.class);

        //then
        assertEquals(Response.Status.OK.getStatusCode(), clientResponse.getStatus());

        Assert.assertTrue(clientResponse.getEntity());
    }

    @Test
    @RunAsClient
    @InSequence(6)
    public void testDeleteAllCurrentUserMassage(@ArquillianResource URL baseURL) throws Exception {

        //given

        //when
        ClientRequest request = new ClientRequest(new URL(baseURL, "rest/message/all").toExternalForm());
        request.accept(MediaType.APPLICATION_JSON);
        request.setHttpMethod("DELETE");
        ClientResponse<Boolean> clientResponse = request.delete(Boolean.class);

        //then
        assertEquals(Response.Status.OK.getStatusCode(), clientResponse.getStatus());

        Assert.assertTrue(clientResponse.getEntity());
    }


}
