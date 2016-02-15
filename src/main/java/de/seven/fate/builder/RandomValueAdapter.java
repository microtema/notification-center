package de.seven.fate.builder;

public interface RandomValueAdapter<T> {
    T randomValue(String propertyName);
    T randomValue(String propertyName, Integer max);
}