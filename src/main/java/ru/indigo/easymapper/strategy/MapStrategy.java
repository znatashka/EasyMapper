package ru.indigo.easymapper.strategy;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.stream.Collectors;

public class MapStrategy extends AbstractStrategy {

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

    @SuppressWarnings("unchecked")
    @Override
    public <S> Object extractValueFromField(S source, Field sourceField, Field targetField) {
        Map<Object, Object> sourceMap = (Map<Object, Object>) ReflectionUtils.getField(sourceField, source);
        ParameterizedType targetGenericType = (ParameterizedType) targetField.getGenericType();

        return getValue(sourceMap, targetGenericType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S, T> Object getValue(S source, T targetClass) {
        ParameterizedType parameterizedType = (ParameterizedType) targetClass;
        return ((Map) source).entrySet().stream().collect(Collectors.toMap(entry -> {
            Object sourceKey = ((Map.Entry) entry).getKey();
            return EASY_MAPPER.map(sourceKey, (Class) parameterizedType.getActualTypeArguments()[0]);
        }, entry -> {
            Object sourceValue = ((Map.Entry) entry).getValue();
            Type type = parameterizedType.getActualTypeArguments()[1];
            if (type instanceof ParameterizedType) {
                if (Map.class.equals(((ParameterizedType) type).getRawType())) return getValue(sourceValue, type);
                else return CollectionStrategy.getInstance().getValue(sourceValue, type);
            } else {
                return EASY_MAPPER.map(sourceValue, (Class) type);
            }
        }));
    }
}
