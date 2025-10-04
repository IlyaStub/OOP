package ru.nsu.gstubarev.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DivTest {
    @Test
    void testDivEvaluation() {
        Expression div = new Div(new Number(10), new Number(2));
        assertEquals(5, div.eval("x=10"));
    }

    @Test
    void testDivWithVariables() {
        Expression div = new Div(new Variable("x"), new Number(2));
        assertEquals(2.5, div.eval("x=5"));
    }

    @Test
    void testDivDerivative() {
        Expression div = new Div(new Variable("x"), new Number(2));
        Expression derivative = div.derivative("x");
        assertEquals(0.5, derivative.eval("x=5"));
    }

    @Test
    void testDivDerivativeComplex() {
        Expression div = new Div(new Variable("x"), new Variable("x"));
        Expression derivative = div.derivative("x");
        assertEquals(0, derivative.eval("x=5"));
    }

    @Test
    void testDivToString() {
        Expression div = new Div(new Variable("x"), new Number(2));
        assertEquals("(x/2.0)", div.toString());
    }
}