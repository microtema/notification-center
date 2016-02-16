package de.seven.fate.converter;

/**
 * Created by Mario on 16.02.2016.
 */
public interface Converter<D, O> {

    /**
     * @param orig
     * @return new instance of target type
     */
    D convert(final O orig);

    void update(D dest, final O orig);

    /**
     * @return concrete type for target classes
     */
    Class<D> getDestinationType();

    /**
     * @return concrete type for source classes
     */
    Class<O> getOriginalType();
}
