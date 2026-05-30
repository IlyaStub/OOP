package ru.nsu.gstubarev.topology.master;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.topology.Command;
import ru.nsu.gstubarev.topology.worker.Worker;

/**
 * Test.
 */
class MasterTest {

    private static final int MASTER_PORT = 19300;
    private static final int MASTER_UDP_PORT = 19302;
    private static final int WORKER_PORT = 19301;
    private static final int SLEEP_MS = 300;
    private Master master;
    private Worker worker;
    private Thread workerThread;

    /**
     * Test.
     */
    @BeforeEach
    void setUp() throws InterruptedException {
        master = new Master(MASTER_PORT, MASTER_UDP_PORT);
        master.startRegistrationListener();
        Thread.sleep(SLEEP_MS);
    }

    /**
     * Test.
     */
    @AfterEach
    void tearDown() throws InterruptedException {
        master.stop();
        if (worker != null) {
            worker.stop();
        }
        if (workerThread != null) {
            workerThread.interrupt();
        }
        Thread.sleep(300);
    }

    @Test
    void testConstructorAndStartListener() {
        assertDoesNotThrow(master::startRegistrationListener);
    }

    @Test
    void testStop() {
        assertDoesNotThrow(master::stop);
    }

    @Test
    void testHasCompositeNoWorkersThrows() {
        assertThrows(
                IllegalStateException.class,
                () -> master.hasComposite(new long[]{2, 3, 5})
        );
    }

    @Test
    void testHasCompositeWithWorkerAllPrimes()
            throws InterruptedException, IOException {
        startWorker();
        registerWorker();

        assertFalse(master.hasComposite(new long[]{
            20319251L, 6997901L, 6997927L, 6997937L
        }));
    }

    @Test
    void testHasCompositeWithWorkerFindsComposite()
            throws InterruptedException, IOException {
        startWorker();
        registerWorker();

        org.junit.jupiter.api.Assertions.assertTrue(
                master.hasComposite(new long[]{4L, 6997901L})
        );
    }

    @Test
    void testRegistrationWithNullLine() throws IOException, InterruptedException {
        try (Socket socket = new Socket("localhost", MASTER_PORT)) {
            socket.shutdownOutput();
        }
        Thread.sleep(100);
        assertDoesNotThrow(master::stop);
    }

    @Test
    void testRegistrationWithInvalidCommand() throws IOException, InterruptedException {
        try (
                Socket socket = new Socket("localhost", MASTER_PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            out.println("INVALID COMMAND");
        }
        Thread.sleep(100);
        assertDoesNotThrow(master::stop);
    }

    private void startWorker() throws InterruptedException {
        worker = new Worker(WORKER_PORT);
        workerThread = new Thread(() -> {
            try {
                worker.start();
            } catch (IOException e) {
                System.out.println("after stop");
            }
        });
        workerThread.setDaemon(true);
        workerThread.start();
        Thread.sleep(SLEEP_MS);
    }

    private void registerWorker() throws IOException, InterruptedException {
        try (
                Socket socket = new Socket("localhost", MASTER_PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                )
        ) {
            out.println(Command.REGISTER.getText() + " localhost " + WORKER_PORT);
            in.readLine();
        }
        Thread.sleep(SLEEP_MS);
    }
}