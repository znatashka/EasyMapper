package ru.indigo.easymapper.strategy;

import org.junit.Test;
import ru.indigo.easymapper.TestExceptionWithMessage;
import ru.indigo.easymapper.exception.EasyMapperException;
import ru.indigo.easymapper.model.client.Client;
import ru.indigo.easymapper.model.client.ClientWithEnum;
import ru.indigo.easymapper.model.server.Server;
import ru.indigo.easymapper.model.server.ServerWithEnum;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class EnumStrategyTest extends TestExceptionWithMessage {

    @Test
    public void getValueBothEnum() throws Exception {
        // PREPARE
        ServerWithEnum serverWithEnum = new ServerWithEnum();
        serverWithEnum.setEnumValue(ServerWithEnum.ServerEnum.VALUE);

        Field sourceField = serverWithEnum.getClass().getDeclaredField("enumValue");
        sourceField.setAccessible(true);
        Field targetField = ClientWithEnum.class.getDeclaredField("enumValue");
        targetField.setAccessible(true);

        // ACT
        Object value = EnumStrategy.getInstance().getValue(serverWithEnum, sourceField, targetField);

        // ASSERT
        assertNotNull(value);
        assertTrue(value instanceof ClientWithEnum.ClientEnum);
        assertEquals(serverWithEnum.getEnumValue().name(), ((ClientWithEnum.ClientEnum) value).name());
    }

    @Test
    public void getValueSourceEnum() throws Exception {
        // PREPARE
        ServerWithEnum serverWithEnum = new ServerWithEnum();
        serverWithEnum.setClientNotEnum(ServerWithEnum.ServerEnum.VALUE);

        Field sourceField = serverWithEnum.getClass().getDeclaredField("clientNotEnum");
        sourceField.setAccessible(true);
        Field targetField = ClientWithEnum.class.getDeclaredField("clientNotEnum");
        targetField.setAccessible(true);

        // ACT
        Object value = EnumStrategy.getInstance().getValue(serverWithEnum, sourceField, targetField);

        // ASSERT
        assertNotNull(value);
        assertTrue(value instanceof String);
        assertEquals(serverWithEnum.getClientNotEnum().name(), String.valueOf(value));
    }

    @Test
    public void getValueTargetEnum() throws Exception {
        // PREPARE
        ClientWithEnum clientWithEnum = new ClientWithEnum();
        clientWithEnum.setClientNotEnum("VALUE");

        Field sourceField = clientWithEnum.getClass().getDeclaredField("clientNotEnum");
        sourceField.setAccessible(true);
        Field targetField = ServerWithEnum.class.getDeclaredField("clientNotEnum");
        targetField.setAccessible(true);

        // ACT
        Object value = EnumStrategy.getInstance().getValue(clientWithEnum, sourceField, targetField);

        // ASSERT
        assertNotNull(value);
        assertTrue(value instanceof ServerWithEnum.ServerEnum);
        assertEquals(clientWithEnum.getClientNotEnum(), ((ServerWithEnum.ServerEnum) value).name());
    }

    @Test
    public void getValueError() throws Exception {
        // PREPARE
        Server server = new Server();
        server.setNumber(1);

        Field sourceField = server.getClass().getDeclaredField("number");
        sourceField.setAccessible(true);
        Field targetField = Client.class.getDeclaredField("number");
        targetField.setAccessible(true);

        // ASSERT
        expectedEx.expect(EasyMapperException.class);
        expectedEx.expectMessage("Impossible mapping types: " + Integer.class.getName() + " <-> " + int.class.getName());

        // ACT
        EnumStrategy.getInstance().getValue(server, sourceField, targetField);
    }
}