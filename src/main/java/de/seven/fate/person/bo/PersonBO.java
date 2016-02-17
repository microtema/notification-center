package de.seven.fate.person.bo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Mario on 17.02.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonBO implements Serializable {

    private String ldapId;

    public String getLdapId() {
        return ldapId;
    }

    public void setLdapId(String ldapId) {
        this.ldapId = ldapId;
    }
}
