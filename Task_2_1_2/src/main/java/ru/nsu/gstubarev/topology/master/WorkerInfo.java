package ru.nsu.gstubarev.topology.master;

/**
 * Holds connection info and status of a remote worker node.
 */
public class WorkerInfo {

    private final String host;
    private final int port;
    private volatile boolean alive;

    /**
     * Creates a WorkerInfo with given host and port.
     *
     * @param host worker hostname or IP
     * @param port worker listening port
     */
    public WorkerInfo(String host, int port) {
        this.host = host;
        this.port = port;
        this.alive = true;
    }

    /**
     * Get worker hostname or IP.
     */
    public String getHost() {
        return host;
    }

    /**
     * Get worker port.
     */
    public int getPort() {
        return port;
    }

    /**
     * Get true if worker is considered alive.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Marks this worker as dead.
     */
    public void markDead() {
        this.alive = false;
    }

    @Override
    public String toString() {
        return host + ":" + port + " [" + (alive ? "alive" : "dead") + "]";
    }
}