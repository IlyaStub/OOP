package ru.nsu.gstubarev.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SubTest {

    @Test
    void testSubEvaluation() {
        Expression sub = new Sub(new Number(10), new Number(4));
        assertEquals(6, sub.eval("x=10"));
    }

    @Test
    void testSubWithVariables() {
        Expression sub = new Sub(new Variable("x"), new Number(3));
        assertEquals(2, sub.eval("x=5"));
    }

    @Test
    void testSubDerivative() {
        Expression sub = new Sub(new Variable("x"), new Number(3));
        Expression derivative = sub.derivative("x");
        assertEquals(1, derivative.eval("x=5"));
    }

    @Test
    void testSubToString() {
        Expression sub = new Sub(new Variable("x"), new Number(3));
        assertEquals("(x-3.0)", sub.toString());
    }
}