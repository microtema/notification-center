package de.seven.fate.builder;

import java.util.Date;
import java.util.Random;

public class BooleanRandomValueAdapter implements RandomValueAdapter<Boolean> {

    @Override
    public Boolean randomValue(String propertyName) {
        return new Random().nextBoolean();
    }

    @Override
    public Boolean randomValue(String propertyName, Integer max) {
        return randomValue(propertyName);
    }

}