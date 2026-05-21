package ru.nsu.gstubarev.topology.worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import ru.nsu.gstubarev.topology.Command;

/**
 * Handles worker registration with the master.
 */
public class WorkerRegistrar {

    private final String masterHost;
    private final int masterPort;
    private final int workerPort;

    /**
     * Creates a registrar for the given master and worker port.
     */
    public WorkerRegistrar(String masterHost, int masterPort, int workerPort) {
        this.masterHost = masterHost;
        this.masterPort = masterPort;
        this.workerPort = workerPort;
    }

    /**
     * Sends registration request to master and waits for confirmation.
     */
    public void register() throws IOException {
        try (
                Socket socket = new Socket(masterHost, masterPort);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                )
        ) {
            out.println(Command.REGISTER.getText() + " " + workerPort);
            String response = in.readLine();
            if (!Command.OK.getText().equals(response)) {
                throw new IOException("Registration rejected: " + response);
            }
            System.out.println("Registered with master at " + masterHost + ":" + masterPort);
        }
    }
}