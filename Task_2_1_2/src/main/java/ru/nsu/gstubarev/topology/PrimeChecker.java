package ru.nsu.gstubarev.topology;

/**
 * Utility class for prime number validation.
 */
public class PrimeChecker {
    /**
     * Is Prime.
     */
    public static boolean isPrime(long val) {
        if (val <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(val); i++) {
            if (val % i == 0) {
                return false;
            }
        }
        return true;
    }
}
