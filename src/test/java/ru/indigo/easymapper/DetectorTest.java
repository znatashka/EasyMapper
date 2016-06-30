package ru.indigo.easymapper;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.indigo.easymapper.exception.EasyMapperException;
import ru.indigo.easymapper.model.client.Client;
import ru.indigo.easymapper.strategy.PrimitiveStrategy;
import ru.indigo.easymapper.strategy.Strategy;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class DetectorTest {

    @Test
    public void findStrategyOk() throws Exception {
        // ACT
        Strategy strategy = Detector.findStrategy(Integer.class, int.class);

        // ASSERT
        assertTrue(PrimitiveStrategy.class.equals(strategy.getClass()));
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void findStrategyError() throws Exception {
        // ASSERT
        expectedEx.expect(EasyMapperException.class);
        expectedEx.expectMessage("Impossible mapping types: " + List.class.getName() + " <-> " + Client.class.getName());

        // ACT
        Detector.findStrategy(List.class, Client.class);
    }
}