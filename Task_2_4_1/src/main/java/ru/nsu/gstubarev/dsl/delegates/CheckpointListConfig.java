package ru.nsu.gstubarev.dsl.delegates;

import java.time.LocalDate;
import java.util.Map;
import ru.nsu.gstubarev.dsl.dataClasses.Checkpoint;
import ru.nsu.gstubarev.dsl.dataClasses.Config;

/**
 * Configures checkpoint list entries.
 */
public class CheckpointListConfig {
    private final Config config;

    /**
     * Wraps config for checkpoint setup.
     */
    public CheckpointListConfig(Config config) {
        this.config = config;
    }

    /**
     * Adds checkpoint from parameters.
     */
    public void addCheckpoint(Map<String, String> params) {
        config.addCheckpoint(new Checkpoint(
                params.get("name"),
                LocalDate.parse(params.get("date"))
        ));
    }
}