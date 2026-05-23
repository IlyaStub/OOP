package ru.nsu.gstubarev.topology.master;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import ru.nsu.gstubarev.topology.Command;

/**
 * Master node that coordinates distributed prime checking across worker nodes.
 */
public class Master {

    private final int registrationPort;
    private final WorkerRegistry registry;
    private final TaskDistributor distributor;
    private volatile boolean running;
    private ServerSocket serverSocket;

    /**
     * Creates a Master that listens for worker registrations on the given port.
     */
    public Master(int registrationPort) {
        this.registrationPort = registrationPort;
        this.registry = new WorkerRegistry();
        this.distributor = new TaskDistributor();
        this.running = true;
    }

    /**
     * Starts listening for worker registrations in a background thread.
     */
    public void startRegistrationListener() {
        Thread listener = new Thread(this::listenForRegistrations);
        listener.setDaemon(true);
        listener.start();
        System.out.println("Master listening on port " + registrationPort);
    }

    /**
     * Accepts worker registration connections until stopped.
     */
    private void listenForRegistrations() {
        try {
            serverSocket = new ServerSocket(registrationPort);
            while (running) {
                Socket socket = serverSocket.accept();
                new Thread(() -> handleRegistration(socket)).start();
            }
        } catch (IOException e) {
            if (running) {
                System.err.println("Registration listener error: " + e.getMessage());
            }
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.err.println("Error closing socket: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Handles a single worker registration request.
     */
    private void handleRegistration(Socket socket) {
        try (socket;
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream())
             );
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String line = in.readLine();
            if (line == null || !line.startsWith(Command.REGISTER.getText())) {
                return;
            }
            int workerPort = Integer.parseInt(line.split(" ")[1].trim());
            String workerHost = socket.getInetAddress().getHostAddress();
            registry.register(workerHost, workerPort);
            out.println(Command.OK.getText());
        } catch (IOException | NumberFormatException e) {
            System.err.println("Registration error: " + e.getMessage());
        }
    }

    /**
     * Checks if the array contains at least one composite number.
     */
    public boolean hasComposite(long[] array) {
        List<WorkerInfo> alive = registry.getAlive();
        if (alive.isEmpty()) {
            throw new IllegalStateException("No alive workers available");
        }
        long[][] chunks = ArraySplitter.split(array, alive.size());
        try {
            return distributor.distribute(chunks, alive);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Computation interrupted", e);
        }
    }

    /**
     * Stops the master node.
     */
    public void stop() {
        running = false;
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing server socket: " + e.getMessage());
            }
        }
    }
}
