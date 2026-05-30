package ru.nsu.gstubarev.topology.master;

import java.util.Arrays;

/**
 * Utility class for splitting arrays.
 */
public class ArraySplitter {
    /**
     * Splits the given array.
     */
    public static long[][] split(long[] array, int count) {
        int chunkSize = (int) Math.ceil((double) array.length / count);
        long[][] chunks = new long[count][];
        for (int i = 0; i < count; i++) {
            int start = i * chunkSize;
            if (start >= array.length) {
                chunks[i] = new long[0];
            } else {
                int end = Math.min(start + chunkSize, array.length);
                chunks[i] = Arrays.copyOfRange(array, start, end);
            }
        }
        return chunks;
    }
}