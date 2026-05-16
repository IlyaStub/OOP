package ru.nsu.gstubarev.dsl.delegates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.dsl.dataclasses.Config;
import ru.nsu.gstubarev.dsl.dataclasses.Task;

/**
 * TEST.
 */
public class TaskListConfigTest {

    @Test
    public void testAddTask() {
        Config config = new Config();
        TaskListConfig delegate = new TaskListConfig(config);

        Map<String, Object> params = new HashMap<>();
        params.put("id", 1);
        params.put("name", "Task 1");
        params.put("maxScores", 10);
        params.put("softDeadline", "2024-01-01");
        params.put("hardDeadline", "2024-01-10");

        delegate.addTask(params);
        Task task = config.getTaskById(1);

        assertNotNull(task);
        assertEquals("Task 1", task.getName());
        assertEquals(10, task.getMaxScores());
    }
}