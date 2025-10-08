package ru.nsu.gstubarev.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MulTest {
    @Test
    void testMulEvaluation() {
        Expression mul = new Mul(new Number(3), new Number(4));
        assertEquals(12, mul.eval("x=10"), 0.001);
    }

    @Test
    void testMulWithVariables() {
        Expression mul = new Mul(new Variable("x"), new Number(2));
        assertEquals(10, mul.eval("x=5"), 0.001);
    }

    @Test
    void testMulDerivative() {
        Expression mul = new Mul(new Variable("x"), new Number(2));
        Expression derivative = mul.derivative("x");
        assertEquals(2, derivative.eval("x=5"), 0.001);
    }

    @Test
    void testMulDerivativeComplex() {
        Expression mul = new Mul(new Variable("x"), new Variable("x"));
        Expression derivative = mul.derivative("x");
        assertEquals(10, derivative.eval("x=5"), 0.001);
    }

    @Test
    void testMulToString() {
        Expression mul = new Mul(new Number(2), new Variable("x"));
        assertEquals("(2.0*x)", mul.toString());
    }
}