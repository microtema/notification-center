package de.seven.fate.util;

import java.util.Collection;

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
}
