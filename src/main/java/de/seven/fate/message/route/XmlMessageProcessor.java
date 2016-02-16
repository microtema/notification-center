package de.seven.fate.message.route;

import de.seven.fate.message.dao.MessagesDTO;
import de.seven.fate.message.service.XmlMessageService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.inject.Inject;

/**
 * Created by Mario on 16.02.2016.
 */
public class XmlMessageProcessor implements Processor {

    @Inject
    private XmlMessageService messageService;

    public void process(Exchange exchange) throws Exception {
        MessagesDTO messagesDTO = exchange.getIn().getBody(MessagesDTO.class);
        messageService.process(messagesDTO.getMessages());
    }
}
