package ru.nsu.g.stubarev.heapsort;

/**
 * Класс Sort содержит в себе методы для пиромидальной сортировки.
 * Содержит метод sort и heapify
 */
public class Sort {

    /**
     * Метод sort сортирует массив целых чисел.
     *
     * @param array массив для сортировки
     * @return отсортированный массив
     */
    public static int[] sort(int[] array) {
        int len = array.length;

        for (int i = len / 2 - 1; i >= 0; i--) {
            heapify(array, len, i);
        }

        for (int i = len - 1; i > 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            heapify(array, i, 0);
        }

        return array;
    }

    private static void heapify(int[] array, int len, int root) {
        int largest = root;
        int leftChild = 2 * root + 1;
        int rightChild = 2 * root + 2;

        if (leftChild < len && array[leftChild] > array[largest]) {
            largest = leftChild;
        }
        if (rightChild < len && array[rightChild] > array[largest]) {
            largest = rightChild;
        }
        if (largest != root) {
            int temp = array[root];
            array[root] = array[largest];
            array[largest] = temp;

            heapify(array, len, largest);
        }
    }
}