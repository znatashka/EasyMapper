package ru.indigo.easymapper.strategy;

import org.junit.Test;
import ru.indigo.easymapper.model.client.ClientWithCollection;
import ru.indigo.easymapper.model.server.ServerWithCollection;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class ArrayStrategyTest {

    @Test
    public void getValue() throws Exception {
        // PREPARE
        ServerWithCollection serverWithCollection = ServerWithCollection.createWArray();

        Field sourceField = serverWithCollection.getClass().getDeclaredField("serversArray");
        sourceField.setAccessible(true);
        Field targetField = ClientWithCollection.class.getDeclaredField("serversArray");
        targetField.setAccessible(true);

        // ACT
        Object value = ArrayStrategy.getInstance().extractValueFromField(serverWithCollection, sourceField, targetField);

        // ASSERT
        assertNotNull(value);
        assertNotNull(value.getClass().isArray());
        assertTrue(((Object[]) value).length > 0);
        assertEquals(serverWithCollection.getServersArray().length, ((Object[]) value).length);
    }
}