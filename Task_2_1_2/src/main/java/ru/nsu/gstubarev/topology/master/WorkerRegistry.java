package ru.nsu.gstubarev.topology.master;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Stores and manages registered worker nodes.
 */
public class WorkerRegistry {

    private final List<WorkerInfo> workers = new CopyOnWriteArrayList<>();

    /**
     * Registers a new worker.
     */
    public void register(String host, int port) {
        WorkerInfo worker = new WorkerInfo(host, port);
        workers.add(worker);
        System.out.println("Registered worker: " + worker);
    }

    /**
     * Returns list of currently alive workers.
     */
    public List<WorkerInfo> getAlive() {
        List<WorkerInfo> alive = new ArrayList<>();
        for (WorkerInfo w : workers) {
            if (w.isAlive()) {
                alive.add(w);
            }
        }
        return alive;
    }

    /**
     * Returns total number of registered workers.
     */
    public int size() {
        return workers.size();
    }
}