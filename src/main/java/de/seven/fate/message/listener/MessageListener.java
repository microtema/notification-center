package de.seven.fate.message.listener;

import de.seven.fate.message.event.MessageEventService;
import de.seven.fate.message.model.Message;
import de.seven.fate.util.ContextUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

/**
 * Created by Mario on 16.02.2016.
 */
public class MessageListener {

    @PostUpdate
    void postUpdate(Object object) {

        if (object instanceof Message) {
            getEventService().fireUpdateEvent(((Message) object).getPerson().getLdapId());
        }
    }

    @PostPersist
    void postPersist(Object object) {

        if (object instanceof Message) {
            getEventService().firePersistEvent(((Message) object).getPerson().getLdapId());
        }
    }

    private MessageEventService getEventService() {
        return ContextUtil.getComponent(MessageEventService.class);
    }

}
