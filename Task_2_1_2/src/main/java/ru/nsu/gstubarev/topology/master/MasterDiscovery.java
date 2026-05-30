package ru.nsu.gstubarev.topology.master;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Broadcasts master presence over UDP to the local subnet.
 */
public class MasterDiscovery {

    private static final String BROADCAST_ADDRESS = "255.255.255.255";
    private static final int BROADCAST_INTERVAL_MS = 2000;

    private final int tcpPort;
    private final int udpPort;
    private volatile boolean running;

    /**
     * Constructor MasterDiscovery.
     */
    public MasterDiscovery(int tcpPort, int udpPort) {
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
        this.running = true;
    }

    /**
     * Starts broadcasting in a daemon thread.
     */
    public void startBroadcasting() {
        Thread thread = new Thread(this::broadcast);
        thread.setDaemon(true);
        thread.start();
        System.out.println("Master broadcasting on UDP port " + udpPort);
    }

    private void broadcast() {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true);
            InetAddress address = InetAddress.getByName(BROADCAST_ADDRESS);
            String message = "MASTER_HERE " + tcpPort;
            byte[] data = message.getBytes();

            while (running) {
                DatagramPacket packet = new DatagramPacket(
                        data, data.length, address, udpPort
                );
                socket.send(packet);
                Thread.sleep(BROADCAST_INTERVAL_MS);
            }
        } catch (IOException | InterruptedException e) {
            if (running) {
                System.err.println("Broadcast error: " + e.getMessage());
            }
        }
    }

    /**
     * Stops broadcasting.
     */
    public void stop() {
        running = false;
    }
}