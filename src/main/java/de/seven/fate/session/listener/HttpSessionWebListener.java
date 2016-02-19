package de.seven.fate.session.listener;

import de.seven.fate.cache.UserCacheService;
import de.seven.fate.cache.enums.AttributeName;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by Mario on 19.02.2016.
 */
@WebListener("Session listener for the application")
public class HttpSessionWebListener implements HttpSessionListener {

    private static final Logger logger = Logger.getLogger(HttpSessionWebListener.class);

    @EJB(name = "UserCacheService")
    private UserCacheService cacheService;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

        HttpSession session = httpSessionEvent.getSession();

        logger.debug("sessionCreated[" + session.getId() + "]");

        Object attribute = session.getAttribute(AttributeName.USER_NAME.name());

        if (attribute == null) {
            return;
        }

        cacheService.setAttribute((String) attribute, AttributeName.USER_NAME, attribute);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

        HttpSession session = httpSessionEvent.getSession();

        logger.debug("sessionDestroyed[" + session.getId() + "]");

        Object attribute = session.getAttribute(AttributeName.USER_NAME.name());

        if (attribute == null) {
            return;
        }

        cacheService.remove((String) attribute);
    }
}

