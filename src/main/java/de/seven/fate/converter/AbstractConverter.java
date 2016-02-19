package de.seven.fate.converter;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import java.util.*;

import static de.seven.fate.util.ClassUtil.getGenericType;

/**
 * Created by Mario on 16.02.2016.
 */
public abstract class AbstractConverter<D, O> implements Converter<D, O> {

    private static final Logger logger = Logger.getLogger(AbstractConverter.class);


    @Override
    public D convert(final O orig) {
        if (orig == null) {
            return null;
        }

        D dest = de.seven.fate.util.ClassUtil.createInstance(getDestinationType());

        update(dest, orig);

        return dest;
    }


    public List<D> convertList(final List<O> orig) {
        if (orig == null) {
            return Collections.emptyList();
        }

        List<D> dest = new ArrayList<>();
        for (O o : orig) {
            dest.add(convert(o));
        }

        return dest;
    }


    public Set<D> convertSet(final Set<O> orig) {
        if (orig == null) {
            return Collections.emptySet();
        }

        Set<D> dest = new HashSet<>();
        for (O o : orig) {
            dest.add(convert(o));
        }

        return dest;
    }


    @Override
    public void update(D dest, final O orig) {
        Validate.notNull(dest, "dest should not be null");
        if (orig == null) {
            return;
        }

        try {
            PropertyUtils.copyProperties(dest, orig);
        } catch (Exception any) {
            logger.warn(String.format("Unable to copy Properties from: %s to: %s", orig, dest), any);
        }
    }


    @Override
    public Class<O> getOriginalType() {
        return getGenericType(getClass(), 1);
    }
}
