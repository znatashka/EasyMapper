package ru.indigo.easymapper.strategy;

import ru.indigo.easymapper.exception.EasyMapperException;

import java.lang.reflect.Field;

public class EnumStrategy implements Strategy {

    private static EnumStrategy instance;

    public static synchronized EnumStrategy getInstance() {
        if (instance == null) {
            synchronized (EnumStrategy.class) {
                if (instance == null) {
                    instance = new EnumStrategy();
                }
            }
        }
        return instance;
    }

    private EnumStrategy() {
    }

    @Override
    public <S> Object getValue(S source, Field sourceField, Field targetField) {
        // TODO: 30.06.2016 метод не реализован
        throw new EasyMapperException("Method not implemented");
    }
}
