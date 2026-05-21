package ru.nsu.gstubarev.topology.master;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Test.
 */
class TaskDistributorTest {

    @Test
    void testDistributeWithEmptyChunks() {
        TaskDistributor distributor = new TaskDistributor();
        long[][] chunks = {new long[0], new long[0]};
        List<WorkerInfo> workers = new ArrayList<>();
        workers.add(new WorkerInfo("localhost", 9999));

        boolean result = distributor.distribute(chunks, workers);

        assertFalse(result);
    }

    @Test
    void testDistributeNoWorkersThrowsException() {
        TaskDistributor distributor = new TaskDistributor();
        long[][] chunks = {{1, 2, 3}};
        List<WorkerInfo> workers = new ArrayList<>();

        assertThrows(IllegalStateException.class, () -> {
            distributor.distribute(chunks, workers);
        });
    }

    @Test
    void testDistributeWithAllChunksEmpty() {
        TaskDistributor distributor = new TaskDistributor();
        long[][] chunks = {new long[0], new long[0], new long[0]};
        List<WorkerInfo> workers = new ArrayList<>();
        workers.add(new WorkerInfo("localhost", 9999));

        boolean result = distributor.distribute(chunks, workers);

        assertFalse(result);
    }
}