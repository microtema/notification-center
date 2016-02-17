package de.seven.fate.person.resource;

import de.seven.fate.person.facade.PersonFacade;
import de.seven.fate.rest.interceptor.UserName;
import de.seven.fate.rest.interceptor.UserNameInterceptor;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Mario on 17.02.2016.
 */
@Stateless
@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Interceptors(value = {UserNameInterceptor.class})
public class PersonResource {

    private static final Logger logger = Logger.getLogger(PersonResource.class);

    @Inject
    private PersonFacade facade;

    @GET
    public Response getPerson(@UserName String userName) {
        logger.debug("get  messages for user: " + userName);

        return Response.ok(facade.getPerson(userName)).build();
    }

}
