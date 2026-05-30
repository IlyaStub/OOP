package ru.nsu.gstubarev.topology.master;

/**
 * Holds the address of a discovered master node.
 */
public class MasterInfo {

    private final String host;
    private final int port;

    /**
     * Constructor MasterInfo.
     */
    public MasterInfo(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Returns master host.
     */
    public String getHost() {
        return host;
    }

    /**
     * Returns master port.
     */
    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return host + ":" + port;
    }
}
