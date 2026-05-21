package ru.nsu.gstubarev.topology.worker;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Test.
 */
class WorkerTest {

    @Test
    void testConstructor() {
        assertDoesNotThrow(() -> {
            new Worker(9999);
        });
    }

    @Test
    void testStop() {
        Worker worker = new Worker(8888);
        assertDoesNotThrow(worker::stop);
    }

    @Test
    void testRegisterThrowsWithInvalidMaster() {
        Worker worker = new Worker(7777);
        assertThrows(Exception.class, () -> {
            worker.register("invalid-host", 9999);
        });
    }
}