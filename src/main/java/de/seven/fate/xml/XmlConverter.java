package de.seven.fate.xml;

import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;

/**
 * Created by Mario on 15.02.2016.
 */
public interface XmlConverter<T> {
    <T> T convert(String xml) throws JAXBException, SAXException;
}
