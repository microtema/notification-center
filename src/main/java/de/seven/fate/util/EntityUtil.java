package de.seven.fate.util;

import de.seven.fate.dao.IdAble;

import javax.persistence.Id;

/**
 * Created by Mario on 14.02.2016.
 */
public final class EntityUtil {

    private EntityUtil() {
    }

    public static String getPrimaryKeyColumnName(Class entityClass) {
        String propertyNameByAnnotation = ClassUtil.getPropertyNameByAnnotation(entityClass, Id.class);

        if (propertyNameByAnnotation == null) {
            throw new IllegalArgumentException("unable to find Primary Key Column Name for entityClass: " + entityClass);
        }
        return propertyNameByAnnotation;
    }
}
