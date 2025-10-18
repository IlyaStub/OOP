package ru.nsu.gstubarev.expression;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class NumberTest {

    @Test
    void testNumberEvaluation() {
        Number num = new Number(5.123);
        assertAll(
                () -> assertEquals(5.123, num.eval("x=10")),
                () -> assertEquals(5.123, num.eval(""), 0.001)
        );
    }

    @Test
    void testNumberDerivative() {
        Number num = new Number(5.123);
        Expression derivative = num.derivative("x");
        assertEquals(0, derivative.eval("x=10"), 0.001);
    }

    @Test
    void testNumberToString() {
        Number num = new Number(3.14);
        assertEquals("3.14", num.toString());
    }
}