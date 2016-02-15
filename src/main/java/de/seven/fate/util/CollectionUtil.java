package de.seven.fate.util;

import de.seven.fate.message.model.Message;

import java.util.*;

/**
 * Created by Mario on 14.02.2016.
 */
public final class CollectionUtil {

    private CollectionUtil (){

    }

    public static boolean isEmpty(Collection collection){
        if(collection == null){
            return true;
        }

        return collection.isEmpty();
    }

    public static boolean equals(List<?> messages, List<?> other) {

        if(isEmpty(messages)){
            return isEmpty(other);
        }

        if(isEmpty(other)){
            return isEmpty(messages);
        }

        return Objects.equals(messages, other);
    }

    public static int hash(List<Message> messages) {

        if(isEmpty(messages)){
            return 0;
        }

        return Objects.hash(messages);
    }

    public static <E> List<E> trimToEmpty(List<E> list) {

        if(isEmpty(list)){
            return Collections.emptyList();
        }

        return list;
    }

    public static <E> Iterator<E> iterator(List<E> list) {
        if(isEmpty(list)){
            return Collections.emptyIterator();
        }

        return list.iterator();
    }
}
