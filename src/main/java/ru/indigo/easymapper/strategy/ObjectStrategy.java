package ru.indigo.easymapper.strategy;

import org.springframework.util.ReflectionUtils;
import ru.indigo.easymapper.creator.ObjectCreator;

import java.lang.reflect.Field;
import java.util.Arrays;

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
    public <S> Object extractValueFromField(S source, Field sourceField, Field targetField) {
        Object target = ObjectCreator.createTarget(targetField.getType());

        Object sourceValue = ReflectionUtils.getField(sourceField, source);
        if (sourceValue != null) {
            target = mapObject(target, sourceValue);
        }
        return target;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S, T> Object getValue(S source, T targetClass) {
        if (source != null) {
            Object target = ObjectCreator.createTarget((Class<T>) targetClass);
            return mapObject(target, source);
        }
        return null;
    }

    private Object mapObject(Object target, Object source) {
        Field[] declaredSourceFields = source.getClass().getDeclaredFields();
        Field[] declaredTargetFields = target.getClass().getDeclaredFields();

        Arrays.stream(declaredSourceFields).forEach(declaredSourceField -> Arrays.stream(declaredTargetFields).forEach(declaredTargetField -> {
            if (declaredSourceField.getName().equals(declaredTargetField.getName())) {
                declaredSourceField.setAccessible(true);
                declaredTargetField.setAccessible(true);

                Object field = ReflectionUtils.getField(declaredSourceField, source);
                Object value;
                if (declaredTargetField.getType().isPrimitive()) {
                    value = PrimitiveStrategy.getInstance().extractValueFromField(source, declaredSourceField, declaredTargetField);
                } else {
                    value = EASY_MAPPER.map(field, declaredTargetField.getType());
                }
                ReflectionUtils.setField(declaredTargetField, target, value);
            }
        }));
        return target;
    }
}
