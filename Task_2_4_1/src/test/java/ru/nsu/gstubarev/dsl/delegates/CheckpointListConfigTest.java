package ru.nsu.gstubarev.dsl.delegates;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.dsl.dataclasses.Checkpoint;
import ru.nsu.gstubarev.dsl.dataclasses.Config;

/**
 * TEST.
 */
public class CheckpointListConfigTest {

    @Test
    public void testAddCheckpoint() {
        Config config = new Config();
        CheckpointListConfig delegate = new CheckpointListConfig(config);

        Map<String, String> params = new HashMap<>();
        params.put("name", "CP1");
        params.put("date", "2024-01-01");

        delegate.addCheckpoint(params);
        assertEquals(1, config.getCheckpoints().size());

        Checkpoint cp = config.getCheckpoints().get(0);
        assertEquals("CP1", cp.getName());
    }
}