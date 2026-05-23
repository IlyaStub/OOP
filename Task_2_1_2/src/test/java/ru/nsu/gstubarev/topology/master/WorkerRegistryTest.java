package ru.nsu.gstubarev.topology.master;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Test.
 */
class WorkerRegistryTest {

    @Test
    void testRegisterWorker() {
        WorkerRegistry registry = new WorkerRegistry();

        assertEquals(0, registry.size());

        registry.register("127.0.0.1", 8080);
        assertEquals(1, registry.size());
    }

    @Test
    void testRegisterMultipleWorkers() {
        WorkerRegistry registry = new WorkerRegistry();

        registry.register("192.168.1.1", 1000);
        registry.register("192.168.1.2", 2000);
        registry.register("192.168.1.3", 3000);

        assertEquals(3, registry.size());
    }

    @Test
    void testGetAliveReturnsOnlyAliveWorkers() {
        WorkerRegistry registry = new WorkerRegistry();

        registry.register("127.0.0.1", 8080);
        registry.register("127.0.0.2", 8081);

        List<WorkerInfo> alive = registry.getAlive();
        assertEquals(2, alive.size());

        alive.get(0).markDead();

        List<WorkerInfo> aliveAfter = registry.getAlive();
        assertEquals(1, aliveAfter.size());
        assertEquals("127.0.0.2:8081 [alive]", aliveAfter.get(0).toString());
    }

    @Test
    void testGetAliveEmptyRegistry() {
        WorkerRegistry registry = new WorkerRegistry();

        List<WorkerInfo> alive = registry.getAlive();

        assertTrue(alive.isEmpty());
    }
}