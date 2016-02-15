package de.seven.fate.util;

import java.util.Random;

/**
 * Created by Mario on 14.02.2016.
 */
public final class NumberUtil {

    private NumberUtil() {
    }

    public static int random(int minSize, int maxSize) {
        Random rn = new Random();
        int n = maxSize - minSize + 1;
        int i = rn.nextInt() % n;
        int randomNum = Math.max(minSize, i);

        randomNum = Math.min(minSize, randomNum);

        return randomNum;
    }
}
