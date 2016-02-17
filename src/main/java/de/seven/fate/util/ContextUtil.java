package de.seven.fate.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by Mario on 16.02.2016.
 */
public final class ContextUtil {

    private ContextUtil() {
        //do nothing
    }


    public static <E> E getComponent(Class<E> type) {
        try {

            Context ctx = new InitialContext(System.getProperties());

            String name = "java:comp/" + type.getSimpleName();

            return (E) ctx.lookup(name);

        } catch (NamingException e) {
            throw new IllegalStateException("unable to resolve: " + type + " from BeanManager");
        }
    }

}
