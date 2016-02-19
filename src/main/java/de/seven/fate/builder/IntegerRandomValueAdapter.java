package de.seven.fate.builder;

import de.seven.fate.util.NumberUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class IntegerRandomValueAdapter implements RandomValueAdapter<Integer> {

    private static final int MIN_SIZE = 1;
    private static final int MAX_SIZE = 10;

    private Map<String, RandomValueAdapter<Integer>> randomValueAdapterMap = new HashMap<>();
    private AtomicInteger position = new AtomicInteger(0);
    private AtomicInteger id = new AtomicInteger(1000);

    public IntegerRandomValueAdapter() {
        randomValueAdapterMap.put("id", new RandomValueAdapter<Integer>() {

            @Override
            public Integer randomValue(String propertyName) {
                return id.getAndIncrement();
            }

            @Override
            public Integer randomValue(String propertyName, Integer max) {
               return Math.min(randomValue(propertyName), max);
            }
        });

        randomValueAdapterMap.put("position", new RandomValueAdapter<Integer>() {

            @Override
            public Integer randomValue(String propertyName) {
                return position.getAndIncrement();
            }

            @Override
            public Integer randomValue(String propertyName, Integer max) {
                return Math.min(randomValue(propertyName), max);
            }
        });
    }

    @Override
    public Integer randomValue(String propertyName) {
        if(randomValueAdapterMap.containsKey(propertyName)){
            return randomValueAdapterMap.get(propertyName).randomValue(propertyName);
        }
        return randomValue();
    }

    @Override
    public Integer randomValue(String propertyName, Integer max) {
        if(randomValueAdapterMap.containsKey(propertyName)){
            return randomValueAdapterMap.get(propertyName).randomValue(propertyName, max);
        }
        return randomValue(max);
    }

    public Integer randomValue() {
        return randomValue(MAX_SIZE);
    }

    protected Integer randomValue(int maxSize) {
        return randomValue(MIN_SIZE, maxSize);
    }

    protected Integer randomValue(int minSize, int maxSize) {

        return NumberUtil.random(minSize, maxSize);
    }
}