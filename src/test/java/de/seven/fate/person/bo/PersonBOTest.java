package de.seven.fate.person.bo;

import junit.framework.Assert;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.junit.Test;

/**
 * Created by Mario on 17.02.2016.
 */
public class PersonBOTest {

    PersonBO sut = new PersonBO();

    @Test
    public void testClassAnnotation() throws Exception {
        Assert.assertTrue(sut.getClass().isAnnotationPresent(JsonIgnoreProperties.class));
    }

    @Test
    public void shouldIgnoreIgnoreUnkonwnProperties() throws Exception {
        JsonIgnoreProperties annotation = sut.getClass().getAnnotation(JsonIgnoreProperties.class);

        Assert.assertNotNull(annotation);
        Assert.assertTrue(annotation.ignoreUnknown());
    }
}
