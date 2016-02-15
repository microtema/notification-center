package de.seven.fate.builder;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StringRandomValueAdapter implements RandomValueAdapter<String> {

    private static Map<String, RandomValueAdapter<String>> randomValueAdapterMap = new HashMap<>();

    static {

        randomValueAdapterMap.put("email", new RandomValueAdapter<String>() {

            @Override
            public String randomValue(String propertyName) {
                return getRandomString(null) + "@" + getRandomString(null) + "." + getRandomString(null).substring(0, 3);
            }

            @Override
            public String randomValue(String propertyName, Integer max) {
                return randomValue(propertyName);
            }
        });
    }

    @Override
    public String randomValue(String propertyName) {
        if (randomValueAdapterMap.containsKey(propertyName)) {
            return randomValueAdapterMap.get(propertyName).randomValue(propertyName);
        }
        return getRandomString(propertyName);
    }

    @Override
    public String randomValue(String propertyName, Integer max) {
        if (randomValueAdapterMap.containsKey(propertyName)) {
            return randomValueAdapterMap.get(propertyName).randomValue(propertyName, max);
        }

        return randomValue(propertyName).substring(0, max);
    }

    private static String getRandomString(String propertyName) {
        String randomStr = UUID.randomUUID().toString();
        if (StringUtils.isNotEmpty(propertyName)) {
            return StringUtils.trimToEmpty(propertyName) + "-" + randomStr;
        }
        return randomStr;
    }

}