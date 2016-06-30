package ru.indigo.easymapper.creator;

import org.junit.Test;
import ru.indigo.easymapper.exception.EasyMapperException;
import ru.indigo.easymapper.model.client.Client;
import ru.indigo.easymapper.model.client.ClientWithoutDefConstructor;

import static org.junit.Assert.assertNotNull;

public class ObjectCreatorTest {

    @Test
    public void createTargetObjectOk() {
        // ACT
        Client result = ObjectCreator.createTarget(Client.class);

        // ASSERT
        assertNotNull(result);
    }

    @Test(expected = EasyMapperException.class)
    public void createTargetObjectError() {
        // ACT
        ObjectCreator.createTarget(ClientWithoutDefConstructor.class);
    }
}