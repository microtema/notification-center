package de.seven.fate.xml;

import de.seven.fate.message.converter.MessageXmlConverter;
import de.seven.fate.message.dao.MessagesDTO;
import de.seven.fate.message.dto.MessageDTO;
import junit.framework.Assert;
import org.junit.Test;

import javax.xml.bind.UnmarshalException;
import java.util.List;

/**
 * Created by Mario on 15.02.2016.
 */

public class MessageXmlConverterTest {

    MessageXmlConverter sut = new MessageXmlConverter();

    @Test
    public void testConvert() throws Exception {
        MessagesDTO dto = sut.convert("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<messages>\n" +
                "    <message>\n" +
                "        <person>\n" +
                "            <ldapId>mtema</ldapId>\n" +
                "        </person>\n" +
                "        <description>Description</description>\n" +
                "        <image>image</image>\n" +
                "        <pubDate>2016-02-15T13:22:38.521+01:00</pubDate>\n" +
                "    </message>\n" +
                "    <message>\n" +
                "        <person>\n" +
                "            <ldapId>etema</ldapId>\n" +
                "        </person>\n" +
                "        <description>Other Description</description>\n" +
                "        <image>other image</image>\n" +
                "        <pubDate>2016-01-15T13:22:38.521+01:00</pubDate>\n" +
                "    </message>\n" +
                "</messages>");

        Assert.assertNotNull(dto);

        List<MessageDTO> messages = dto.getMessages();
        Assert.assertNotNull(messages);

        Assert.assertEquals(2, messages.size());

        MessageDTO messageDTO = messages.get(0);
        Assert.assertEquals("Description", messageDTO.getDescription());
        Assert.assertEquals("image", messageDTO.getImage());
        Assert.assertEquals(1455538958521l, messageDTO.getPubDate().getTime());
        Assert.assertEquals("mtema", messageDTO.getPerson().getLdapId());

        messageDTO = messages.get(1);
        Assert.assertEquals("Other Description", messageDTO.getDescription());
        Assert.assertEquals("other image", messageDTO.getImage());
        Assert.assertEquals(1452860558521l, messageDTO.getPubDate().getTime());
        Assert.assertEquals("etema", messageDTO.getPerson().getLdapId());
    }

    @Test(expected = UnmarshalException.class)
    public void shouldNotConvertOnInvalidLdapId() throws Exception {
        sut.convert("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<messages>\n" +
                "    <message>\n" +
                "        <person>\n" +
                //   "            <ldapId>mtema</ldapId>\n" +
                "        </person>\n" +
                "        <description>Description</description>\n" +
                "        <image>image</image>\n" +
                "        <pubDate>2016-02-15T13:22:38.521+01:00</pubDate>\n" +
                "    </message>\n" +
                "</messages>");
    }


    @Test(expected = UnmarshalException.class)
    public void shouldNotConvertOnEmptyPersonLdapId() throws Exception {
        sut.convert("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<messages>\n" +
                "    <message>\n" +
                "        <person>\n" +
                "            <ldapId></ldapId>\n" +
                "        </person>\n" +
                "        <description>Description</description>\n" +
                "        <image>image</image>\n" +
                "        <pubDate>2016-02-15T13:22:38.521+01:00</pubDate>\n" +
                "    </message>\n" +
                "</messages>");
    }

    @Test(expected = UnmarshalException.class)
    public void shouldNotConvertOnInvalidPerson() throws Exception {
        sut.convert("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<messages>\n" +
                "    <message>\n" +
                // "        <person>\n" +
                //   "            <ldapId>mtema</ldapId>\n" +
                // "        </person>\n" +
                "        <description>Description</description>\n" +
                "        <image>image</image>\n" +
                "        <pubDate>2016-02-15T13:22:38.521+01:00</pubDate>\n" +
                "    </message>\n" +
                "</messages>");
    }

    @Test(expected = UnmarshalException.class)
    public void shouldNotConvertOnInvalidDescription() throws Exception {
        sut.convert("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<messages>\n" +
                "    <message>\n" +
                "        <person>\n" +
                "            <ldapId>mtema</ldapId>\n" +
                "        </person>\n" +
                //"        <description>Description</description>\n" +
                "        <image>image</image>\n" +
                "        <pubDate>2016-02-15T13:22:38.521+01:00</pubDate>\n" +
                "    </message>\n" +
                "</messages>");
    }

    @Test(expected = UnmarshalException.class)
    public void shouldNotConvertOnInvalidImage() throws Exception {
        sut.convert("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<messages>\n" +
                "    <message>\n" +
                "        <person>\n" +
                "            <ldapId>mtema</ldapId>\n" +
                "        </person>\n" +
                "        <description>Description</description>\n" +
                //"        <image>image</image>\n" +
                "        <pubDate>2016-02-15T13:22:38.521+01:00</pubDate>\n" +
                "    </message>\n" +
                "</messages>");
    }

    @Test
    public void shouldConvertOnInvalidDate() throws Exception {
        sut.convert("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<messages>\n" +
                "    <message>\n" +
                "        <person>\n" +
                "            <ldapId>mtema</ldapId>\n" +
                "        </person>\n" +
                "        <description>Description</description>\n" +
                "        <image>image</image>\n" +
                //"        <pubDate>2016-02-15T13:22:38.521+01:00</pubDate>\n" +
                "    </message>\n" +
                "</messages>");
    }
}
