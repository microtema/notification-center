package de.seven.fate.util;

import org.apache.deltaspike.core.api.provider.BeanManagerProvider;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
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
            e.printStackTrace();

            return getReference(type);
        }
    }


    public static <E> E getReference(Class<E> type) {

        BeanManager bm = BeanManagerProvider.getInstance().getBeanManager();

        Bean<?> bean = bm.resolve(bm.getBeans(type, new java.lang.annotation.Annotation[]{}));

        if (bean == null) {
            throw new IllegalStateException("unable to resolve: " + type + " from BeanManager");
        }

        CreationalContext cc = bm.createCreationalContext(bean);

        return (E) bm.getReference(bean, type, cc);
    }
}
