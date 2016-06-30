package ru.indigo.easymapper;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

public abstract class TestExceptionWithMessage {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
}
