package ru.nsu.gstubarev.prime;

/**
 * Sequential implementation of composite number detection.
 * Processes array elements one by one in a single thread.
 */
public class ConsistentCalculation implements CalculationPrime {
    @Override
    public boolean hasComposite(long[] array) {
        for (long l : array) {
            if (!CalculationPrime.isPrime(l)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "ConsistentCalculation:";
    }
}
