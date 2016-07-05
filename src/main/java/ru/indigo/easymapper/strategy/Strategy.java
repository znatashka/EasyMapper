package ru.indigo.easymapper.strategy;

import java.lang.reflect.Field;

public interface Strategy {

    <S> Object extractValueFromField(S source, Field sourceField, Field targetField);

    <S, T> Object getValue(S source, T targetClass);
}
