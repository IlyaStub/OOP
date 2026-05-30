package ru.nsu.gstubarev.topology;

import ru.nsu.gstubarev.topology.master.Master;
import ru.nsu.gstubarev.topology.worker.Worker;

/**
 * Main class.
 */
public class Main {
    private static final int UDP_PORT = 9001;
    private static final int DISCOVERY_TIMEOUT_MS = 30000;

    private static int startupWaitMs = 15000;

    /**
     * Sets startup wait time. For testing only.
     */
    public static void setStartupWaitMs(int ms) {
        startupWaitMs = ms;
    }

    /**
     * Application entry point.
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            printUsage();
            return;
        }
        switch (args[0]) {
            case "master" -> runMaster(args);
            case "worker" -> runWorker(args);
            default -> printUsage();
        }
    }

    private static void runMaster(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: master <registrationPort>");
            return;
        }
        int registrationPort = Integer.parseInt(args[1]);
        Master master = new Master(registrationPort, UDP_PORT);
        master.startRegistrationListener();

        System.out.println("Waiting " + startupWaitMs + "ms for workers to connect...");
        try {
            Thread.sleep(startupWaitMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        long[] testArray = {
            20319251L, 6997901L, 6997927L, 6997937L, 17858849L, 6997967L, 6998009L, 6998029L
        };

        try {
            boolean result = master.hasComposite(testArray);
            System.out.println("Has composite: " + result);
        } catch (IllegalStateException e) {
            System.err.println("Computation failed: " + e.getMessage());
        } finally {
            master.stop();
        }
    }

    private static void runWorker(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: worker <workerPort>");
            return;
        }
        int workerPort = Integer.parseInt(args[1]);
        Worker worker = new Worker(workerPort);

        try {
            worker.discoverAndRegister(UDP_PORT, DISCOVERY_TIMEOUT_MS);
            worker.start();
        } catch (Exception e) {
            System.err.println("Worker error: " + e.getMessage());
        }
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  master <registrationPort>");
        System.out.println("  worker <masterHost> <masterPort> <workerPort>");
    }
}