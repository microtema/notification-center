package de.seven.fate.util;

import java.util.*;

/**
 * Created by Mario on 14.02.2016.
 */
public final class CollectionUtil {

    private CollectionUtil() {

    }

    public static boolean isEmpty(Collection collection) {
        if (collection == null) {
            return true;
        }

        return collection.isEmpty();
    }

    public static boolean isEmpty(Object[] objects) {
        return objects == null || objects.length == 0;
    }

    public static boolean equals(List<?> messages, List<?> other) {

        if (isEmpty(messages)) {
            return isEmpty(other);
        }

        if (isEmpty(other)) {
            return isEmpty(messages);
        }

        return Objects.equals(messages, other);
    }

    public static int hash(Collection<?> collection) {

        if (isEmpty(collection)) {
            return 0;
        }

        return Objects.hash(collection);
    }

    public static <E> List<E> trimToEmpty(List<E> list) {

        if (isEmpty(list)) {
            return Collections.emptyList();
        }

        return list;
    }

    public static <E> Iterator<E> iterator(List<E> list) {
        if (isEmpty(list)) {
            return Collections.emptyIterator();
        }

        return list.iterator();
    }

    public static <E> E random(Collection<E> collection) {
        if (isEmpty(collection)) {
            return null;
        }

        int randomIndex = NumberUtil.random(0, collection.size());
        int index = 0;

        for (E e : collection) {
            if (index++ == randomIndex) {
                return e;
            }
        }

        throw new IllegalStateException("unable to get random :" + index + " element from collection: " + collection);
    }

    public static <E> E random(E[] values) {
        if (isEmpty(values)) {
            return null;
        }

        return random(Arrays.asList(values));
    }


    public static Map<Object, Object> createMap(Object... objects) {
        if (isEmpty(objects)) {
            return Collections.emptyMap();
        }

        if (objects.length % 2 != 0) {
            throw new IllegalArgumentException("args should be even");
        }

        Map<Object, Object> map = new HashMap<>();

        for (int i = 0; i < objects.length; i++) {
            map.put(objects[i], objects[++i]);
        }

        return map;
    }


    public static <E> E first(List<E> collection) {
        if (isEmpty(collection)) {
            return null;
        }

        for (E e : collection) {
            return e;
        }

        throw new IllegalArgumentException("unable to find first element in: " + collection);
    }


}
