package de.seven.fate.message.service;

import de.seven.fate.message.converter.MessageDTO2MessageConverter;
import de.seven.fate.message.dto.MessageDTO;
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
    private MessageService service;

    @Inject
    private MessageDTO2MessageConverter converter;


    public void process(List<MessageDTO> messageDTOList) {
        Validate.notNull(messageDTOList);

        logger.debug("precess " + messageDTOList.size() + " DTO messages.");

        List<Message> messages = converter.convertList(messageDTOList);

        logger.debug("convert " + messages.size() + " messages.");

        service.saveMessage(messages);
    }

}
