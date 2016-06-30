package ru.indigo.easymapper.strategy;

import com.google.common.base.Defaults;
import org.springframework.util.ReflectionUtils;

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
        if (sourceField.getType().isPrimitive()) {
            return ReflectionUtils.getField(sourceField, source);
        } else {
            Object value = ReflectionUtils.getField(sourceField, source);
            return value == null ? Defaults.defaultValue(targetField.getType()) : value;
        }
    }
}
