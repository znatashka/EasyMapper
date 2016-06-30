package ru.indigo.easymapper.strategy;

import ru.indigo.easymapper.mapper.TypeMapper;

import java.lang.reflect.Field;

public class PrimitiveStrategy implements Strategy {

    private static PrimitiveStrategy instance;

    public static synchronized PrimitiveStrategy getInstance() {
        if (instance == null) {
            synchronized (PrimitiveStrategy.class) {
                if (instance == null) {
                    instance = new PrimitiveStrategy();
                }
            }
        }
        return instance;
    }

    private PrimitiveStrategy() {
    }

    @Override
    public <S> Object getValue(S source, Field sourceField, Field targetField) {
        return TypeMapper.primitive(source, sourceField, targetField);
    }
}
