package de.seven.fate.xml;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.StringReader;

/**
 * Created by Mario on 15.02.2016.
 */
public abstract class BaseXmlConverter<T> implements XmlConverter<T>, ErrorHandler {

    private static final Logger logger = Logger.getLogger(BaseXmlConverter.class);


    @Override
    public <T> T convert(String xml) throws JAXBException, SAXException {
        Validate.notNull(xml, "xml should not be null");

        JAXBContext jaxbContext = JAXBContext.newInstance(getClassesToBeBound());

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        if (validateSchema()) {
            logger.debug("validate xml");
            validateSchema(unmarshaller);
        }

        return (T) unmarshaller.unmarshal(new StringReader(xml));
    }


    private void validateSchema(Unmarshaller unmarshaller) throws SAXException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(getSchemaFile());
        Validator validator = schema.newValidator();
        validator.setErrorHandler(this);

        unmarshaller.setSchema(schema);
    }

    public abstract Class[] getClassesToBeBound();

    public abstract File getSchemaFile();

    public abstract boolean validateSchema();


    @Override
    public void warning(SAXParseException exception) throws SAXException {
        logger.warn("SAX Parse Exception", exception);
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        logger.error("SAX Parse Exception", exception);
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        logger.fatal("SAX Parse Exception", exception);
    }
}
