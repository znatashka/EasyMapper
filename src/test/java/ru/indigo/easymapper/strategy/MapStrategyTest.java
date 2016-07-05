package ru.indigo.easymapper.strategy;

import org.junit.Test;
import ru.indigo.easymapper.model.client.ClientWithMap;
import ru.indigo.easymapper.model.server.ServerWithMap;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MapStrategyTest {

    @SuppressWarnings("unchecked")
    @Test
    public void getValue() throws Exception {
        // PREPARE
        ServerWithMap server = ServerWithMap.create();

        Field sourceField = server.getClass().getDeclaredField("map");
        sourceField.setAccessible(true);
        Field targetField = ClientWithMap.class.getDeclaredField("map");
        targetField.setAccessible(true);

        // ACT
        Object value = MapStrategy.getInstance().extractValueFromField(server, sourceField, targetField);

        // ASSERT
        assertNotNull(value);
        Map<Integer, List<String>> map = (Map<Integer, List<String>>) value;
        map.entrySet().stream().forEach(entry -> {
            Set<String> serverValue = server.getMap().get(entry.getKey());
            assertNotNull(serverValue);
            serverValue.stream().forEach(s -> assertTrue(entry.getValue().contains(s)));
        });
    }
}