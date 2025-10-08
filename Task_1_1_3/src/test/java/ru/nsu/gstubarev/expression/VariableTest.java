package ru.nsu.gstubarev.expression;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class VariableTest {

    @Test
    void testVariableEvaluation() {
        Variable var = new Variable("x");
        assertAll(
            () -> assertEquals(10.0, var.eval("x=10"), 0.001),
            () -> assertEquals(5.123, var.eval("x=5.123; y=3"), 0.001)
        );
    }

    @Test
    void testVariableEvaluationMultipleVars() {
        Variable variableY = new Variable("y");
        assertEquals(3.0, variableY.eval("x=5; y=3; z=7"), 0.001);
    }

    @Test
    void testVariableDerivativeSameVariable() {
        Variable variable = new Variable("x");
        Expression derivative = variable.derivative("x");
        assertEquals(1, derivative.eval("x=2"), 0.001);
    }

    @Test
    void testVariableDerivativeDifferentVariable() {
        Variable variable = new Variable("x");
        Expression derivative = variable.derivative("y");
        assertEquals(0, derivative.eval("x=10"), 0.001);
    }

    @Test
    void testVariableToString() {
        Variable variable = new Variable("alpha");
        assertEquals("alpha", variable.toString());
    }
}