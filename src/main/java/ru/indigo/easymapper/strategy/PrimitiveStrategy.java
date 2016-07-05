package ru.indigo.easymapper.strategy;

import com.google.common.base.Defaults;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class PrimitiveStrategy extends AbstractStrategy {

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
    public <S> Object extractValueFromField(S source, Field sourceField, Field targetField) {
        Object value = ReflectionUtils.getField(sourceField, source);
        if (value != null) {
            return getValue(value, targetField.getClass());
        } else {
            return Defaults.defaultValue(targetField.getType());
        }
    }

    @Override
    public <S, T> Object getValue(S source, T targetClass) {
        return source;
    }
}
