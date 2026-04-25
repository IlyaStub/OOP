package ru.nsu.gstubarev.dsl.delegates;

import ru.nsu.gstubarev.dsl.dataClasses.Config;
import ru.nsu.gstubarev.dsl.dataClasses.Task;
import java.time.LocalDate;
import java.util.Map;

public class TaskListConfig {
    private final Config config;

    public TaskListConfig(Config config) {
        this.config = config;
    }

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
