package ru.nsu.gstubarev.dsl.dataclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/**
 * TEST.
 */
public class TaskTest {

    @Test
    public void testTaskMethods() {
        LocalDate soft = LocalDate.of(2025, 5, 1);
        LocalDate hard = LocalDate.of(2025, 5, 15);
        Task task = new Task(42, "Final Project", 100, soft, hard);

        assertEquals(42, task.getId());
        assertEquals("Final Project", task.getName());
        assertEquals(100, task.getMaxScores());
        assertEquals(soft, task.getSoftDeadline());
        assertEquals(hard, task.getHardDeadline());

        assertNotNull(task.toString());
    }
}