package ru.indigo.easymapper.strategy;

import org.junit.Test;
import ru.indigo.easymapper.model.client.Client;
import ru.indigo.easymapper.model.server.Server;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public class PrimitiveStrategyTest {

    @Test
    public void objectToPrimitiveWithValue() throws Exception {
        // PREPARE
        Server source = new Server();
        source.setNumber(123);

        Field sourceField = source.getClass().getDeclaredField("number");
        sourceField.setAccessible(true);
        Field targetField = Client.class.getDeclaredField("number");
        targetField.setAccessible(true);

        // ACT
        Object result = PrimitiveStrategy.getInstance().getValue(source, sourceField, targetField);

        // ASSERT
        assertEquals(source.getNumber(), result);
    }

    @Test
    public void objectToPrimitiveWithoutValue() throws Exception {
        // PREPARE
        Server source = new Server();

        Field sourceField = source.getClass().getDeclaredField("number");
        sourceField.setAccessible(true);
        Field targetField = Client.class.getDeclaredField("number");
        targetField.setAccessible(true);

        // ACT
        Object result = PrimitiveStrategy.getInstance().getValue(source, sourceField, targetField);

        // ASSERT
        assertEquals(0, result);
    }

    @Test
    public void primitiveToObject() throws Exception {
        // PREPARE
        Client source = new Client();
        source.setNumber(123);

        Field sourceField = source.getClass().getDeclaredField("number");
        sourceField.setAccessible(true);
        Field targetField = Client.class.getDeclaredField("number");
        targetField.setAccessible(true);

        // ACT
        Object result = PrimitiveStrategy.getInstance().getValue(source, sourceField, targetField);

        // ASSERT
        assertEquals(source.getNumber(), result);
    }
}