package de.seven.fate.message.model;

import de.seven.fate.dao.IdAble;
import de.seven.fate.message.listener.MessageListener;
import de.seven.fate.person.enums.MessageType;
import de.seven.fate.person.model.Person;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Mario on 14.02.2016.
 */
@NamedQueries({
        @NamedQuery(name = Message.FIND_BY_PERSON, query = "SELECT m FROM Message m WHERE m.person = :person ORDER BY m.pubDate DESC"),
        @NamedQuery(name = Message.DELETE_BY_PERSON, query = "DELETE FROM Message m WHERE m.person = :person"),
        @NamedQuery(name = Message.FIND_BY_LDAP_ID, query = "SELECT m FROM Message m WHERE m.person.ldapId = :ldapId ORDER BY m.pubDate DESC"),
        @NamedQuery(name = Message.FIND_BY_LDAP_ID_AND_TYPE, query = "SELECT m FROM Message m WHERE m.messageType = :messageType AND m.person.ldapId = :ldapId ORDER BY m.pubDate DESC"),
        @NamedQuery(name = Message.FIND_BY_PUB_DATE, query = "SELECT m FROM Message m WHERE m.pubDate BETWEEN  :startPubDate AND :endPubDate"),
        @NamedQuery(name = Message.UPDATE_TYPE, query = "UPDATE Message m SET m.messageType = :messageType WHERE m.id IN (:ids)")
})
@Entity
@EntityListeners(value = MessageListener.class)
public class Message implements IdAble<Long> {

    public static final String FIND_BY_PERSON = "Message.findByPerson";
    public static final String FIND_BY_LDAP_ID = "Message.findByLdapId";
    public static final String FIND_BY_LDAP_ID_AND_TYPE = "Message.findByLdapIdAndType";
    public static final String FIND_BY_PUB_DATE = "Message.findAllByPubDate";
    public static final String DELETE_BY_PERSON = "Message.deleteAllByPubDate";
    public static final String UPDATE_TYPE = "Message.updateMessageType";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Lob
    @Column(length = 2048)
    private String description;

    @NotNull
    private String image;

    private Date pubDate;

    private String title;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private MessageType messageType;

    @NotNull
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(description, message.description) &&
                Objects.equals(image, message.image) &&
                Objects.equals(title, message.title) &&
                Objects.equals(pubDate, message.pubDate) &&
                messageType == message.messageType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, image, pubDate, messageType);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", pubDate=" + pubDate +
                ", messageType=" + messageType +
                '}';
    }

}
