package ru.nsu.gstubarev.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.expression.exceptions.DivisionByZeroExeption;

class DivTest {
    @Test
    void testDivEvaluation() {
        Expression div = new Div(new Number(10), new Number(2));
        assertEquals(5, div.eval("x=10"), 0.001);
    }

    @Test
    void testDivWithVariables() {
        Expression div = new Div(new Variable("x"), new Number(2));
        assertEquals(2.5, div.eval("x=5"), 0.001);
    }

    @Test
    void testDivDerivative() {
        Expression div = new Div(new Variable("x"), new Number(2));
        Expression derivative = div.derivative("x");
        assertEquals(0.5, derivative.eval("x=5"), 0.001);
    }

    @Test
    void testDivDerivativeComplex() {
        Expression div = new Div(new Variable("x"), new Variable("x"));
        Expression derivative = div.derivative("x");
        assertEquals(0, derivative.eval("x=5"), 0.001);
    }

    @Test
    void testDivToString() {
        Expression div = new Div(new Variable("x"), new Number(2));
        assertEquals("(x/2.0)", div.toString());
    }

    @Test
    void testDivZero() {
        Expression div = new Div(new Variable("x"), new Number(0));
        assertThrows(RuntimeException.class, () -> {
            div.eval("x=3");
        });
    }

    @Test
    void testDivByZeroWithVariable() {
        Expression div = new Div(new Variable("x"), new Number(0));

        DivisionByZeroExeption exception = assertThrows(DivisionByZeroExeption.class, () -> {
            div.eval("x=5");
        });

        assertEquals("You can't divide by zero.", exception.getMessage());
    }

    @Test
    void testDivByZeroWithComplexExpression() {
        Expression div = new Div(new Variable("x"), new Sub(new Number(5), new Number(5)));

        DivisionByZeroExeption exception = assertThrows(DivisionByZeroExeption.class, () -> {
            div.eval("x=10");
        });

        assertEquals("You can't divide by zero.", exception.getMessage());
    }
}