package de.seven.fate.message.bo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Mario on 16.02.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageBO implements Serializable {

    private Long id;

    private String description;

    private String image;

    private Date pubDate;

    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageBO messageBO = (MessageBO) o;
        return Objects.equals(id, messageBO.id) &&
                Objects.equals(description, messageBO.description) &&
                Objects.equals(image, messageBO.image) &&
                Objects.equals(pubDate, messageBO.pubDate) &&
                Objects.equals(type, messageBO.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, image, pubDate, type);
    }
}
