package ru.nsu.gstubarev.dsl.dataclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CheckResultTest {

    @Test
    public void testGetTestsStringNotCompiled() {
        CheckResult result = new CheckResult();
        result.compiled = false;

        assertEquals("-", result.getTestsString());
    }

    @Test
    public void testGetTestsStringCompiled() {
        CheckResult result = new CheckResult();
        result.compiled = true;
        result.testsPassed = 10;
        result.testsFailed = 2;
        result.testsSkipped = 1;

        assertEquals("10/2/1", result.getTestsString());
    }
}