package de.seven.fate.util;

import java.util.Random;

/**
 * Created by Mario on 14.02.2016.
 */
public final class NumberUtil {

    private NumberUtil() {
    }

    public static int random(int minSize, int maxSize) {
        if (minSize > maxSize) {
            throw new IllegalArgumentException("min should be less than max");
        }

        if (minSize == maxSize) {
            throw new IllegalArgumentException("min should not be equal to max");
        }

        Random rn = new Random();
        int n = maxSize - minSize + 1;
        int i = rn.nextInt() % n;
        int randomNum = Math.max(minSize, i);

        randomNum = Math.min(minSize, randomNum);

        return randomNum;
    }
}
