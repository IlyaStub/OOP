package ru.nsu.gstubarev.dsl.dataclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class CheckpointTest {
    @Test
    public void testCheckpointMethods() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        Checkpoint checkpoint = new Checkpoint("Milestone", date);

        assertEquals("Milestone", checkpoint.getName());
        assertEquals(date, checkpoint.getDate());
    }
}