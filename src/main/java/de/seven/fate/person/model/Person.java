package de.seven.fate.person.model;

import de.seven.fate.dao.IdAble;
import de.seven.fate.message.model.Message;
import de.seven.fate.util.CollectionUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * Created by Mario on 14.02.2016.
 */
@NamedQueries({
        @NamedQuery(name = Person.FIND_BY_MESSAGE, query = "SELECT p FROM Person p WHERE :message MEMBER OF p.messages")
})
@Entity
public class Person implements IdAble<Long>{

    public static final String FIND_BY_MESSAGE = "Person.findByMessage";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String ldapId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "person", fetch = FetchType.LAZY)
    private List<Message> messages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLdapId() {
        return ldapId;
    }

    public void setLdapId(String name) {
        this.ldapId = name;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(ldapId, person.ldapId) &&
                CollectionUtil.equals(messages, person.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ldapId, CollectionUtil.hash(messages));
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + ldapId + '\'' +
                ", messages=" + messages +
                '}';
    }
}
