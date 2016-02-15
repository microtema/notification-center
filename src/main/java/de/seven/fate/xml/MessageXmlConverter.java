package de.seven.fate.xml;

import de.seven.fate.message.dao.MessagesDTO;
import de.seven.fate.message.dto.MessageDTO;

import java.io.File;

/**
 * Created by mtema on 27.02.2015.
 */
public class MessageXmlConverter extends BaseXmlConverter<MessagesDTO> {

    private final Class[] classes = {MessageDTO.class, MessagesDTO.class};

    private final File schemaFile = new File(MessageXmlConverter.class.getClassLoader().getResource("message-schema.xsd").getFile());

    private boolean validateSchema = true;

    @Override
    public Class[] getClassesToBeBound() {
        return classes;
    }

    @Override
    public File getSchemaFile() {
        return schemaFile;
    }

    @Override
    public boolean validateSchema() {
        return validateSchema;
    }

}
