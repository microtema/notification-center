package de.seven.fate.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import javax.persistence.Id;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mario on 14.02.2016.
 */
public final class ClassUtil {

    private ClassUtil() {
    }

    public static <T> T getGenericType(Class<?> classType, int index) {
        ParameterizedType genericSuperclass = (ParameterizedType) classType.getGenericSuperclass();

        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        return (T) actualTypeArguments[index];
    }

    public static String getPropertyNameByAnnotation(Class<?> classType, Class<Id> annotationClass) {
        Validate.notNull(classType, "classType should not be null");
        Validate.notNull(classType, "classType should not be null");

        Field[] fields = classType.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(annotationClass)) {
                return field.getName();
            }
        }

        if (classType.getSuperclass() != null) {
            return getPropertyNameByAnnotation(classType.getSuperclass(), annotationClass);
        }

        return null;
    }

    public static Map<String, Object> getProperties(Object obj) {

        Map<String, Object> describe = new HashMap<>();
        List<String> propertyNames = getPropertyNames(obj.getClass());

        for (String propertyName : propertyNames) {
            try {
                describe.put(propertyName, PropertyUtils.getProperty(obj, propertyName));
            } catch (Exception e) {
                //   e.printStackTrace();
            }
        }

        return describe;
    }

    public static List<String> getPropertyNames(Class objectClass) {

        BeanInfo info = null;
        try {
            info = Introspector.getBeanInfo(objectClass);
        } catch (IntrospectionException e) {
            // e.printStackTrace();
        }
        PropertyDescriptor[] propertyDescriptors = info.getPropertyDescriptors();
        List<String> list = new ArrayList<>();

        try {
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if (StringUtils.equals(propertyDescriptor.getName(), "class")) {
                    continue;
                }
                list.add(propertyDescriptor.getName());
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

        return list;
    }

    public static Object getProperty(Object obj, String propertyName) {

        try {
            return BeanUtils.getProperty(obj, propertyName);
        } catch (Exception e) {
            //  e.printStackTrace();
        }

        return null;
    }

    public static Class<?> getPropertyType(String propertyName, Class<?> objType) {
        Field[] allFields = getAllFields(objType);

        for (Field field : allFields) {
            if (field.getName().equals(propertyName)) {
                return field.getType();
            }
        }

        return null;
    }


    private static Field[] getAllFields(Class<?> type) {
        if (type.getSuperclass() != null) {
            return ArrayUtils.addAll(getAllFields(type.getSuperclass()), type.getDeclaredFields());
        }
        return type.getDeclaredFields();
    }

    public static <D> D createInstance(Class<D> destinationType) {
        try {
            return destinationType.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static int getIndexOfParameter(Annotation[][] annotations, Class<?> annotationType) {

        for (int index = 0; index < annotations.length; index++) {
            Annotation[] subAnnotations = annotations[index];
            for (Annotation annotation : subAnnotations) {
                if (annotation.annotationType() == annotationType) {
                    return index;
                }
            }
        }

        return -1;
    }
}
