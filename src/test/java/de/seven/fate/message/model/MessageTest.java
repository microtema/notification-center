package de.seven.fate.message.model;

import de.seven.fate.dao.IdAble;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * Created by Mario on 14.02.2016.
 */

public class MessageTest {

    Message sut = new Message();

    @Test
    public void testImplementation(){
        Assert.assertTrue(IdAble.class.isAssignableFrom(sut.getClass()));
    }

    @Test
    public void isAnnotationEntityPresent(){
        Assert.assertTrue(sut.getClass().isAnnotationPresent(Entity.class));
    }

    @Test
    public void isAnnotationIdPresent() throws Exception{
        Field field = sut.getClass().getDeclaredField("id");
        Assert.assertTrue(field.isAnnotationPresent(Id.class));
    }
}
