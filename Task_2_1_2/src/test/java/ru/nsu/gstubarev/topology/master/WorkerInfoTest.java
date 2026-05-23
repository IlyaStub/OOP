package ru.nsu.gstubarev.topology.master;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test.
 */
class WorkerInfoTest {
    @Test
    void testConstructorAndGetters() {
        WorkerInfo worker = new WorkerInfo("127.0.0.1", 8080);

        assertEquals("127.0.0.1", worker.getHost());
        assertEquals(8080, worker.getPort());
        assertTrue(worker.isAlive());
    }

    @Test
    void testMarkDead() {
        WorkerInfo worker = new WorkerInfo("localhost", 9090);

        assertTrue(worker.isAlive());
        worker.markDead();
        assertFalse(worker.isAlive());
    }

    @Test
    void testToString() {
        WorkerInfo aliveWorker = new WorkerInfo("192.168.1.1", 1234);
        assertEquals("192.168.1.1:1234 [alive]", aliveWorker.toString());

        aliveWorker.markDead();
        assertEquals("192.168.1.1:1234 [dead]", aliveWorker.toString());
    }
}