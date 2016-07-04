package ru.indigo.easymapper.strategy;

import org.junit.Test;
import ru.indigo.easymapper.model.client.ClientFull;
import ru.indigo.easymapper.model.server.ServerFull;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StringStrategyTest {

    @Test
    public void getValue() throws Exception {
        // PREPARE
        ServerFull server = new ServerFull();
        server.setString("text");

        Field sourceField = server.getClass().getDeclaredField("string");
        sourceField.setAccessible(true);
        Field targetField = ClientFull.class.getDeclaredField("string");
        targetField.setAccessible(true);

        // ACT
        Object value = StringStrategy.getInstance().getValue(server, sourceField, targetField);

        // ASSERT
        assertNotNull(value);
        assertEquals(server.getString(), value);
    }
}