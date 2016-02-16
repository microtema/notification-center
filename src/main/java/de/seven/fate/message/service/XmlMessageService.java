package de.seven.fate.message.service;

import de.seven.fate.message.converter.MessageDTO2MessageConverter;
import de.seven.fate.message.converter.MessageXmlConverter;
import de.seven.fate.message.dao.MessagesDTO;
import de.seven.fate.message.model.Message;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Mario on 16.02.2016.
 */
@Singleton
public class XmlMessageService {

    private static final Logger logger = Logger.getLogger(XmlMessageService.class);

    @Inject
    private MessageXmlConverter xmlConverter;

    @Inject
    private MessageService service;

    @Inject
    private MessageDTO2MessageConverter converter;


    public void process(String xml) {
        Validate.notEmpty(xml);

        MessagesDTO messagesDTO = getMessagesDTO(xml);

        List<Message> messages = converter.convertList(messagesDTO.getMessages());

        logger.debug("precess " + messages.size() + " messages.");

        service.saveMessage(messages);
    }

    private MessagesDTO getMessagesDTO(String xml) {
        MessagesDTO messagesDTO = null;
        try {
            messagesDTO = xmlConverter.convert(xml);
        } catch (Exception e) {
            logger.warn("Unable to convert xml", e);
            throw new IllegalArgumentException(e);
        }
        return messagesDTO;
    }
}
