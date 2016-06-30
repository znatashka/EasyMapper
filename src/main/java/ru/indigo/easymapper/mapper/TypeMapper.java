package ru.indigo.easymapper.mapper;

import com.google.common.base.Defaults;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

@Slf4j
public class TypeMapper {

    public static <S, T> Object primitive(S source, T target, String fieldName) {
        Field sourceField = ReflectionUtils.findField(source.getClass(), fieldName);
        Field targetField = ReflectionUtils.findField(target.getClass(), fieldName);
        sourceField.setAccessible(true);

        if (sourceField.getType().isPrimitive()) {
            return ReflectionUtils.getField(sourceField, source);
        } else {
            Object value = ReflectionUtils.getField(sourceField, source);
            return value == null ? Defaults.defaultValue(targetField.getType()) : value;
        }
    }

    public static <S, T> Pair<ParameterizedType, Collection> collection(S source, T target, String fieldName) {
        Field sourceField = ReflectionUtils.findField(source.getClass(), fieldName);
        Field targetField = ReflectionUtils.findField(target.getClass(), fieldName);
        sourceField.setAccessible(true);

        Collection sourceCollection = (Collection) ReflectionUtils.getField(sourceField, source);
        ParameterizedType targetGenericType = (ParameterizedType) targetField.getGenericType();

        return new Pair<>(targetGenericType, sourceCollection);
    }
}
