package ru.indigo.easymapper;

import javafx.util.Pair;
import org.springframework.util.ReflectionUtils;
import ru.indigo.easymapper.creator.ObjectCreator;
import ru.indigo.easymapper.mapper.TypeMapper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility for easy mapping java objects
 * <p>
 * Created by Nataliia_Zolotovitckaia on 29.06.2016.
 */
public class EasyMapper {

    public <T, S> T map(S source, Class<T> targetClass) {
        T target = ObjectCreator.createTarget(targetClass);

        Field[] sourceFields = source.getClass().getDeclaredFields();
        for (Field sourceField : sourceFields) {
            Field targetField = ReflectionUtils.findField(targetClass, sourceField.getName());
            if (targetField != null) {
                targetField.setAccessible(true);

                Object value = null;

                if (!Modifier.isStatic(sourceField.getModifiers())) {
                    if (sourceField.getType().isPrimitive() || targetField.getType().isPrimitive()) {
                        value = TypeMapper.primitive(source, target, sourceField.getName());
                    } else if (Collection.class.isAssignableFrom(sourceField.getType()) && Collection.class.isAssignableFrom(targetField.getType())) {
                        value = createCollectionValue(TypeMapper.collection(source, target, sourceField.getName()));
                    } else if (Map.class.isAssignableFrom(sourceField.getType()) && Map.class.isAssignableFrom(targetField.getType())) {
                        // TODO: 30.06.2016 маппинг для Map
                    } else if (sourceField.getType().isEnum() || targetField.getType().isEnum()) {
                        // TODO: 30.06.2016 маппинг для Enum
                    } else if (sourceField.getType().isArray() && targetField.getType().isArray()) {
                        value = createArrayValue(TypeMapper.array(source, target, sourceField.getName()));
                    }
                    // TODO: 29.06.2016 аннотоции для более точного маппинга

                    ReflectionUtils.setField(targetField, target, value);
                }
            }
        }
        return target;
    }

    private Object createArrayValue(Pair<Class, Object[]> pair) {
        Object array = null;
        if (pair.getValue() != null) {
            array = Array.newInstance(pair.getKey(), pair.getValue().length);
            for (int i = 0; i < pair.getValue().length; i++) {
                Array.set(array, i, map(pair.getValue()[i], pair.getKey()));
            }
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    private Object createCollectionValue(Pair<ParameterizedType, Collection> pair) {
        return pair.getValue().stream()
                .map(sourceItem -> map(sourceItem, (Class) pair.getKey().getActualTypeArguments()[0]))
                .collect(pair.getKey().getRawType().equals(List.class) ? Collectors.toList() : Collectors.toSet());
    }
}
