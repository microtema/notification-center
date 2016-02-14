package de.seven.fate.util;

import de.seven.fate.dao.GenericEntityDAO;
import de.seven.fate.dao.IdAble;
import org.apache.commons.lang3.Validate;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
}
