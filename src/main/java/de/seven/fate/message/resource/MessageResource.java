package de.seven.fate.message.resource;

import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Mario on 16.02.2016.
 */
@Path("/message")
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

    private static final Logger logger = Logger.getLogger(MessageResource.class);

    @GET
    public Response getUserMassages() {
        logger.debug("get all user Messages");

        return Response.ok().build();
    }

}
