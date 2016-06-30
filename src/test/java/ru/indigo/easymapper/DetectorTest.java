package ru.indigo.easymapper;

import org.junit.Test;
import ru.indigo.easymapper.exception.EasyMapperException;
import ru.indigo.easymapper.model.client.Client;
import ru.indigo.easymapper.strategy.PrimitiveStrategy;
import ru.indigo.easymapper.strategy.Strategy;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class DetectorTest extends TestExceptionWithMessage {

    @Test
    public void findStrategyOk() throws Exception {
        // ACT
        Strategy strategy = Detector.findStrategy(Integer.class, int.class);

        // ASSERT
        assertTrue(PrimitiveStrategy.class.equals(strategy.getClass()));
    }


    @Test
    public void findStrategyError() throws Exception {
        // ASSERT
        expectedEx.expect(EasyMapperException.class);
        expectedEx.expectMessage("Impossible mapping types: " + List.class.getName() + " <-> " + Client.class.getName());

        // ACT
        Detector.findStrategy(List.class, Client.class);
    }
}