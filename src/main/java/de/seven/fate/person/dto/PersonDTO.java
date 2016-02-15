package de.seven.fate.person.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Mario on 15.02.2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonDTO {

    @XmlElement(required = true, nillable = false)
    private String ldapId;

    public String getLdapId() {
        return ldapId;
    }

    public void setLdapId(String ldapId) {
        this.ldapId = ldapId;
    }

}
