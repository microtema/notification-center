package de.seven.fate.message.resource;

import de.seven.fate.message.bo.MessageBO;
import de.seven.fate.message.facade.MessageFacade;
import de.seven.fate.person.enums.MessageType;
import de.seven.fate.rest.interceptor.UserName;
import de.seven.fate.rest.interceptor.UserNameInterceptor;
import de.seven.fate.util.NumberUtil;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Mario on 16.02.2016.
 */
@Stateless
@Path("/message")
@Produces(MediaType.APPLICATION_JSON)
@Interceptors(value = {UserNameInterceptor.class})
public class MessageResource {

    private static final Logger logger = Logger.getLogger(MessageResource.class);

    @Inject
    private MessageFacade facade;

    @GET
    public Response getMassages(@UserName String userName) {
        logger.debug("get  messages for user: " + userName);

        return Response.ok(facade.findMessagesByPerson(userName)).build();
    }

    @GET
    @Path("/type/{type}")
    public Response getMassagesByType(@UserName String userName, @PathParam("type") MessageType messageType) {
        logger.debug("get messages for user: " + userName + " adn type: " + messageType);

        return Response.ok(facade.findMessagesByPersonAndType(userName, messageType)).build();
    }

    @POST
    public Response updateMassage(MessageBO message) {
        logger.debug("update  message: " + message);

        return Response.ok(facade.updateMassage(message)).build();
    }

    @DELETE
    @Path("/{messageIds}")
    public Response deleteMassage(@PathParam("messageIds") String messageIds) {
        logger.debug("delete  messages: " + messageIds);

        return Response.ok(facade.deleteMassage(NumberUtil.parseLong(messageIds.split(",")))).build();
    }

    @DELETE
    @Path("/all")
    public Response deleteAllCurrentUserMassages(@UserName String userName) {
        logger.debug("delete  all messages for current user: " + userName);

        return Response.ok(facade.deleteMassage(userName)).build();
    }

}
