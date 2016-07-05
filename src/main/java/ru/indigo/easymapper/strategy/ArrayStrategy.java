package ru.indigo.easymapper.strategy;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class ArrayStrategy implements Strategy {

    private static ArrayStrategy instance;

    public static synchronized ArrayStrategy getInstance() {
        if (instance == null) {
            synchronized (ArrayStrategy.class) {
                if (instance == null) {
                    instance = new ArrayStrategy();
                }
            }
        }
        return instance;
    }

    private ArrayStrategy() {
    }

    @Override
    public <S> Object extractValueFromField(S source, Field sourceField, Field targetField) {
        Object[] sourceArray = (Object[]) ReflectionUtils.getField(sourceField, source);
        Class targetGenericClass = targetField.getType().getComponentType();

        return getValue(sourceArray, targetGenericClass);
    }

    @Override
    public <S, T> Object getValue(S source, T targetClass) {
        Object array = null;
        if (source != null) {
            array = Array.newInstance((Class) targetClass, ((Object[]) source).length);
            for (int i = 0; i < ((Object[]) source).length; i++) {
                Array.set(array, i, EASY_MAPPER.map(((Object[]) source)[i], (Class) targetClass));
            }
        }
        return array;
    }
}
