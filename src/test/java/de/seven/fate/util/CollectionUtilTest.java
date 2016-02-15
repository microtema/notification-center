package de.seven.fate.util;

import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Mario on 15.02.2016.
 */
public class CollectionUtilTest {

    CollectionUtil sut;

    @Test
    public void testIsEmptyOnNullCollection()  {
        Assert.assertTrue(CollectionUtil.isEmpty((Collection) null));
    }

    @Test
    public void testIsEmptyOnEmptyList() {
        Assert.assertTrue(CollectionUtil.isEmpty(Collections.emptyList()));
    }

    @Test
    public void testIsEmptyOnEmptySet()  {
        Assert.assertTrue(CollectionUtil.isEmpty(Collections.emptySet()));
    }

    @Test
    public void testIsEmptyOnList()  {
        Assert.assertFalse(CollectionUtil.isEmpty(Arrays.asList("Foo")));
    }

    @Test
    public void testIsEmptyOnNullArray() {
        Assert.assertTrue(CollectionUtil.isEmpty((Object[]) null));
    }

    @Test
    public void testIsEmptyOnArray()  {
        Assert.assertFalse(CollectionUtil.isEmpty(new String[]{"Foo"}));
    }

    @Test
    public void testEquals() {
        Assert.assertTrue(CollectionUtil.equals(null, Collections.emptyList()));
    }

    @Test
    public void testEqualsOnList()  {
        Assert.assertTrue(CollectionUtil.equals(Arrays.asList("foo"), Arrays.asList("foo")));
    }

    @Test
    public void testHashOnNull()  {
        Assert.assertEquals(0, CollectionUtil.hash(null));
    }

    @Test
    public void testHash() {

        List<String> collection = Arrays.asList("Bar");

        Assert.assertEquals(Objects.hash(collection), CollectionUtil.hash(collection));
    }

    @Test
    public void testTrimToEmptyOnNull() throws Exception {
        Assert.assertTrue(CollectionUtil.trimToEmpty(null).isEmpty());
    }

    @Test
    public void testTrimToEmpty() {
        Assert.assertFalse(CollectionUtil.trimToEmpty(Arrays.asList("Bar")).isEmpty());
    }

    @Test
    public void testIteratorOnNull() {
        Assert.assertNotNull(CollectionUtil.iterator(null));
    }

    @Test
    public void testIterator() throws Exception {
        Assert.assertNotNull(CollectionUtil.iterator(Arrays.asList(new Integer(1))));
    }

    @Test
    public void testRandomOnNull() {
        Assert.assertNull(CollectionUtil.random(null));
    }

    @Test
    public void testRandom() {
        Assert.assertNotNull(CollectionUtil.random(Arrays.asList(new Boolean(true), new Boolean(false))));
    }

    @Test
    public void testCreateMapOnNull() {
        Assert.assertNotNull(CollectionUtil.createMap(null));
    }

    @Test
    public void testCreateMap(){
        Map<Object, Object> map = CollectionUtil.createMap("key", new Object());
        Assert.assertNotNull(map);
        Assert.assertNotNull(map.get("key"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateMapOnOdd() throws Exception {
        CollectionUtil.createMap("key", "bar", "baz");
    }

    public void testFirst() {
       Assert.assertEquals("key" ,CollectionUtil.first(Arrays.asList("key", "bar", "baz")));
    }
}
