package ru.indigo.easymapper;

import ru.indigo.easymapper.exception.EasyMapperException;
import ru.indigo.easymapper.strategy.*;

import java.util.Collection;
import java.util.Map;

class Detector {

    protected static Strategy findStrategy(Class sourceType, Class targetType) {
        if (checkType(sourceType) == ObjectType.PRIMITIVE && checkType(targetType) == ObjectType.PRIMITIVE) {
            return PrimitiveStrategy.getInstance();
        } else if (checkType(sourceType) == ObjectType.COLLECTION && checkType(targetType) == ObjectType.COLLECTION) {
            return CollectionStrategy.getInstance();
        } else if (checkType(sourceType) == ObjectType.ARRAY && checkType(targetType) == ObjectType.ARRAY) {
            return ArrayStrategy.getInstance();
        } else if (checkType(sourceType) == ObjectType.ENUM || checkType(targetType) == ObjectType.ENUM) {
            return EnumStrategy.getInstance();
        } else if (checkType(sourceType) == ObjectType.MAP && checkType(targetType) == ObjectType.MAP) {
            return MapStrategy.getInstance();
        } else if (checkType(sourceType) == ObjectType.OBJECT && checkType(targetType) == ObjectType.OBJECT) {
            return ObjectStrategy.getInstance();
        }
        throw new EasyMapperException("Impossible mapping types: " + sourceType.getName() + " <-> " + targetType.getName());
    }

    protected static ObjectType checkType(Class type) {
        if (type.isPrimitive() ||
                Number.class.isAssignableFrom(type) ||
                Character.class.equals(type) ||
                Byte.class.equals(type) ||
                Boolean.class.equals(type) ||
                CharSequence.class.isAssignableFrom(type)) return ObjectType.PRIMITIVE;
        if (type.isEnum()) return ObjectType.ENUM;
        if (type.isArray()) return ObjectType.ARRAY;
        if (Collection.class.isAssignableFrom(type)) return ObjectType.COLLECTION;
        if (Map.class.isAssignableFrom(type)) return ObjectType.MAP;
        return ObjectType.OBJECT;
    }

    enum ObjectType {
        PRIMITIVE, OBJECT, COLLECTION, ARRAY, MAP, ENUM
    }
}
