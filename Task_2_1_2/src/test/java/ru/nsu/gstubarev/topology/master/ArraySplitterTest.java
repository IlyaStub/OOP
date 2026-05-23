package ru.nsu.gstubarev.topology.master;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test.
 */
class ArraySplitterTest {

    @Test
    void testSplitEqualChunks() {
        long[] array = {1, 2, 3, 4};
        long[][] chunks = ArraySplitter.split(array, 2);

        assertEquals(2, chunks.length);
        assertArrayEquals(new long[]{1, 2}, chunks[0]);
        assertArrayEquals(new long[]{3, 4}, chunks[1]);
    }

    @Test
    void testSplitUnevenChunks() {
        long[] array = {1, 2, 3, 4, 5};
        long[][] chunks = ArraySplitter.split(array, 2);

        assertEquals(2, chunks.length);
        assertArrayEquals(new long[]{1, 2, 3}, chunks[0]);
        assertArrayEquals(new long[]{4, 5}, chunks[1]);
    }

    @Test
    void testSplitMoreChunksThanElements() {
        long[] array = {1, 2};
        long[][] chunks = ArraySplitter.split(array, 5);

        assertEquals(5, chunks.length);
        assertArrayEquals(new long[]{1}, chunks[0]);
        assertArrayEquals(new long[]{2}, chunks[1]);
        assertArrayEquals(new long[]{}, chunks[2]);
        assertArrayEquals(new long[]{}, chunks[3]);
        assertArrayEquals(new long[]{}, chunks[4]);
    }

    @Test
    void testSplitEmptyArray() {
        long[] array = {};
        long[][] chunks = ArraySplitter.split(array, 3);

        assertEquals(3, chunks.length);
        for (long[] chunk : chunks) {
            assertEquals(0, chunk.length);
        }
    }

    @Test
    void testSplitSingleChunk() {
        long[] array = {1, 2, 3, 4, 5};
        long[][] chunks = ArraySplitter.split(array, 1);

        assertEquals(1, chunks.length);
        assertArrayEquals(array, chunks[0]);
    }
}