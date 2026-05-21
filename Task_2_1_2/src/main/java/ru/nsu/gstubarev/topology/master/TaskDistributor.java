package ru.nsu.gstubarev.topology.master;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import ru.nsu.gstubarev.topology.MessageParser;

/**
 * Distributes array chunks to workers and collects results.
 */
public class TaskDistributor {

    private static final int CONNECT_TIMEOUT_MS = 3000;
    private static final int READ_TIMEOUT_MS = 10000;

    /**
     * Sends chunks to workers and returns true if any composite number found.
     */
    public boolean distribute(long[][] chunks, List<WorkerInfo> workers) {
        for (int i = 0; i < chunks.length; i++) {
            if (chunks[i].length == 0) {
                continue;
            }
            if (sendWithFallback(chunks[i], workers, i)) {
                return true;
            }
        }
        return false;
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
                    CONNECT_TIMEOUT_MS
            );
            socket.setSoTimeout(READ_TIMEOUT_MS);
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