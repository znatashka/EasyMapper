package ru.indigo.easymapper.creator;

import com.google.common.base.Defaults;
import org.springframework.util.ReflectionUtils;
import ru.indigo.easymapper.exception.EasyMapperException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ObjectCreator {

    private static final String ERROR_MSG = "Creation target object is failed";

    @SuppressWarnings("unchecked")
    public static <T> T createTarget(Class<T> targetClass) {
        try {
            return targetClass.newInstance();
        } catch (Exception e) {
            Constructor<?>[] constructors = targetClass.getConstructors();
            for (Constructor<?> constructorItem : constructors) {
                if (constructorItem.getParameterCount() == 1 && constructorItem.getParameterTypes()[0].isPrimitive()) {
                    constructorItem.setAccessible(true);
                    try {
                        Object type = ReflectionUtils.getField(ReflectionUtils.findField(targetClass, "TYPE"), targetClass);
                        return targetClass.cast(constructorItem.newInstance(Defaults.defaultValue((Class<T>) type)));
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e1) {
                        throw new EasyMapperException(ERROR_MSG, e1);
                    }
                }
            }
        }
        throw new EasyMapperException(ERROR_MSG);
    }
}
