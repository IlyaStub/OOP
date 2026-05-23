package ru.nsu.gstubarev.topology.master;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Test.
 */
class TaskDistributorTest {

    private static final int SLEEP_MS = 100;

    @Test
    void testDistributeWithEmptyChunks() throws InterruptedException {
        TaskDistributor distributor = new TaskDistributor();
        long[][] chunks = {new long[0], new long[0]};
        List<WorkerInfo> workers = new ArrayList<>();
        workers.add(new WorkerInfo("localhost", 9999));

        assertFalse(distributor.distribute(chunks, workers));
    }

    @Test
    void testDistributeAllChunksEmpty() throws InterruptedException {
        TaskDistributor distributor = new TaskDistributor();
        long[][] chunks = {new long[0], new long[0], new long[0]};
        List<WorkerInfo> workers = List.of(new WorkerInfo("localhost", 9999));

        assertFalse(distributor.distribute(chunks, workers));
    }

    @Test
    void testDistributeNoWorkersThrows() {
        TaskDistributor distributor = new TaskDistributor();
        long[][] chunks = {{2, 3, 5}};

        assertThrows(
                IllegalStateException.class,
                () -> distributor.distribute(chunks, new ArrayList<>())
        );
    }

    @Test
    void testDistributeAllPrimes() throws IOException, InterruptedException {
        int port = 19200;
        startFakeWorker(port, "RESULT false");

        List<WorkerInfo> workers = List.of(new WorkerInfo("localhost", port));
        long[][] chunks = {{101L, 103L, 107L}};

        assertFalse(new TaskDistributor().distribute(chunks, workers));
    }

    @Test
    void testDistributeWithComposite() throws IOException, InterruptedException {
        int port = 19201;
        startFakeWorker(port, "RESULT true");

        List<WorkerInfo> workers = List.of(new WorkerInfo("localhost", port));
        long[][] chunks = {{4L, 103L}};

        assertTrue(new TaskDistributor().distribute(chunks, workers));
    }

    @Test
    void testFallbackToSecondWorker() throws IOException, InterruptedException {
        int port = 19202;
        startFakeWorker(port, "RESULT false");

        WorkerInfo dead = new WorkerInfo("localhost", 19299);
        dead.markDead();
        WorkerInfo alive = new WorkerInfo("localhost", port);
        List<WorkerInfo> workers = new ArrayList<>();
        workers.add(dead);
        workers.add(alive);

        long[][] chunks = {{101L, 103L}};
        assertFalse(new TaskDistributor().distribute(chunks, workers));
    }

    private void startFakeWorker(int port, String response)
            throws IOException, InterruptedException {
        Thread fake = new Thread(() -> {
            try (ServerSocket server = new ServerSocket(port);
                 Socket client = server.accept();
                 BufferedReader in = new BufferedReader(
                         new InputStreamReader(client.getInputStream())
                 );
                 PrintWriter out = new PrintWriter(client.getOutputStream(), true)
            ) {
                in.readLine();
                out.println(response);
            } catch (IOException e) {
                System.err.println("error: " + e.getMessage());
            }
        });
        fake.setDaemon(true);
        fake.start();
        Thread.sleep(SLEEP_MS);
    }
}