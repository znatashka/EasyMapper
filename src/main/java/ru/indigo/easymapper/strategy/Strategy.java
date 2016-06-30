package ru.indigo.easymapper.strategy;

import ru.indigo.easymapper.EasyMapper;

import java.lang.reflect.Field;

public interface Strategy {

    EasyMapper EASY_MAPPER = new EasyMapper();

    <S> Object getValue(S source, Field sourceField, Field targetField);
}
