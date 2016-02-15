package de.seven.fate.builder;

import de.seven.fate.util.ClassUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.util.BeanUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RandomValueAdapterFactory {

    private static Map<Class<?>, RandomValueAdapter<?>> randomValueAdapterMap = new HashMap<>();

    static {
        randomValueAdapterMap.put(String.class, new StringRandomValueAdapter());
        randomValueAdapterMap.put(Integer.class, new IntegerRandomValueAdapter());
        randomValueAdapterMap.put(Long.class, new LongRandomValueAdapter());
        randomValueAdapterMap.put(Date.class, new DateRandomValueAdapter());
        randomValueAdapterMap.put(Boolean.class, new BooleanRandomValueAdapter());
    }

    public static <T> T randomValue(String propertyName, Class<T> propertyType, Object defaultValue) {
        return randomValue(propertyName, propertyType, defaultValue, null);
    }

    public static <T> T randomValue(String propertyName, Class<T> propertyType, Object defaultValue, Integer maxSize) {
        if (randomValueAdapterMap.containsKey(propertyType)) {
            if (maxSize != null) {
                return (T) randomValueAdapterMap.get(propertyType).randomValue(propertyName, maxSize);
            }
            return (T) randomValueAdapterMap.get(propertyType).randomValue(propertyName);
        }
        return (T) defaultValue;
    }

    public static <T> T randomValue(Class<T> propertyType) {
        return randomValue(null, propertyType, null);
    }

    public static void initPropertiesWithRandomValues(Object obj) {
        Map<String, Object> properties = ClassUtil.getProperties(obj);

        for (String propertyName : properties.keySet()) {
            if (StringUtils.equalsIgnoreCase(propertyName, "class")) {
                continue;
            }

            Class<?> propertyType = ClassUtil.getPropertyType(propertyName, obj.getClass());
            Object defaultValue = properties.get(propertyName);
            properties.put(propertyName, randomValue(propertyName, propertyType, defaultValue));
        }

        try {
            BeanUtils.populate(obj, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}