package ru.nsu.g.stubarev.Task_1_1_1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortTest {

    @Test
    void sort() {
        int[] array = new int[]{1, 3, 2};
        int[] result = Sort.sort(array);
        assertArrayEquals(new int[]{1, 2, 3}, result);
    }
}