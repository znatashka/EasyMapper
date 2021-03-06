package ru.indigo.easymapper;

import org.junit.Before;
import org.junit.Test;
import ru.indigo.easymapper.exception.EasyMapperException;
import ru.indigo.easymapper.model.client.*;
import ru.indigo.easymapper.model.server.Server;
import ru.indigo.easymapper.model.server.ServerFull;
import ru.indigo.easymapper.model.server.ServerWithAnnotations;
import ru.indigo.easymapper.model.server.ServerWithCollection;

import static org.junit.Assert.*;

public class EasyMapperTest {

    private EasyMapper easyMapper;

    @Before
    public void setUp() throws Exception {
        easyMapper = new EasyMapper();
    }

    @Test
    public void mapEmptyObjectOk() {
        // ACT
        ClientFull result = easyMapper.map(new ServerFull(), ClientFull.class);

        // ASSERT
        assertNotNull(result);
    }

    @Test(expected = EasyMapperException.class)
    public void mapEmptyObjectError() {
        // ACT
        easyMapper.map(new Server(12), ClientWithoutDefConstructor.class);
    }

    @Test
    public void mapObject() {
        // PREPARE
        Server server = new Server();
        server.setNumber(123);

        // ACT
        Client result = easyMapper.map(server, Client.class);

        // ASSERT
        assertNotNull(result);
        assertEquals(server.getNumber().intValue(), result.getNumber());
    }

    @Test
    public void mapObjectWithCollectionSetToList() {
        // PREPARE
        ServerWithCollection serverWithCollection = ServerWithCollection.create();

        // ACT
        ClientWithCollection result = easyMapper.map(serverWithCollection, ClientWithCollection.class);

        // ASSERT
        assertNotNull(result);
        assertFalse(result.getServers().isEmpty());
        assertEquals(serverWithCollection.getServers().size(), result.getServers().size());
    }

    @Test
    public void mapObjectWithCollectionListToSet() throws Exception {
        // PREPARE
        ClientWithCollection clientWithCollection = ClientWithCollection.create();

        // ACT
        ServerWithCollection result = easyMapper.map(clientWithCollection, ServerWithCollection.class);

        // ASSERT
        assertNotNull(result);
        assertFalse(result.getServers().isEmpty());
        assertEquals(clientWithCollection.getServers().size(), result.getServers().size());
    }

    @Test
    public void mapObjectWithArray() throws Exception {
        // PREPARE
        ServerWithCollection serverWithCollection = ServerWithCollection.createWArray();

        // ACT
        ClientWithCollection result = easyMapper.map(serverWithCollection, ClientWithCollection.class);

        // ASSERT
        assertNotNull(result);
        assertTrue(result.getServersArray().length > 0);
        assertEquals(serverWithCollection.getServersArray().length, result.getServersArray().length);
    }

    @Test
    public void mapFullObjectServerToClient() throws Exception {
        // PREPARE
        ServerFull server = ServerFull.create();

        // ACT
        ClientFull result = easyMapper.map(server, ClientFull.class);

        // ASSERT
        assertNotNull(result);
        assertEquals(server.getNumber().intValue(), result.getNumber());
        assertEquals(server.getString(), result.getString());
        assertEquals(server.getLongNumber(), result.getLongNumber());
        assertEquals(server.getObject().getNumber().intValue(), result.getObject().getNumber());
        assertEquals(server.getEnumValue().name(), result.getEnumValue().name());
        assertEquals(server.getClientNotEnum().name(), result.getClientNotEnum());
        assertEquals(server.isTrue(), result.getIsTrue());
        assertEquals(server.getServerMapToField().getNumber().intValue(), result.getClientMapToField().getNumber());

        assertEquals(server.getServers().size(), result.getServers().size());
        assertTrue(result.getServers().size() == 2);
        result.getServers().stream().forEach(client -> assertNotNull(server.findServerByNumber(client.getNumber())));

        assertEquals(server.getServersArray().length, result.getServersArray().length);
        assertTrue(result.getServersArray().length == 1);
        assertEquals(server.getServersArray()[0].getNumber().intValue(), result.getServersArray()[0].getNumber());

        assertEquals(server.getMap().size(), result.getMap().size());
        result.getMap().entrySet().stream().forEach(entry -> assertNotNull(server.getMap().get(entry.getKey())));
    }

    @Test
    public void mapFullObjectClientToServer() throws Exception {
        // PREPARE
        ClientFull client = ClientFull.create();

        // ACT
        ServerFull result = easyMapper.map(client, ServerFull.class);

        // ASSERT
        assertNotNull(result);
        assertEquals(client.getNumber(), result.getNumber().intValue());
        assertEquals(client.getString(), result.getString());
        assertEquals(client.getLongNumber(), result.getLongNumber());
        assertEquals(client.getObject().getNumber(), result.getObject().getNumber().intValue());
        assertEquals(client.getEnumValue().name(), result.getEnumValue().name());
        assertEquals(client.getClientNotEnum(), result.getClientNotEnum().name());
        assertFalse(result.isTrue());

        assertEquals(client.getServers().size(), result.getServers().size());
        assertTrue(result.getServers().size() == 2);
        result.getServers().stream().forEach(server -> assertNotNull(client.findServerByNumber(server.getNumber())));

        assertEquals(client.getServersArray().length, result.getServersArray().length);
        assertTrue(result.getServersArray().length == 1);
        assertEquals(client.getServersArray()[0].getNumber(), result.getServersArray()[0].getNumber().intValue());

        assertEquals(client.getMap().size(), result.getMap().size());
        result.getMap().entrySet().stream().forEach(entry -> assertNotNull(client.getMap().get(entry.getKey())));
    }

    @Test
    public void mapObjectAnnotationMapTpField() {
        // PREPARE
        ServerWithAnnotations source = new ServerWithAnnotations();
        source.setServerString("serverString");

        // ACT
        ClientWithAnnotations result = easyMapper.map(source, ClientWithAnnotations.class);

        // ASSERT
        assertNotNull(result);
        assertEquals(source.getServerString(), result.getClientString());
    }
}