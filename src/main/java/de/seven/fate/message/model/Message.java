package de.seven.fate.message.model;

import de.seven.fate.dao.IdAble;
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
        @NamedQuery(name = Message.FIND_BY_PERSON, query = "SELECT m FROM Message m WHERE m.person = :person"),
        @NamedQuery(name = Message.FIND_BY_PUB_DATE, query = "SELECT m FROM Message m WHERE m.pubDate BETWEEN  :startPubDate AND :endPubDate")
})
@Entity
public class Message implements IdAble<Long> {

    public static final String FIND_BY_PERSON = "Message.findByPerson";
    public static final String FIND_BY_PUB_DATE = "Message.findAllByPubDate";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String description;

    @NotNull
    private String image;

    private Date pubDate;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private MessageType messageType;

    @ManyToOne(cascade = CascadeType.ALL)
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
                ", image='" + image + '\'' +
                ", pubDate=" + pubDate +
                ", messageType=" + messageType +
                '}';
    }
}
