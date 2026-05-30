package ru.nsu.gstubarev.topology.worker;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import org.junit.jupiter.api.Test;

/**
 * Test.
 */
class WorkerDiscoveryTest {

    private static final int SLEEP_MS = 100;

    @Test
    void testConstructor() {
        assertDoesNotThrow(() -> new WorkerDiscovery(19600, 1000));
    }

    @Test
    void testDiscoverTimeout() {
        WorkerDiscovery discovery = new WorkerDiscovery(19601, 100);
        assertThrows(IOException.class, discovery::discover);
    }

    @Test
    void testDiscoverSuccess() throws Exception {
        int udpPort = 19602;
        int tcpPort = 9000;

        Thread sender = new Thread(() -> {
            try {
                Thread.sleep(SLEEP_MS);
                DatagramSocket socket = new DatagramSocket();
                socket.setBroadcast(true);
                String msg = "MASTER_HERE " + tcpPort;
                byte[] data = msg.getBytes();
                DatagramPacket packet = new DatagramPacket(
                        data, data.length,
                        InetAddress.getByName("localhost"),
                        udpPort
                );
                socket.send(packet);
                socket.close();
            } catch (Exception e) {
                System.err.println("Sender error: " + e.getMessage());
            }
        });
        sender.setDaemon(true);
        sender.start();

        WorkerDiscovery discovery = new WorkerDiscovery(udpPort, 3000);
        var master = discovery.discover();
        assertEquals(tcpPort, master.getPort());
    }

    @Test
    void testDiscoverUnexpectedMessage() throws Exception {
        int udpPort = 19603;

        Thread sender = new Thread(() -> {
            try {
                Thread.sleep(SLEEP_MS);
                DatagramSocket socket = new DatagramSocket();
                String msg = "UNKNOWN_MESSAGE";
                byte[] data = msg.getBytes();
                DatagramPacket packet = new DatagramPacket(
                        data, data.length,
                        InetAddress.getByName("localhost"),
                        udpPort
                );
                socket.send(packet);
                socket.close();
            } catch (Exception e) {
                System.err.println("Sender error: " + e.getMessage());
            }
        });
        sender.setDaemon(true);
        sender.start();

        WorkerDiscovery discovery = new WorkerDiscovery(udpPort, 3000);
        assertThrows(IOException.class, discovery::discover);
    }
}