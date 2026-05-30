package ru.nsu.gstubarev.topology.master;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import ru.nsu.gstubarev.topology.MessageParser;
import ru.nsu.gstubarev.topology.worker.WorkerInfo;

/**
 * Distributes array chunks to workers and collects results.
 */
public class ChunkDistributor {
    private final int connectTimeoutMs;
    private final int readTimeoutMs;

    /**
     * Default constructor.
     */
    public ChunkDistributor() {
        this(3000, 10000);
    }

    /**
     * Custom constructor.
     */
    public ChunkDistributor(int connectTimeoutMs, int readTimeoutMs) {
        this.connectTimeoutMs = connectTimeoutMs;
        this.readTimeoutMs = readTimeoutMs;
    }

    /**
     * Sends chunks to workers and returns true if any composite number found.
     */
    public boolean distribute(long[][] chunks, List<WorkerInfo> workers)
            throws InterruptedException {
        if (workers.isEmpty()) {
            throw new IllegalStateException("No workers available");
        }

        ExecutorService executor = Executors.newFixedThreadPool(workers.size());
        AtomicBoolean result = new AtomicBoolean(false);
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < chunks.length; i++) {
            if (chunks[i].length == 0) {
                continue;
            }
            final int index = i;
            futures.add(executor.submit(() -> {
                if (sendWithFallback(chunks[index], workers, index)) {
                    result.set(true);
                }
            }));
        }

        for (Future<?> f : futures) {
            try {
                f.get();
            } catch (ExecutionException e) {
                throw new IllegalStateException("Worker failed", e.getCause());
            }
        }
        executor.shutdown();
        return result.get();
    }

    /**
     * Sends a chunk to a worker, trying fallbacks if the primary fails.
     */
    private boolean sendWithFallback(long[] chunk, List<WorkerInfo> workers, int index) {
        for (int attempt = 0; attempt < workers.size(); attempt++) {
            WorkerInfo worker = workers.get((index + attempt) % workers.size());
            if (!worker.isAlive()) {
                continue;
            }
            try {
                return sendChunk(chunk, worker);
            } catch (IOException e) {
                System.err.println("Worker " + worker + " failed: " + e.getMessage());
                worker.markDead();
            }
        }
        throw new IllegalStateException("No worker could process chunk");
    }

    /**
     * Sends a chunk to a specific worker and reads the result.
     */
    private boolean sendChunk(long[] chunk, WorkerInfo worker) throws IOException {
        try (Socket socket = new Socket()) {
            socket.connect(
                    new InetSocketAddress(worker.getHost(), worker.getPort()),
                    connectTimeoutMs
            );
            socket.setSoTimeout(readTimeoutMs);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            out.println(MessageParser.buildCheck(chunk));
            String response = in.readLine();
            if (response == null) {
                throw new IOException("Worker closed connection without response");
            }
            return MessageParser.parseResult(response);
        }
    }
}