package ru.nsu.gstubarev.dsl.delegates;

import ru.nsu.gstubarev.dsl.dataClasses.Checkpoint;
import ru.nsu.gstubarev.dsl.dataClasses.Config;

import java.time.LocalDate;
import java.util.Map;

public class CheckpointListConfig {
    private final Config config;
    public CheckpointListConfig(Config config) {
        this.config = config;
    }

    public void addCheckpoint(Map<String, String> params) {
        config.addCheckpoint(new Checkpoint(
                params.get("name"),
                LocalDate.parse(params.get("date"))
        ));
    }
}
