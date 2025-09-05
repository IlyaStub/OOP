package ru.nsu.g.stubarev.heapsort;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class SortTest {

    @Test
    void emptyArray() {
        int[] emptyArray = new int[]{};
        Sort.sort(emptyArray);
        assertArrayEquals(new int[]{}, emptyArray);
    }

    @Test
    void singleElement() {
        int[] singleElement = new int[]{0};
        Sort.sort(singleElement);
        assertArrayEquals(new int[]{0}, singleElement);
    }

    @Test
    void sortedArray() {
        int[] sortedArray = new int[]{1, 2, 3, 4, 5};
        Sort.sort(sortedArray);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, sortedArray);
    }

    @Test
    void reverseArray() {
        int[] reverseArray = new int[]{5, 4, 3, 2, 1};
        Sort.sort(reverseArray);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, reverseArray);
    }

    @Test
    void negativeOnly() {
        int[] negativeOnly = new int[]{-4, -10, -1, -8, -2, -4};
        Sort.sort(negativeOnly);
        assertArrayEquals(new int[]{-10, -8, -4, -4, -2, -1}, negativeOnly);
    }

    @Test
    void manyDuplicates() {
        int[] manyDuplicates = new int[]{2, 2, 3, 1, 1, 3, 4, 3, 1, 2, -1, -1, -1, -1};
        Sort.sort(manyDuplicates);
        assertArrayEquals(
                new int[]{-1, -1, -1, -1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4},
                manyDuplicates
        );
    }

    @Test
    void allSame() {
        int[] allSame = new int[]{1, 1, 1, 1, 1, 1};
        Sort.sort(allSame);
        assertArrayEquals(new int[]{1, 1, 1, 1, 1, 1}, allSame);
    }

    @Test
    void largeNumbers() {
        int[] largeNumbers = new int[]{395760211, 1232342, 23123412};
        Sort.sort(largeNumbers);
        assertArrayEquals(new int[]{1232342, 23123412, 395760211}, largeNumbers);
    }

    @Test
    void mixedLargeSmall() {
        int[] mixedLargeSmall = new int[]{-1235953403, 342342345, -463244523, 212323, 23, 1, 5};
        Sort.sort(mixedLargeSmall);
        assertArrayEquals(
                new int[]{-1235953403, -463244523, 1, 5, 23, 212323, 342342345},
                mixedLargeSmall
        );
    }
}