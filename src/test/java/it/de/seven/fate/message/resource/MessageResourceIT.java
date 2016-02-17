package it.de.seven.fate.message.resource;

import it.de.seven.fate.ArchiveDeployment;
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

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mario on 16.02.2016.
 */
@RunWith(Arquillian.class)
public class MessageResourceIT {

    @Deployment(testable = true)
    public static WebArchive createDeployment() {

        return ArchiveDeployment.createDeployment();
    }

    @Test
    @InSequence(1)
    public void setUp() throws Exception {

    }

    @Test
    @InSequence(100)
    public void tearDown() throws Exception {

    }


    @Test
    @RunAsClient
    @InSequence(1)
    public void testGetUserMassages(@ArquillianResource URL baseURL) throws Exception {

        //given

        //when
        ClientRequest request = new ClientRequest(new URL(baseURL, "rest/message").toExternalForm());
        request.accept(MediaType.APPLICATION_JSON);
        request.setHttpMethod("GET");
        ClientResponse clientResponse = request.get();

        //then
        assertEquals(Response.Status.OK.getStatusCode(), clientResponse.getStatus());

    }
}
