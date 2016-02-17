package de.seven.fate.person.builder;

import de.seven.fate.builder.AbstractModelBuilder;
import de.seven.fate.message.builder.MessageBuilder;
import de.seven.fate.person.model.Person;
import de.seven.fate.util.StringUtil;

import javax.inject.Inject;

/**
 * Created by Mario on 14.02.2016.
 */
public class PersonBuilder extends AbstractModelBuilder<Person> {

    private final MessageBuilder messageBuilder;

    @Inject
    public PersonBuilder(MessageBuilder messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    @Override
    public Person min() {

        return min(null);
    }

    public Person min(String ldapId) {
        Person min = super.min();

        min.setId(null);
        min.setLdapId(StringUtil.getNotNull(ldapId, min.getLdapId()));

        return min;
    }

    @Override
    public Person max() {

        return max(null);
    }

    public Person max(String ldapId) {
        Person min = min(ldapId);

        min.setMessages(messageBuilder.list());

        return min;
    }
}
