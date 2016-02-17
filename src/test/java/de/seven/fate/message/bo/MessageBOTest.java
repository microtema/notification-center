package de.seven.fate.message.bo;

import junit.framework.Assert;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.junit.Test;

/**
 * Created by Mario on 16.02.2016.
 */

public class MessageBOTest {

    MessageBO sut = new MessageBO();

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
