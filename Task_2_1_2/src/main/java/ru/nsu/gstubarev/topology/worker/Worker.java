package ru.nsu.gstubarev.topology.worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Worker node that listens for tasks from Master and checks numbers for primality.
 */
public class Worker {

    private final int port;
    private final CommandHandler handler;
    private volatile boolean running;

    /**
     * Constructor for Worker.
     */
    public Worker(int port) {
        this.port = port;
        this.handler = new CommandHandler();
        this.running = true;
    }

    /**
     * Registers this worker with the master node.
     */
    public void register(String masterHost, int masterPort) throws IOException {
        new WorkerRegistrar(masterHost, masterPort, port).register();
    }

    /**
     * Starts listening for incoming task connections.
     */
    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Worker listening on port " + port);
            while (running) {
                Socket client = serverSocket.accept();
                new Thread(() -> handleConnection(client)).start();
            }
        }
    }

    private void handleConnection(Socket socket) {
        try (socket;
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream())
             );
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String line = in.readLine();
            if (line == null) {
                return;
            }
            handler.handle(line, out);
        } catch (IOException e) {
            System.err.println("Worker connection error: " + e.getMessage());
        }
    }

    /**
     * Stops the worker gracefully.
     */
    public void stop() {
        running = false;
    }
}