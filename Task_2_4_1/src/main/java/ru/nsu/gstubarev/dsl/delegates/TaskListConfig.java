package ru.nsu.gstubarev.dsl.delegates;

import java.time.LocalDate;
import java.util.Map;
import ru.nsu.gstubarev.dsl.dataclasses.Config;
import ru.nsu.gstubarev.dsl.dataclasses.Task;

/**
 * Configures task list entries.
 */
public class TaskListConfig {
    private final Config config;

    /**
     * Wraps config for task setup.
     */
    public TaskListConfig(Config config) {
        this.config = config;
    }

    /**
     * Adds task from parameters.
     */
    public void addTask(Map<String, Object> params) {
        int id = (Integer) params.get("id");
        String name = (String) params.get("name");
        int maxScores = (Integer) params.get("maxScores");

        LocalDate soft = LocalDate.parse((String) params.get("softDeadline"));
        LocalDate hard = LocalDate.parse((String) params.get("hardDeadline"));

        Task t = new Task(id, name, maxScores, soft, hard);
        config.addTask(t);
    }
}