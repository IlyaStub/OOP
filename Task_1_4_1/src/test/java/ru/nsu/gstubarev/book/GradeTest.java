package ru.nsu.gstubarev.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GradeTest {

    @Test
    void testNumericValues() {
        assertEquals(5, Grade.EXCELLENT.getNumericValue());
        assertEquals(4, Grade.GOOD.getNumericValue());
        assertEquals(3, Grade.SATISFACTORY.getNumericValue());
        assertEquals(2, Grade.FAIL.getNumericValue());
        assertEquals(-1, Grade.PASS.getNumericValue());
        assertEquals(-1, Grade.FAIL_PASS.getNumericValue());
    }

    @Test
    void testHasNumericValue() {
        assertTrue(Grade.EXCELLENT.hasNumericValue());
        assertTrue(Grade.GOOD.hasNumericValue());
        assertTrue(Grade.SATISFACTORY.hasNumericValue());
        assertTrue(Grade.FAIL.hasNumericValue());
        assertFalse(Grade.PASS.hasNumericValue());
        assertFalse(Grade.FAIL_PASS.hasNumericValue());
    }

    @Test
    void testIsExcellent() {
        assertTrue(Grade.EXCELLENT.isExcellent());
        assertFalse(Grade.GOOD.isExcellent());
        assertFalse(Grade.SATISFACTORY.isExcellent());
    }

    @Test
    void testIsBad() {
        assertFalse(Grade.EXCELLENT.isBad());
        assertFalse(Grade.GOOD.isBad());
        assertTrue(Grade.SATISFACTORY.isBad());
        assertTrue(Grade.FAIL.isBad());
        assertFalse(Grade.PASS.isBad());
        assertTrue(Grade.FAIL_PASS.isBad());
    }

    @Test
    void testIsSatisfactory() {
        assertFalse(Grade.EXCELLENT.isSatisfactory());
        assertFalse(Grade.GOOD.isSatisfactory());
        assertTrue(Grade.SATISFACTORY.isSatisfactory());
        assertFalse(Grade.FAIL.isSatisfactory());
    }

    @Test
    void testDescription() {
        assertEquals("отлично", Grade.EXCELLENT.getDescription());
        assertEquals("хорошо", Grade.GOOD.getDescription());
        assertEquals("зачет", Grade.PASS.getDescription());
    }
}