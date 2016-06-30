package ru.indigo.easymapper.strategy;

import ru.indigo.easymapper.exception.EasyMapperException;

import java.lang.reflect.Field;

public class ObjectStrategy implements Strategy {

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
    public <S> Object getValue(S source, Field sourceField, Field targetField) {
        // TODO: 30.06.2016 метод не реализован
        throw new EasyMapperException("Method not implemented");
    }
}
