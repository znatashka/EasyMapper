package ru.indigo.easymapper.strategy;

import org.springframework.util.ReflectionUtils;
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
    public <S> Object extractValueFromField(S source, Field sourceField, Field targetField) {
        Object sourceValue = ReflectionUtils.getField(sourceField, source);
        if (sourceValue != null) {
            Class<?> targetFieldType = targetField.getType();
            return getValue(sourceValue, targetFieldType);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S, T> Object getValue(S source, T targetClass) {
        if (source.getClass().isEnum() && String.class.equals(targetClass)) {
            // источник -> enum, цель -> String
            return String.valueOf(source);
        } else if (((Class) targetClass).isEnum() &&
                (String.class.equals(source.getClass()) || source.getClass().isEnum())) {
            // источник -> String, цель -> enum или оба enum
            Class<? extends Enum> enumType = (Class<? extends Enum>) targetClass;
            return Enum.valueOf(enumType, String.valueOf(source));
        } else {
            throw new EasyMapperException("Impossible mapping types: " +
                    source.getClass().getName() + " <-> " + ((Class) targetClass).getName());
        }
    }
}
