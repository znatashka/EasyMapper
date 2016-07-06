package ru.indigo.easymapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;
import ru.indigo.easymapper.annotations.MappingToField;
import ru.indigo.easymapper.creator.ObjectCreator;
import ru.indigo.easymapper.strategy.Strategy;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Utility for easy mapping java objects
 * <p>
 * Created by Nataliia_Zolotovitckaia on 29.06.2016.
 */
public class EasyMapper {

    public <T, S> T map(S source, Class<T> targetClass) {
        if (source != null) {
            if (isCustomObject(targetClass)) {
                T target = ObjectCreator.createTarget(targetClass);

                Field[] sourceFields = source.getClass().getDeclaredFields();
                for (Field sourceField : sourceFields) {
                    if (!Modifier.isStatic(sourceField.getModifiers())) {
                        String fieldName = sourceField.getName();
                        if (sourceField.isAnnotationPresent(MappingToField.class)) {
                            String targetFieldName = sourceField.getAnnotation(MappingToField.class).value();
                            if (StringUtils.isNoneBlank(targetFieldName)) fieldName = targetFieldName;
                        }

                        Field targetField = ReflectionUtils.findField(targetClass, fieldName);
                        if (targetField != null) {
                            sourceField.setAccessible(true);
                            targetField.setAccessible(true);

                            Strategy strategy = Detector.findStrategy(sourceField.getType(), targetField.getType());
                            ReflectionUtils.setField(targetField, target, strategy.extractValueFromField(source, sourceField, targetField));
                        }
                    }
                }
                return target;
            } else {
                Strategy strategy = Detector.findStrategy(source.getClass(), targetClass);
                return targetClass.cast(strategy.getValue(source, targetClass));
            }
        }
        return null;
    }

    private static <T> boolean isCustomObject(Class<T> targetClass) {
        return Detector.checkType(targetClass) == Detector.ObjectType.OBJECT;
    }
}
