package de.seven.fate.person.builder;

import de.seven.fate.builder.AbstractModelBuilder;
import de.seven.fate.message.builder.MessageBuilder;
import de.seven.fate.person.model.Person;

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
        Person min = super.min();
        min.setId(null);

        return min;
    }

    @Override
    public Person max() {
        Person min = min();

        min.setMessages(messageBuilder.list());

        return min;
    }
}
