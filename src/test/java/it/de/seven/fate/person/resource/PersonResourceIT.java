package it.de.seven.fate.person.resource;

import de.seven.fate.person.bo.PersonBO;
import de.seven.fate.person.builder.PersonBuilder;
import de.seven.fate.person.dao.PersonDAO;
import de.seven.fate.person.model.Person;
import de.seven.fate.person.resource.PersonResource;
import it.de.seven.fate.ArchiveDeployment;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
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

import static org.junit.Assert.assertEquals;

/**
 * Created by Mario on 17.02.2016.
 */
@RunWith(Arquillian.class)
public class PersonResourceIT {

    PersonResource sut;

    @Inject
    PersonDAO personDAO;

    @Inject
    PersonBuilder builder;

    @Inject
    UserTransaction utx;

    @PersistenceContext(unitName = "wcmsds")
    EntityManager em;

    @Deployment
    public static WebArchive createDeployment() {
        return ArchiveDeployment.createDeployment();
    }

    @Test
    @InSequence(1)
    public void setUp() throws Exception {

        utx.begin();
        em.joinTransaction();

        Person person = builder.min("mtema");

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
    public void testGetPerson(@ArquillianResource URL baseURL) throws Exception {

        //given

        //when
        ClientRequest request = new ClientRequest(new URL(baseURL, "rest/person").toExternalForm());
        request.accept(MediaType.APPLICATION_JSON);
        request.setHttpMethod("GET");
        ClientResponse<PersonBO> clientResponse = request.get(PersonBO.class);

        //then
        assertEquals(Response.Status.OK.getStatusCode(), clientResponse.getStatus());

        PersonBO person = clientResponse.getEntity();

        Assert.assertNotNull(person);
        Assert.assertEquals("mtema", person.getLdapId());

    }

}
