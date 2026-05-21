package ru.nsu.gstubarev.topology;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test.
 */
class MessageParserTest {
    @Test
    void testBuildCheckWithSingleNumber() {
        long[] chunk = {42L};
        String result = MessageParser.buildCheck(chunk);
        assertEquals("CHECK 42", result);
    }

    @Test
    void testBuildCheckWithMultipleNumbers() {
        long[] chunk = {1L, 2L, 3L, 4L, 5L};
        String result = MessageParser.buildCheck(chunk);
        assertEquals("CHECK 1,2,3,4,5", result);
    }

    @Test
    void testBuildCheckWithEmptyArray() {
        long[] chunk = {};
        String result = MessageParser.buildCheck(chunk);
        assertEquals("CHECK ", result);
    }

    @Test
    void testParseCheckWithSingleNumber() {
        String line = "CHECK 42";
        long[] result = MessageParser.parseCheck(line);
        assertArrayEquals(new long[]{42L}, result);
    }

    @Test
    void testParseCheckWithMultipleNumbers() {
        String line = "CHECK 1,2,3,4,5";
        long[] result = MessageParser.parseCheck(line);
        assertArrayEquals(new long[]{1L, 2L, 3L, 4L, 5L}, result);
    }

    @Test
    void testParseCheckWithSpaces() {
        String line = "CHECK 10, 20, 30";
        long[] result = MessageParser.parseCheck(line);
        assertArrayEquals(new long[]{10L, 20L, 30L}, result);
    }

    @Test
    void testBuildResultTrue() {
        String result = MessageParser.buildResult(true);
        assertEquals("RESULT true", result);
    }

    @Test
    void testBuildResultFalse() {
        String result = MessageParser.buildResult(false);
        assertEquals("RESULT false", result);
    }

    @Test
    void testParseResultTrue() {
        boolean result = MessageParser.parseResult("RESULT true");
        assertTrue(result);
    }

    @Test
    void testParseResultFalse() {
        boolean result = MessageParser.parseResult("RESULT false");
        assertFalse(result);
    }

    @Test
    void testParseResultWithSpaces() {
        boolean result = MessageParser.parseResult("RESULT  true");
        assertTrue(result);
    }

    @Test
    void testParseResultThrowsOnInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            MessageParser.parseResult("CHECK 1,2,3");
        });
    }
}