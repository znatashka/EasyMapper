package ru.indigo.easymapper.strategy;

import javafx.util.Pair;
import ru.indigo.easymapper.mapper.TypeMapper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionStrategy implements Strategy {

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

    @SuppressWarnings("unchecked")
    @Override
    public <S> Object getValue(S source, Field sourceField, Field targetField) {
        Pair<ParameterizedType, Collection> pair = TypeMapper.collection(source, sourceField, targetField);

        return pair.getValue().stream()
                .map(sourceItem -> EASY_MAPPER.map(sourceItem, (Class) pair.getKey().getActualTypeArguments()[0]))
                .collect(pair.getKey().getRawType().equals(List.class) ? Collectors.toList() : Collectors.toSet());
    }
}
