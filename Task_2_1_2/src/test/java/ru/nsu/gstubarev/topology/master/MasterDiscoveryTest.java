package ru.nsu.gstubarev.topology.master;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import org.junit.jupiter.api.Test;

/**
 * Test.
 */
class MasterDiscoveryTest {

    private static final int SLEEP_MS = 3000;

    @Test
    void testConstructor() {
        assertDoesNotThrow(() -> new MasterDiscovery(9000, 19500));
    }

    @Test
    void testStop() {
        MasterDiscovery discovery = new MasterDiscovery(9000, 19500);
        assertDoesNotThrow(discovery::stop);
    }

    @Test
    void testStopBeforeStart() {
        MasterDiscovery discovery = new MasterDiscovery(9000, 19501);
        assertDoesNotThrow(discovery::stop);
    }

    @Test
    void testBroadcastSendsPacket() throws Exception {
        int udpPort = 19502;
        MasterDiscovery discovery = new MasterDiscovery(9000, udpPort);

        try (DatagramSocket receiver = new DatagramSocket(udpPort)) {
            receiver.setSoTimeout(SLEEP_MS);
            discovery.startBroadcasting();

            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            receiver.receive(packet);

            String message = new String(packet.getData(), 0, packet.getLength());
            org.junit.jupiter.api.Assertions.assertTrue(
                    message.startsWith("MASTER_HERE")
            );
        } finally {
            discovery.stop();
        }
    }
}