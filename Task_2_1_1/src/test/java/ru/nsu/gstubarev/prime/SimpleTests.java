package ru.nsu.gstubarev.prime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for prime number detection methods.
 */
public class SimpleTests {
    @Test
    void testIsPrime() {
        assertTrue(CalculationPrime.isPrime(2));
        assertTrue(CalculationPrime.isPrime(20319251));
        assertFalse(CalculationPrime.isPrime(88));
        assertFalse(CalculationPrime.isPrime(1));
    }

    @Test
    void testWithComposites() {
        long[] array = {6, 8, 7, 13, 5, 9, 4};

        ConsistentCalculation cc = new ConsistentCalculation();
        ThreadCalculation tc = new ThreadCalculation(2);
        ParallelStreamCalculation pc = new ParallelStreamCalculation();

        assertTrue(cc.hasComposite(array));
        assertTrue(tc.hasComposite(array));
        assertTrue(pc.hasComposite(array));
    }

    @Test
    void testArrayOfBigPrimes() {
        long[] bigPrimes = {
            20319251, 6997901, 6997927, 6997937, 17858849,
            6997967, 6998009, 6998029, 6998039, 20165149,
            6998051, 6998053
        };

        ConsistentCalculation cc = new ConsistentCalculation();
        ThreadCalculation tc = new ThreadCalculation(4);
        ParallelStreamCalculation pc = new ParallelStreamCalculation();

        assertFalse(cc.hasComposite(bigPrimes));
        assertFalse(tc.hasComposite(bigPrimes));
        assertFalse(pc.hasComposite(bigPrimes));
    }

    @Test
    void testHugeArrayPerformance() {
        long[] hugeArray = new long[100_000];
        Arrays.fill(hugeArray, 3);

        ConsistentCalculation cc = new ConsistentCalculation();
        ThreadCalculation tc = new ThreadCalculation(8);
        ParallelStreamCalculation pc = new ParallelStreamCalculation();

        assertFalse(cc.hasComposite(hugeArray));
        assertFalse(tc.hasComposite(hugeArray));
        assertFalse(pc.hasComposite(hugeArray));
    }

    @Test
    void testHugeArrayWithOneCompositeAtEnd() {
        long[] hugeArray = new long[500_000];
        for (int i = 0; i < hugeArray.length - 1; i++) {
            hugeArray[i] = 5;
        }
        hugeArray[hugeArray.length - 1] = 100;

        ConsistentCalculation cc = new ConsistentCalculation();
        ThreadCalculation tc = new ThreadCalculation(8);
        ParallelStreamCalculation pc = new ParallelStreamCalculation();

        assertTrue(cc.hasComposite(hugeArray));
        assertTrue(tc.hasComposite(hugeArray));
        assertTrue(pc.hasComposite(hugeArray));
    }

    @Test
    void testThreadCalculationInvalidThreadCount() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ThreadCalculation(0);
        });
        assertThrows(IllegalArgumentException.class, () -> new ThreadCalculation(-3));
    }

    @Test
    void testEmptyArray() {
        ConsistentCalculation cc = new ConsistentCalculation();
        ThreadCalculation tc = new ThreadCalculation(8);
        ParallelStreamCalculation pc = new ParallelStreamCalculation();
        long[] emptyArray = new long[]{};
        assertFalse(cc.hasComposite(emptyArray));
        assertFalse(tc.hasComposite(emptyArray));
        assertFalse(pc.hasComposite(emptyArray));
    }

    @Test
    void testLengthArrayShortestThenCountThread() {
        long[] shortArray = new long[10];
        Arrays.fill(shortArray, 3);
        ThreadCalculation tc12 = new ThreadCalculation(12);
        ThreadCalculation tc16 = new ThreadCalculation(16);
        assertFalse(tc12.hasComposite(shortArray));
        assertFalse(tc16.hasComposite(shortArray));
    }

    @Test
    void testMainMethodRunsWithoutErrors() {
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }
}
