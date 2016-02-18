package de.seven.fate.message.facade;

import de.seven.fate.message.bo.MessageBO;
import de.seven.fate.message.converter.Message2MessageBOConverter;
import de.seven.fate.message.converter.MessageBO2MessageConverter;
import de.seven.fate.message.model.Message;
import de.seven.fate.message.service.MessageService;
import de.seven.fate.person.enums.MessageType;
import org.apache.commons.lang3.Validate;

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

    @Inject
    private Message2MessageBOConverter message2MessageBOConverter;

    @Inject
    private MessageBO2MessageConverter messageBO2MessageConverter;


    public List<MessageBO> findMessagesByPerson(String ldapId) {
        Validate.notNull(ldapId);

        List<Message> messages = service.findMessagesByPerson(ldapId);

        return message2MessageBOConverter.convertList(messages);
    }

    public MessageBO updateMassage(MessageBO messageBO) {
        Validate.notNull(messageBO);

        Message message = messageBO2MessageConverter.convert(messageBO);

        Message updateMessage = service.updateMessage(message);

        return message2MessageBOConverter.convert(updateMessage);
    }

    public Boolean deleteMassage(List<Long> messageIds) {

        for (Long messageId : messageIds) {
            service.removeMessage(messageId);
        }

        return Boolean.TRUE;
    }

    public Boolean deleteMassage(String personLdapId) {

        service.removeAllMessage(personLdapId);

        return Boolean.TRUE;
    }

    public List<MessageBO> findMessagesByPersonAndType(String userName, MessageType messageType) {

        List<Message> messages = service.findMessagesByPersonAndType(userName, messageType);

        return message2MessageBOConverter.convertList(messages);
    }

    public Boolean markMassageAsRead(List<Long> messageIds) {

        service.markMassage(messageIds, MessageType.READ);

        return Boolean.TRUE;
    }
}
