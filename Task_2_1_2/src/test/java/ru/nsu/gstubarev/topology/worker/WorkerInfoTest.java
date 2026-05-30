package ru.nsu.gstubarev.topology.worker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test.
 */
class WorkerInfoTest {

    @Test
    void testConstructor() {
        WorkerInfo info = new WorkerInfo("localhost", 8080);
        assertEquals("localhost", info.getHost());
        assertEquals(8080, info.getPort());
        assertTrue(info.isAlive());
    }

    @Test
    void testMarkDead() {
        WorkerInfo info = new WorkerInfo("localhost", 8080);
        info.markDead();
        assertFalse(info.isAlive());
    }

    @Test
    void testToStringAlive() {
        WorkerInfo info = new WorkerInfo("localhost", 8080);
        assertEquals("localhost:8080 [alive]", info.toString());
    }

    @Test
    void testToStringDead() {
        WorkerInfo info = new WorkerInfo("localhost", 8080);
        info.markDead();
        assertEquals("localhost:8080 [dead]", info.toString());
    }
}