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

    @SuppressWarnings("unchecked")
    @Override
    public <S> Object getValue(S source, Field sourceField, Field targetField) {
        Object sourceValue = ReflectionUtils.getField(sourceField, source);
        if (sourceValue != null) {
            if (sourceField.getType().isEnum() && String.class.equals(targetField.getType())) {
                // источник -> enum, цель -> String
                return String.valueOf(sourceValue);
            } else if (targetField.getType().isEnum() &&
                    (String.class.equals(sourceField.getType()) || sourceField.getType().isEnum())) {
                // источник -> String, цель -> enum или оба enum
                Class<? extends Enum> enumType = (Class<? extends Enum>) targetField.getType();
                return Enum.valueOf(enumType, String.valueOf(sourceValue));
            } else {
                throw new EasyMapperException("Impossible mapping types: " +
                        sourceField.getType().getName() + " <-> " + targetField.getType().getName());
            }
        }
        return null;
    }
}
