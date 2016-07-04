package ru.indigo.easymapper.strategy;

import org.junit.Test;
import ru.indigo.easymapper.model.client.Client;
import ru.indigo.easymapper.model.client.ClientFull;
import ru.indigo.easymapper.model.server.Server;
import ru.indigo.easymapper.model.server.ServerFull;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ObjectStrategyTest {

    @Test
    public void getValue() throws Exception {
        // PREPARE
        ServerFull server = new ServerFull();
        server.setObject(new Server(123));

        Field sourceField = server.getClass().getDeclaredField("object");
        sourceField.setAccessible(true);
        Field targetField = ClientFull.class.getDeclaredField("object");
        targetField.setAccessible(true);

        // ACT
        Object value = ObjectStrategy.getInstance().getValue(server, sourceField, targetField);

        // ASSERT
        assertNotNull(value);
        assertEquals(server.getObject().getNumber().intValue(), ((Client) value).getNumber());
    }
}