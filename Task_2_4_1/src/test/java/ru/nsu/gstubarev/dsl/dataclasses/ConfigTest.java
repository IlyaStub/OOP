package ru.nsu.gstubarev.dsl.dataclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/**
 * TEST.
 */
public class ConfigTest {

    @Test
    public void testConfigMethods() {
        Config config = new Config();

        Task task = new Task(1, "Task1", 10, LocalDate.now(), LocalDate.now());
        config.addTask(task);
        assertEquals(task, config.getTaskById(1));
        assertNull(config.getTaskById(999));

        Group group = new Group("20201");
        config.addGroup(group);
        assertEquals(1, config.getGroups().size());
        assertEquals("20201", config.getGroups().get(0).getName());

        Checkpoint cp = new Checkpoint("CP1", LocalDate.now());
        config.addCheckpoint(cp);
        assertEquals(1, config.getCheckpoints().size());

        config.addCheck("20201", 1L);
        assertTrue(config.getChecks().containsKey("20201"));
        assertEquals(1, config.getChecks().get("20201").size());

        config.addBonus("userGit", 1L, 5);
        assertEquals(5, config.getBonus("userGit", 1L));
        assertEquals(0, config.getBonus("unknownGit", 1L));
        assertEquals(0, config.getBonus("userGit", 2L));

        assertNotNull(config.toString());
    }
}