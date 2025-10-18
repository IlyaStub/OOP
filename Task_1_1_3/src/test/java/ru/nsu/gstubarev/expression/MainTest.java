package ru.nsu.gstubarev.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void testComplexExpressionEvaluation() {
        Expression complex = new Add(
                new Number(3),
                new Div(new Number(2), new Variable("x"))
        );
        assertEquals(4.0, complex.eval("x=2"), 0.001);
    }

    @Test
    void testComplexExpressionDerivative() {
        Expression complex = new Add(
                new Number(3),
                new Div(new Number(2), new Variable("x"))
        );
        Expression derivative = complex.derivative("x");
        assertEquals(-0.5, derivative.eval("x=2"), 0.001);
    }

    @Test
    void testNestedOperations() {
        Expression nested = new Mul(
                new Add(new Variable("x"), new Number(2)),
                new Sub(new Variable("x"), new Number(1))
        );
        assertEquals(10.0, nested.eval("x=3"), 0.001);
    }

    @Test
    void testPrintMethod() {
        Expression expr = new Add(new Number(1), new Variable("x"));
        expr.print();
    }

    @Test
    void testMultipleVariableExpression() {
        Expression expr = new Add(new Variable("x"), new Variable("y"));
        assertEquals(7.0, expr.eval("x=3; y=4"), 0.001);
    }

    @Test
    void testExpressionWithConstantsOnly() {
        Expression expr = new Div(
                new Mul(
                        new Add(new Number(3), new Number(4)),
                        new Sub(new Number(5), new Number(2))
                ),
                new Number(2)
        );
        assertEquals(10.5, expr.eval(""));
    }

    @Test
    void testChainOfOperations() {
        Expression chain = new Sub(
                new Add(new Variable("x"), new Mul(new Number(2), new Variable("x"))),
                new Number(1)
        );
        assertEquals(8.0, chain.eval("x=3"), 0.001);
    }

    @Test
    void testMain() {
        Main.main(new String[]{});
        assertTrue(true);
    }
}