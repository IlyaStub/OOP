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
        int left_child = 2 * root + 1;
        int right_child = 2 * root + 2;

        if (left_child < len && array[left_child] > array[largest]) {
            largest = left_child;
        }
        if (right_child < len && array[right_child] > array[largest]) {
            largest = right_child;
        }
        if (largest != root) {
            int temp = array[root];
            array[root] = array[largest];
            array[largest] = temp;

            heapify(array, len, largest);
        }
    }
}