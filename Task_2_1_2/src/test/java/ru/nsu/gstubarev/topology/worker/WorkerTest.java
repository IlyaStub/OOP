package ru.nsu.gstubarev.topology.worker;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

/**
 * Test.
 */
class WorkerTest {

    private static final int PORT = 18081;
    private static final int SLEEP_MS = 200;
    private Worker worker;
    private Thread workerThread;

    /**
     * Test.
     */
    @BeforeEach
    void setUp() throws InterruptedException {
        worker = new Worker(PORT);
        workerThread = new Thread(() -> {
            try {
                worker.start();
            } catch (IOException e) {
                System.err.println("error: " + e.getMessage());
            }
        });
        workerThread.setDaemon(true);
        workerThread.start();
        Thread.sleep(SLEEP_MS);
    }

    /**
     * Test.
     */
    @AfterEach
    void tearDown() {
        worker.stop();
        workerThread.interrupt();
    }

    @Test
    void testConstructor() {
        assertDoesNotThrow(() -> new Worker(19999));
    }

    @Test
    void testStop() {
        assertDoesNotThrow(worker::stop);
    }

    @Test
    void testRegisterThrowsWithInvalidMaster() {
        Worker w = new Worker(17777);
        assertThrows(Exception.class, () -> w.register("invalid-host", 9999));
    }

    @Test
    void testPingReturnsPoint() throws IOException {
        try (Socket socket = new Socket("localhost", PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream())
             )
        ) {
            out.println(Command.PING.getText());
            String response = in.readLine();
            assertEquals(Command.PONG.getText(), response);
        }
    }

    @Test
    void testHandleConnectionWithNullLine() throws IOException, InterruptedException {
        try (Socket socket = new Socket("localhost", PORT)) {
            socket.shutdownOutput();
        }
        Thread.sleep(100);
        try (Socket socket = new Socket("localhost", PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream())
             )
        ) {
            out.println(Command.PING.getText());
            assertEquals(Command.PONG.getText(), in.readLine());
        }
    }

    @Test
    void testHandleConnectionIoException() throws IOException, InterruptedException {
        try (Socket socket = new Socket("localhost", PORT)) {
            socket.close();
        }
        Thread.sleep(100);
        try (Socket socket = new Socket("localhost", PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream())
             )
        ) {
            out.println(Command.PING.getText());
            assertEquals(Command.PONG.getText(), in.readLine());
        }
    }

    @Test
    void testCheckAllPrimesReturnsFalse() throws IOException {
        try (Socket socket = new Socket("localhost", PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream())
             )
        ) {
            out.println("CHECK 101,103,107");
            String response = in.readLine();
            assertEquals("RESULT false", response);
        }
    }

    @Test
    void testCheckWithCompositReturnsTrue() throws IOException {
        try (Socket socket = new Socket("localhost", PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream())
             )
        ) {
            out.println("CHECK 101,4,107");
            String response = in.readLine();
            assertEquals("RESULT true", response);
        }
    }

    @Test
    void testStopClosesServerSocket() throws InterruptedException {
        worker.stop();
        Thread.sleep(100);
        assertDoesNotThrow(worker::stop);
    }

    @Test
    void testStopBeforeStart() {
        Worker w = new Worker(19998);
        assertDoesNotThrow(w::stop);
    }

    @Test
    void testMultipleConnectionsHandled() throws IOException {
        for (int i = 0; i < 3; i++) {
            try (Socket socket = new Socket("localhost", PORT);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(
                         new InputStreamReader(socket.getInputStream())
                 )
            ) {
                out.println(Command.PING.getText());
                assertEquals(Command.PONG.getText(), in.readLine());
            }
        }
    }

    @Test
    void testAcceptExceptionWhenNotRunning() throws IOException, InterruptedException {
        worker.stop();
        Thread.sleep(100);
        assertThrows(IOException.class, () -> {
            new Socket("localhost", PORT);
        });
    }

    @Test
    void testWorkerStartTwice() throws IOException, InterruptedException {
        worker.stop();
        Thread.sleep(100);
        assertDoesNotThrow(() -> worker.start());
        worker.stop();
    }

    @Test
    void testCheckSinglePrime() throws IOException {
        try (Socket socket = new Socket("localhost", PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream())
             )
        ) {
            out.println("CHECK 17");
            String response = in.readLine();
            assertEquals("RESULT false", response);
        }
    }

    @Test
    void testCheckSingleComposite() throws IOException {
        try (Socket socket = new Socket("localhost", PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream())
             )
        ) {
            out.println("CHECK 4");
            String response = in.readLine();
            assertEquals("RESULT true", response);
        }
    }

    @Test
    void testHandleConnectionWithEmptyLine() throws IOException {
        try (Socket socket = new Socket("localhost", PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream())
             )
        ) {
            out.println("");
            String response = in.readLine();
            assertEquals(null, response);
        }
    }
}