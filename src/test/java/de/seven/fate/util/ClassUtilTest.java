package de.seven.fate.util;

import de.seven.fate.message.resource.MessageResource;
import de.seven.fate.rest.interceptor.UserName;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Mario on 17.02.2016.
 */
public class ClassUtilTest {
    ClassUtil sut;

    @Test
    public void testGetIndexOfParameter() throws Exception {

        int indexOfParameter = ClassUtil.getIndexOfParameter(MessageResource.class.getDeclaredMethod("getMassages", String.class).getParameterAnnotations(), UserName.class);

        Assert.assertEquals(0, indexOfParameter);
    }
}
