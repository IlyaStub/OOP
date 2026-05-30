package ru.nsu.gstubarev.topology.worker;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import ru.nsu.gstubarev.topology.master.MasterInfo;

/**
 * Listens for UDP broadcast from master and returns its address.
 */
public class WorkerDiscovery {

    private static final int BUFFER_SIZE = 256;
    private static final String MASTER_HEADER = "MASTER_HERE";

    private final int udpPort;
    private final int timeoutMs;

    /**
     * Creates a WorkerDiscovery that listens on the given UDP port.
     */
    public WorkerDiscovery(int udpPort, int timeoutMs) {
        this.udpPort = udpPort;
        this.timeoutMs = timeoutMs;
    }

    /**
     * Waits for master broadcast and returns master address.
     */
    public MasterInfo discover() throws IOException {
        try (DatagramSocket socket = new DatagramSocket(udpPort)) {
            socket.setSoTimeout(timeoutMs);
            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            socket.receive(packet);

            String message = new String(
                    packet.getData(), 0, packet.getLength()
            );
            if (!message.startsWith(MASTER_HEADER)) {
                throw new IOException("Unexpected broadcast message: " + message);
            }

            String masterHost = packet.getAddress().getHostAddress();
            int masterPort = Integer.parseInt(
                    message.substring(MASTER_HEADER.length()).trim()
            );
            System.out.println("Discovered master at " + masterHost + ":" + masterPort);
            return new MasterInfo(masterHost, masterPort);
        }
    }
}