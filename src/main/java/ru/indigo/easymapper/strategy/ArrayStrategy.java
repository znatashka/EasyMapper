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
    public <S> Object getValue(S source, Field sourceField, Field targetField) {
        Object[] sourceArray = (Object[]) ReflectionUtils.getField(sourceField, source);
        Class targetGenericClass = targetField.getType().getComponentType();

        Object array = null;
        if (sourceArray != null) {
            array = Array.newInstance(targetGenericClass, sourceArray.length);
            for (int i = 0; i < sourceArray.length; i++) {
                Array.set(array, i, EASY_MAPPER.map(sourceArray[i], targetGenericClass));
            }
        }
        return array;
    }
}
