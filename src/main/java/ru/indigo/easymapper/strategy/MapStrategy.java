package ru.indigo.easymapper.strategy;

import ru.indigo.easymapper.exception.EasyMapperException;

import java.lang.reflect.Field;

public class MapStrategy implements Strategy {

    private static MapStrategy instance;

    public static synchronized MapStrategy getInstance() {
        if (instance == null) {
            synchronized (MapStrategy.class) {
                if (instance == null) {
                    instance = new MapStrategy();
                }
            }
        }
        return instance;
    }

    private MapStrategy() {
    }

    @Override
    public <S> Object getValue(S source, Field sourceField, Field targetField) {
        // TODO: 30.06.2016 метод не реализован
        throw new EasyMapperException("Method not implemented");
    }
}
