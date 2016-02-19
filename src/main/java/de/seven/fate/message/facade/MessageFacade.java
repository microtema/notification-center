package de.seven.fate.message.facade;

import de.seven.fate.cache.UserCacheService;
import de.seven.fate.cache.enums.AttributeName;
import de.seven.fate.message.bo.MessageBO;
import de.seven.fate.message.converter.Message2MessageBOConverter;
import de.seven.fate.message.converter.MessageBO2MessageConverter;
import de.seven.fate.message.model.Message;
import de.seven.fate.message.service.MessageService;
import de.seven.fate.person.enums.MessageType;
import org.apache.commons.lang3.Validate;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Mario on 17.02.2016.
 */
@Stateless
public class MessageFacade {

    @Inject
    private MessageService service;

    @EJB(name = "UserCacheService")
    private UserCacheService cacheService;

    @Inject
    private Message2MessageBOConverter message2MessageBOConverter;

    @Inject
    private MessageBO2MessageConverter messageBO2MessageConverter;

    /**
     * first lookup in user cache than in DB
     *
     * @param ldapId
     * @return messages for given ldapId
     */
    public List<MessageBO> findMessagesByPerson(String ldapId) {
        Validate.notNull(ldapId);

        List<MessageBO> messageBOList = cacheService.getAttribute(ldapId, AttributeName.MESSAGES);

        if (messageBOList == null) {
            List<Message> messages = service.findMessagesByPerson(ldapId);

            messageBOList = message2MessageBOConverter.convertList(messages);
            cacheService.setAttribute(ldapId, AttributeName.MESSAGES, messageBOList);
        }

        return messageBOList;
    }

    public MessageBO updateMassage(String ldapId, MessageBO messageBO) {
        Validate.notNull(messageBO);

        cacheService.removeAttributes(ldapId);

        Message message = messageBO2MessageConverter.convert(messageBO);

        Message updateMessage = service.updateMessage(message);

        return message2MessageBOConverter.convert(updateMessage);
    }

    public Boolean deleteMassage(String ldapId, List<Long> messageIds) {

        for (Long messageId : messageIds) {
            service.removeMessage(messageId);
        }

        cacheService.removeAttributes(ldapId);

        return Boolean.TRUE;
    }

    public Boolean deleteMassage(String personLdapId) {

        service.removeAllMessage(personLdapId);

        cacheService.removeAttributes(personLdapId);

        return Boolean.TRUE;
    }

    public List<MessageBO> findMessagesByPersonAndType(String userName, MessageType messageType) {

        List<Message> messages = service.findMessagesByPersonAndType(userName, messageType);

        return message2MessageBOConverter.convertList(messages);
    }

    public Boolean markMassageAsRead(String personLdapId, List<Long> messageIds) {

        service.markMassage(messageIds, MessageType.READ);

        cacheService.removeAttributes(personLdapId);

        return Boolean.TRUE;
    }
}
