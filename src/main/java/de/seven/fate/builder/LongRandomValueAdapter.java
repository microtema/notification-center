package de.seven.fate.builder;

import de.seven.fate.util.NumberUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class LongRandomValueAdapter implements RandomValueAdapter<Long> {

    private static final int MIN_SIZE = 1;
    private static final int MAX_SIZE = 10;

    private Map<String, RandomValueAdapter<Long>> randomValueAdapterMap = new HashMap<>();
    private AtomicInteger position = new AtomicInteger(0);
    private AtomicInteger id = new AtomicInteger(1000);
    private AtomicInteger fsId = new AtomicInteger(1000);

    public LongRandomValueAdapter() {

        randomValueAdapterMap.put("id", new RandomValueAdapter<Long>() {

            @Override
            public Long randomValue(String propertyName) {
                return new Long( id.getAndIncrement());
            }

            @Override
            public Long randomValue(String propertyName, Integer max) {
               return Math.min(randomValue(propertyName), max);
            }
        });
    }



    @Override
    public Long randomValue(String propertyName) {
        if(randomValueAdapterMap.containsKey(propertyName)){
            return randomValueAdapterMap.get(propertyName).randomValue(propertyName);
        }
        return randomValue();
    }

    @Override
    public Long randomValue(String propertyName, Integer max) {
        if(randomValueAdapterMap.containsKey(propertyName)){
            return randomValueAdapterMap.get(propertyName).randomValue(propertyName, max);
        }
        return randomValue(max);
    }

    public Long randomValue() {
        return randomValue(MAX_SIZE);
    }

    protected Long randomValue(int maxSize) {
        return randomValue(MIN_SIZE, maxSize);
    }

    protected Long randomValue(int minSize, int maxSize) {
        return new Long(NumberUtil.random(minSize, maxSize));
    }
}