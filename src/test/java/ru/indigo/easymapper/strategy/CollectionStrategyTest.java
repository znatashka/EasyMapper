package ru.indigo.easymapper.strategy;

import org.junit.Test;
import ru.indigo.easymapper.model.client.ClientWithCollection;
import ru.indigo.easymapper.model.server.ServerWithCollection;

import java.lang.reflect.Field;
import java.util.Collection;

import static org.junit.Assert.*;

public class CollectionStrategyTest {

    @Test
    public void getValue() throws Exception {
        // PREPARE
        ServerWithCollection serverWithCollection = ServerWithCollection.create();

        Field sourceField = serverWithCollection.getClass().getDeclaredField("servers");
        sourceField.setAccessible(true);
        Field targetField = ClientWithCollection.class.getDeclaredField("servers");
        targetField.setAccessible(true);

        // ACT
        Object value = CollectionStrategy.getInstance().extractValueFromField(serverWithCollection, sourceField, targetField);

        // ASSERT
        assertNotNull(value);
        assertTrue(value instanceof Collection);
        assertFalse(((Collection) value).isEmpty());
        assertEquals(serverWithCollection.getServers().size(), ((Collection) value).size());
    }
}