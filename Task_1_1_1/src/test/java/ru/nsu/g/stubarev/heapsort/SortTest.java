package ru.nsu.g.stubarev.heapsort;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SortTest {

    @Test
    void sort() {
        //пустой массив
        int[] test1 = new int[]{};
        int[] result1 = Sort.sort(test1);
        assertArrayEquals(new int[]{}, result1);

        //массив с одним элементом
        int[] test2 = new int[]{0};
        int[] result2 = Sort.sort(test2);
        assertArrayEquals(new int[]{0}, result2);

        //уже отсортированный массив
        int[] test3 = new int[]{1, 2, 3, 4, 5};
        int[] result3 = Sort.sort(test3);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, result3);

        //обратный массив
        int[] test4 = new int[]{5, 4, 3, 2, 1};
        int[] result4 = Sort.sort(test4);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, result4);

        //отрицательные и положительные
        int[] test5 = new int[]{-6, -9, 0, -10, 4, 5};
        int[] result5 = Sort.sort(test5);
        assertArrayEquals(new int[]{-10, -9, -6, 0, 4, 5}, result5);

        //только отрицательные
        int[] test6 = new int[]{-4, -10, -1, -8, -2, -4};
        int[] result6 = Sort.sort(test6);
        assertArrayEquals(new int[]{-10, -8, -4, -4, -2, -1}, result6);

        //много повторок
        int[] test7 = new int[]{2, 2, 3, 1, 1, 3, 4, 3, 1, 2, -1, -1, -1, -1};
        int[] result7 = Sort.sort(test7);
        assertArrayEquals(new int[]{-1, -1, -1, -1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4}, result7);

        //онли повторки
        int[] test8 = new int[]{1, 1, 1, 1, 1, 1};
        int[] result8 = Sort.sort(test8);
        assertArrayEquals(new int[]{1, 1, 1, 1, 1, 1}, result8);

        //большие числа ну не больше чем вмещает инт
        int[] test9 = new int[]{395760211, 1232342, 23123412};
        int[] result9 = Sort.sort(test9);
        assertArrayEquals(new int[]{1232342, 23123412, 395760211}, result9);

        //большие числа как отрицательные так и положительные, ну и маленькие есть
        int[] test10 = new int[]{-1235953403, 342342345, -463244523, 212323, 23, 1, 5};
        int[] result10 = Sort.sort(test10);
        assertArrayEquals(new int[]{-1235953403, -463244523, 1, 5, 23, 212323,
                342342345}, result10);
    }
}