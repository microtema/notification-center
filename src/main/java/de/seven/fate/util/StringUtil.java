package de.seven.fate.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * Created by Mario on 17.02.2016.
 */
public final class StringUtil {

    public static String getNotNull(String... strings) {
        Validate.notNull(strings);

        for (String str : strings) {
            if (StringUtils.isNotEmpty(str)) {
                return str;
            }
        }

        return null;
    }
}
