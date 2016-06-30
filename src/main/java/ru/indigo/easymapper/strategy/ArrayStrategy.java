package ru.indigo.easymapper.strategy;

import javafx.util.Pair;
import ru.indigo.easymapper.mapper.TypeMapper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;


public class ArrayStrategy implements Strategy {

    private static ArrayStrategy instance;

    public static synchronized ArrayStrategy getInstance() {
        if (instance == null) {
            synchronized (ArrayStrategy.class) {
                if (instance == null) {
                    instance = new ArrayStrategy();
                }
            }
        }
        return instance;
    }

    private ArrayStrategy() {
    }

    @Override
    public <S> Object getValue(S source, Field sourceField, Field targetField) {
        Pair<Class, Object[]> pair = TypeMapper.array(source, sourceField, targetField);

        Object array = null;
        if (pair.getValue() != null) {
            array = Array.newInstance(pair.getKey(), pair.getValue().length);
            for (int i = 0; i < pair.getValue().length; i++) {
                Array.set(array, i, EASY_MAPPER.map(pair.getValue()[i], pair.getKey()));
            }
        }
        return array;
    }
}
