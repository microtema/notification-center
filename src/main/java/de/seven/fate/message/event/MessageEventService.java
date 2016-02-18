package de.seven.fate.message.event;

import de.seven.fate.event.EntityAddEvent;
import de.seven.fate.event.EntityUpdateEvent;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * Created by Mario on 16.02.2016.
 */
@Singleton
@Startup
public class MessageEventService {


    @Inject
    @EntityAddEvent
    private Event<MessageEventData> addEvent;

    @Inject
    @EntityUpdateEvent
    private Event<MessageEventData> updateEvent;


    public void firePersistEvent(String ldapId) {

        addEvent.fire(new MessageEventData(ldapId, null));
    }

    public void fireUpdateEvent(String ldapId) {

        updateEvent.fire(new MessageEventData(ldapId, null));
    }
}
