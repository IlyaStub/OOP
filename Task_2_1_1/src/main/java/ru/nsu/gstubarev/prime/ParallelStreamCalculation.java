package ru.nsu.gstubarev.prime;

import java.util.Arrays;

/**
 * Parallel stream implementation of composite number detection.
 * Uses Java parallel streams for automatic thread management.
 */
public class ParallelStreamCalculation implements CalculationPrime {

    @Override
    public boolean hasComposite(long[] array) {
        return Arrays.stream(array)
                .parallel()
                .anyMatch(n -> !CalculationPrime.isPrime(n));
    }
}