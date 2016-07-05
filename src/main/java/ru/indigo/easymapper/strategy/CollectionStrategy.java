package ru.indigo.easymapper.strategy;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionStrategy extends AbstractStrategy {

    private static CollectionStrategy instance;

    public static synchronized CollectionStrategy getInstance() {
        if (instance == null) {
            synchronized (CollectionStrategy.class) {
                if (instance == null) {
                    instance = new CollectionStrategy();
                }
            }
        }
        return instance;
    }

    private CollectionStrategy() {
    }

    @Override
    public <S> Object extractValueFromField(S source, Field sourceField, Field targetField) {
        Collection sourceCollection = (Collection) ReflectionUtils.getField(sourceField, source);
        ParameterizedType targetGenericType = (ParameterizedType) targetField.getGenericType();

        return getValue(sourceCollection, targetGenericType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S, T> Object getValue(S source, T targetClass) {
        ParameterizedType targetGenericType = (ParameterizedType) targetClass;
        return ((Collection) source).stream()
                .map(sourceItem -> EASY_MAPPER.map(sourceItem, (Class) targetGenericType.getActualTypeArguments()[0]))
                .collect(targetGenericType.getRawType().equals(List.class) ? Collectors.toList() : Collectors.toSet());
    }
}
