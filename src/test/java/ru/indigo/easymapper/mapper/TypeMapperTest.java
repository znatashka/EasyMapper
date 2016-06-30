package ru.indigo.easymapper.mapper;

import javafx.util.Pair;
import org.junit.Test;
import ru.indigo.easymapper.model.client.Client;
import ru.indigo.easymapper.model.client.ClientWithCollection;
import ru.indigo.easymapper.model.server.Server;
import ru.indigo.easymapper.model.server.ServerWithCollection;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import static org.junit.Assert.*;

public class TypeMapperTest {

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
        Object result = TypeMapper.primitive(source, sourceField, targetField);

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
        Object result = TypeMapper.primitive(source, sourceField, targetField);

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
        Object result = TypeMapper.primitive(source, sourceField, targetField);

        // ASSERT
        assertEquals(source.getNumber(), result);
    }

    @Test
    public void collection() throws Exception {
        // PREPARE
        ServerWithCollection serverWithCollection = ServerWithCollection.create();

        Field sourceField = serverWithCollection.getClass().getDeclaredField("servers");
        sourceField.setAccessible(true);
        Field targetField = ClientWithCollection.class.getDeclaredField("servers");
        targetField.setAccessible(true);

        // ACT
        Pair<ParameterizedType, Collection> result = TypeMapper.collection(serverWithCollection, sourceField, targetField);

        // ASSERT
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertFalse(result.getValue().isEmpty());
        assertEquals(serverWithCollection.getServers().size(), result.getValue().size());
    }

    @Test
    public void array() throws Exception {
        // PREPARE
        ServerWithCollection serverWithCollection = ServerWithCollection.createWArray();

        Field sourceField = serverWithCollection.getClass().getDeclaredField("serversArray");
        sourceField.setAccessible(true);
        Field targetField = ClientWithCollection.class.getDeclaredField("serversArray");
        targetField.setAccessible(true);

        // ACT
        Pair<Class, Object[]> result = TypeMapper.array(serverWithCollection, sourceField, targetField);

        // ASSERT
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertTrue(result.getValue().length > 0);
        assertEquals(serverWithCollection.getServersArray().length, result.getValue().length);
    }
}