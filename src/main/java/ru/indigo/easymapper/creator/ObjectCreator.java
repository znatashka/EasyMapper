package ru.indigo.easymapper.creator;

import ru.indigo.easymapper.exception.EasyMapperException;

public class ObjectCreator {

    public static <T> T createTarget(Class<T> targetClass) {
        try {
            return targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new EasyMapperException("Creation target object is failed", e);
        }
    }
}
