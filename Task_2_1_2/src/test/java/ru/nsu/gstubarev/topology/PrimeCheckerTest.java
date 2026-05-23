package ru.nsu.gstubarev.topology;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test.
 */
class PrimeCheckerTest {

    @Test
    void testIsPrimeWithPrimeNumbers() {
        assertTrue(PrimeChecker.isPrime(2));
        assertTrue(PrimeChecker.isPrime(3));
        assertTrue(PrimeChecker.isPrime(5));
    }

    @Test
    void testIsPrimeWithCompositeNumbers() {
        assertFalse(PrimeChecker.isPrime(1));
        assertFalse(PrimeChecker.isPrime(4));
        assertFalse(PrimeChecker.isPrime(6));
    }

    @Test
    void testIsPrimeWithZeroAndNegative() {
        assertFalse(PrimeChecker.isPrime(0));
        assertFalse(PrimeChecker.isPrime(-1));
    }

    @Test
    void testIsPrimeWithLargePrime() {
        assertTrue(PrimeChecker.isPrime(6997901L));
        assertTrue(PrimeChecker.isPrime(6997927L));
    }

    @Test
    void testIsPrimeWithLargeComposite() {
        assertFalse(PrimeChecker.isPrime(6997903L));
        assertFalse(PrimeChecker.isPrime(6997929L));
    }
}