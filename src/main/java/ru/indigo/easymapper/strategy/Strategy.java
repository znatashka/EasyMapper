package ru.indigo.easymapper.strategy;

import ru.indigo.easymapper.EasyMapper;

import java.lang.reflect.Field;

public interface Strategy {

    EasyMapper EASY_MAPPER = new EasyMapper();

    <S> Object extractValueFromField(S source, Field sourceField, Field targetField);

    <S, T> Object getValue(S source, T targetClass);
}
