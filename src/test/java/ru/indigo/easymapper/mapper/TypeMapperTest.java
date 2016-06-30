package ru.indigo.easymapper.mapper;

import javafx.util.Pair;
import org.junit.Test;
import ru.indigo.easymapper.model.client.Client;
import ru.indigo.easymapper.model.client.ClientWithCollection;
import ru.indigo.easymapper.model.server.Server;
import ru.indigo.easymapper.model.server.ServerWithCollection;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import static org.junit.Assert.*;

public class TypeMapperTest {

    @Test
    public void objectToPrimitiveWithValue() throws Exception {
        // PREPARE
        Server source = new Server();
        source.setNumber(123);

        // ACT
        Object result = TypeMapper.primitive(source, new Client(), "number");

        // ASSERT
        assertEquals(source.getNumber(), result);
    }

    @Test
    public void objectToPrimitiveWithoutValue() throws Exception {
        // PREPARE
        Server source = new Server();

        // ACT
        Object result = TypeMapper.primitive(source, new Client(), "number");

        // ASSERT
        assertEquals(0, result);
    }

    @Test
    public void primitiveToObject() throws Exception {
        // PREPARE
        Client source = new Client();
        source.setNumber(123);

        // ACT
        Object result = TypeMapper.primitive(source, new Server(), "number");

        // ASSERT
        assertEquals(source.getNumber(), result);
    }

    @Test
    public void collection() throws Exception {
        // PREPARE
        ServerWithCollection serverWithCollection = ServerWithCollection.create();

        // ACT
        Pair<ParameterizedType, Collection> result = TypeMapper.collection(serverWithCollection, new ClientWithCollection(), "servers");

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

        // ACT
        Pair<Class, Object[]> result = TypeMapper.array(serverWithCollection, new ClientWithCollection(), "serversArray");

        // ASSERT
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertTrue(result.getValue().length > 0);
        assertEquals(serverWithCollection.getServersArray().length, result.getValue().length);
    }
}