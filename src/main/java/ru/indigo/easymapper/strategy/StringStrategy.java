package ru.indigo.easymapper.strategy;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class StringStrategy implements Strategy {

    private static StringStrategy instance;

    public static synchronized StringStrategy getInstance() {
        if (instance == null) {
            synchronized (StringStrategy.class) {
                if (instance == null) {
                    instance = new StringStrategy();
                }
            }
        }
        return instance;
    }

    private StringStrategy() {
    }

    @Override
    public <S> Object getValue(S source, Field sourceField, Field targetField) {
        return ReflectionUtils.getField(sourceField, source);
    }
}
