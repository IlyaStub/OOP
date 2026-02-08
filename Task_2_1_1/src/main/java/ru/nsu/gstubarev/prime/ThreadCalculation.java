package ru.nsu.gstubarev.prime;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Multithreading implementation of composite number detection.
 * Splits the array into chunks and processes them in parallel threads.
 */
public class ThreadCalculation implements CalculationPrime {

    private final int threadCount;

    /**
     * Creates a thread-based calculator with specified thread count.
     *
     * @param threadCount number of threads to use (must be > 0)
     * @throws IllegalArgumentException if thread count is not positive
     */
    public ThreadCalculation(int threadCount) {
        if (threadCount <= 0) {
            throw new IllegalArgumentException("thread count must be > 0");
        }
        this.threadCount = threadCount;
    }

    @Override
    public boolean hasComposite(long[] array) {
        if (array.length == 0) {
            return false;
        }
        AtomicBoolean found = new AtomicBoolean(false);
        Thread[] threads = new Thread[threadCount];
        int chunkSize = (int) Math.ceil((double) array.length / threadCount);

        for (int i = 0; i < threadCount; i++) {
            int start = i * chunkSize;
            if (start >= array.length) {
                break;
            }
            int end = Math.min(start + chunkSize, array.length);
            threads[i] = new Thread(() -> {
                for (int j = start; j < end && !found.get(); j++) {
                    if (!CalculationPrime.isPrime(array[j])) {
                        found.set(true);
                        break;
                    }
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            if (thread != null) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return found.get();
    }
}