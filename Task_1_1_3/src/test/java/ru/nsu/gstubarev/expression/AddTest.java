package ru.nsu.gstubarev.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AddTest {
    @Test
    void testAddEvaluation() {
        Expression add = new Add(new Number(3), new Number(4));
        assertEquals(7, add.eval("x=10"));
    }

    @Test
    void testAddWithVariables() {
        Expression add = new Add(new Variable("x"), new Number(2));
        assertEquals(7, add.eval("x=5"));
    }

    @Test
    void testAddDerivative() {
        Expression add = new Add(new Variable("x"), new Number(2));
        Expression derivative = add.derivative("x");
        assertEquals(1, derivative.eval("x=5"));
    }

    @Test
    void testAddToString() {
        Expression add = new Add(new Number(3), new Variable("x"));
        assertEquals("(3.0+x)", add.toString());
    }
}