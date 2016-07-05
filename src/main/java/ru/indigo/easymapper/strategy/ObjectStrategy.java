package ru.indigo.easymapper.strategy;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class ObjectStrategy extends AbstractStrategy {

    private static ObjectStrategy instance;

    public static synchronized ObjectStrategy getInstance() {
        if (instance == null) {
            synchronized (ObjectStrategy.class) {
                if (instance == null) {
                    instance = new ObjectStrategy();
                }
            }
        }
        return instance;
    }

    private ObjectStrategy() {
    }

    @Override
    public <S> Object extractValueFromField(S source, Field sourceField, Field targetField) {
        Object sourceValue = ReflectionUtils.getField(sourceField, source);
        return getValue(sourceValue, targetField.getType());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S, T> Object getValue(S source, T targetClass) {
        return EASY_MAPPER.map(source, (Class<T>) targetClass);
    }
}
