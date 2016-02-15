package de.seven.fate.util;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Mario on 15.02.2016.
 */

public class NumberUtilTest {

    NumberUtil sut;

    @Test
    public void testRandom() throws Exception {
        int random = NumberUtil.random(0, 10);

        Assert.assertTrue(random >= 0);
        Assert.assertTrue(random <= 10);
    }

    @Test
    public void testRandomOnMinus() throws Exception {
        int random = NumberUtil.random(-10, -1);

        Assert.assertTrue(random >= -10);
        Assert.assertTrue(random <= -1);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testRandomOnMinGTMax() throws Exception {
        NumberUtil.random(10, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomOnMinEqualMax() throws Exception {
        NumberUtil.random(10, 10);
    }
}
