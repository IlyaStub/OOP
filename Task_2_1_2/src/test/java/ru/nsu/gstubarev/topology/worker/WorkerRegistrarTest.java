package ru.nsu.gstubarev.topology.worker;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.topology.Command;

/**
 * Test.
 */
class WorkerRegistrarTest {

    private static final int SLEEP_MS = 100;

    @Test
    void testConstructor() {
        assertDoesNotThrow(() -> new WorkerRegistrar("localhost", 9999, 8080));
    }

    @Test
    void testRegisterThrowsWithInvalidMaster() {
        WorkerRegistrar registrar = new WorkerRegistrar("unknown-host", 9999, 8080);
        assertThrows(Exception.class, registrar::register);
    }

    @Test
    void testRegisterSuccessfully() throws IOException, InterruptedException {
        int port = 19100;
        Thread fakemaster = new Thread(() -> {
            try (ServerSocket server = new ServerSocket(port);
                 Socket client = server.accept();
                 BufferedReader in = new BufferedReader(
                         new InputStreamReader(client.getInputStream())
                 );
                 PrintWriter out = new PrintWriter(client.getOutputStream(), true)
            ) {
                in.readLine();
                out.println(Command.OK.getText());
            } catch (IOException e) {
                System.err.println("error: " + e.getMessage());
            }
        });
        fakemaster.setDaemon(true);
        fakemaster.start();
        Thread.sleep(SLEEP_MS);

        WorkerRegistrar registrar = new WorkerRegistrar("localhost", port, 8080);
        assertDoesNotThrow(registrar::register);
    }

    @Test
    void testRegisterRejectedThrows() throws IOException, InterruptedException {
        int port = 19101;
        Thread fakemaster = new Thread(() -> {
            try (ServerSocket server = new ServerSocket(port);
                 Socket client = server.accept();
                 BufferedReader in = new BufferedReader(
                         new InputStreamReader(client.getInputStream())
                 );
                 PrintWriter out = new PrintWriter(client.getOutputStream(), true)
            ) {
                in.readLine();
                out.println("ERROR");
            } catch (IOException e) {
                System.err.println("error: " + e.getMessage());
            }
        });
        fakemaster.setDaemon(true);
        fakemaster.start();
        Thread.sleep(SLEEP_MS);

        WorkerRegistrar registrar = new WorkerRegistrar("localhost", port, 8080);
        assertThrows(IOException.class, registrar::register);
    }
}