package ru.indigo.easymapper;

import org.springframework.util.ReflectionUtils;
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
        if (isCustomObject(targetClass)) {
            T target = ObjectCreator.createTarget(targetClass);

            Field[] sourceFields = source.getClass().getDeclaredFields();
            for (Field sourceField : sourceFields) {
                Field targetField = ReflectionUtils.findField(targetClass, sourceField.getName());
                if (targetField != null) {
                    sourceField.setAccessible(true);
                    targetField.setAccessible(true);

                    if (!Modifier.isStatic(sourceField.getModifiers())) {
                        // TODO: 29.06.2016 аннотоции для более точного маппинга

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

    private static <T> boolean isCustomObject(Class<T> targetClass) {
        return Detector.checkType(targetClass) == Detector.ObjectType.OBJECT;
    }
}
