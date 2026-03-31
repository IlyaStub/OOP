package ru.nsu.gstubarev.prime;

import java.util.Arrays;

/**
 * Performance benchmark for different composite number detection implementations.
 * Measures execution time on large arrays filled with prime numbers.
 */
public class Main {

    /**
     * Entry point for the application.
     * Runs performance comparison of all implementations on a 1M-element array.
     *
     * @param args (not used)
     */
    public static void main(String[] args) {
        CalculationPrime consistent = new ConsistentCalculation();
        CalculationPrime parallelStream = new ParallelStreamCalculation();
        CalculationPrime thread8 = new ThreadCalculation(8);
        CalculationPrime thread16 = new ThreadCalculation(16);

        long[] performanceTestArray = generateLargeArray(1_000);
        System.out.println("Start:\n");
        measurePerformance(performanceTestArray, consistent, parallelStream, thread8, thread16);
    }

    /**
     * Generates an array filled with a large prime number.
     *
     * @param size array size
     * @return new array filled with 999_999_937
     */
    private static long[] generateLargeArray(int size) {
        long[] arr = new long[size];
        Arrays.fill(arr, 999_999_937);
        return arr;
    }

    /**
     * Measures and prints execution time for each calculator.
     *
     * @param array array to process
     * @param calculators implementations to benchmark
     */
    private static void measurePerformance(long[] array, CalculationPrime... calculators) {
        for (CalculationPrime calculator : calculators) {
            long startTime = System.currentTimeMillis();
            boolean result = calculator.hasComposite(array);
            long endTime = System.currentTimeMillis();

            System.out.printf("%s %b for %d ms%n",
                    calculator, result, endTime - startTime);
        }
    }
}
