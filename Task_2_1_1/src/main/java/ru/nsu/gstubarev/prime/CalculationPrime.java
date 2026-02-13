package ru.nsu.gstubarev.prime;

/**
 * Interface for checking presence of composite numbers in arrays.
 * Provides a utility method for prime number validation.
 */
public interface CalculationPrime {

    /**
     * Checks if the given value is a prime number.
     *
     * @param val the number to check
     * @return true if the number is prime, false otherwise
     */
    static boolean isPrime(long val) {
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

    /**
     * Checks if the array contains at least one composite number.
     *
     * @param array the array to check
     * @return true if a composite number is found, false otherwise
     */
    boolean hasComposite(long[] array);

    /**
     * Override this method in your class, please.
     *
     * @return sting name class
     */
    @Override
    String toString();
}
