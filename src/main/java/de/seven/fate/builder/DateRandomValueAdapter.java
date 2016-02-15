package de.seven.fate.builder;

import java.util.Date;

public class DateRandomValueAdapter implements RandomValueAdapter<Date> {

    @Override
    public Date randomValue(String propertyName) {
        return new Date();
    }

    @Override
    public Date randomValue(String propertyName, Integer max) {
        return randomValue(propertyName);
    }

}