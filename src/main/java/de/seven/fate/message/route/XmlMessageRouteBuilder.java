package de.seven.fate.message.route;

import de.seven.fate.message.dao.MessagesDTO;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.impl.DefaultCamelContext;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;

/**
 * Created by Mario on 16.02.2016.
 */
@Singleton
@Startup
public class XmlMessageRouteBuilder extends RouteBuilder {

    private final JaxbDataFormat messagesData = new JaxbDataFormat();
    private final CamelContext camelContext = new DefaultCamelContext();

    @Resource(lookup = "java:global/IMPORT_PATH")
    private String uri;

    @Inject
    private XmlMessageProcessor messageProcessor;


    @PostConstruct
    private void init() throws Exception {

        JAXBContext jaxbContext = JAXBContext.newInstance(MessagesDTO.class);

        messagesData.setContext(jaxbContext);

        camelContext.addRoutes(this);

        camelContext.start();
    }

    @Override
    public void configure() {
        from(uri).unmarshal(messagesData).process(messageProcessor);
    }

}
