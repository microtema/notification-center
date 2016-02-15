package de.seven.fate.builder;

import de.seven.fate.util.ClassUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Mario on 14.02.2016.
 */
public abstract class AbstractModelBuilder<T> implements ModelBuilder<T> {


    @Override
    public T min() {
        T model = createNewInstance();
        initPropertiesWithRandomValues(model);
        return model;
    }

    @Override
    public T max() {
        return min();
    }

    public List<T> list() {
        List<T> list = new ArrayList<>();

        int maxListSize = randomInt();

        while (list.size() < maxListSize){
            list.add(min());
        }

        return list;
    }

    protected int randomInt(){
        return new Random().nextInt(10);
    }

    protected boolean randomBoolean(){
        return new Random().nextBoolean();
    }


    private T createNewInstance() {
        Class<T> genericType = ClassUtil.getGenericType(getClass(), 0);

        T instance;

        try {
            instance = genericType.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return instance;
    }

    private void initPropertiesWithRandomValues(T model) {
        RandomValueAdapterFactory.initPropertiesWithRandomValues(model);

        try {
            BeanUtils.setProperty(model, "id", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
