package ru.nsu.gstubarev.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ExpressionParserTest {
    @Test
    void testParseSimpleAddition() {
        ExpressionParser parser = new ExpressionParser();
        Expression expr = parser.parse("(2+3)");
        assertEquals(5.0, expr.eval(""), 0.001);
    }

    @Test
    void testParseSimpleSubtraction() {
        ExpressionParser parser = new ExpressionParser();
        Expression expr = parser.parse("(5-2)");
        assertEquals(3.0, expr.eval(""), 0.001);
    }

    @Test
    void testParseSimpleMultiplication() {
        ExpressionParser parser = new ExpressionParser();
        Expression expr = parser.parse("(3*4)");
        assertEquals(12.0, expr.eval(""), 0.001);
    }

    @Test
    void testParseSimpleDivision() {
        ExpressionParser parser = new ExpressionParser();
        Expression expr = parser.parse("(10/2)");
        assertEquals(5.0, expr.eval(""), 0.001);
    }

    @Test
    void testParseNumber() {
        ExpressionParser parser = new ExpressionParser();
        Expression expr = parser.parse("42");
        assertEquals(42.0, expr.eval(""), 0.001);
    }

    @Test
    void testParseVariable() {
        ExpressionParser parser = new ExpressionParser();
        Expression expr = parser.parse("x");
        assertEquals("x", expr.toString());
        assertEquals(5.0, expr.eval("x=5"), 0.001);
    }

    @Test
    void testParseNestedExpression() {
        ExpressionParser parser = new ExpressionParser();
        Expression expr = parser.parse("(3+(2*x))");
        assertEquals(23.0, expr.eval("x=10"), 0.001);
    }

    @Test
    void testParseComplexNested() {
        ExpressionParser parser = new ExpressionParser();
        Expression expr = parser.parse("((x+1)*(x-1))");
        assertEquals(8.0, expr.eval("x=3"), 0.001);
    }

    @Test
    void testParseDeeplyNested() {
        ExpressionParser parser = new ExpressionParser();
        Expression expr = parser.parse("((1+2)*(3+(4*x)))");
        assertEquals(45.0, expr.eval("x=3"), 0.001);
    }

    @Test
    void testParseWithMultipleVariables() {
        ExpressionParser parser = new ExpressionParser();
        Expression expr = parser.parse("((x+y)*(x-y))");
        assertEquals(16.0, expr.eval("x=5; y=3"), 0.001);
    }

    @Test
    void testParseAssignmentExample() {
        ExpressionParser parser = new ExpressionParser();
        Expression expr = parser.parse("(3+(2*x))");
        assertEquals(23.0, expr.eval("x=10"), 0.001);

        Expression derivative = expr.derivative("x");
        assertEquals(2.0, derivative.eval("x=5"), 0.001);
    }

    @Test
    void testParseUnexpectedEnd() {
        ExpressionParser parser = new ExpressionParser();
        assertThrows(RuntimeException.class, () -> {
            parser.parse("(2+");
        });
    }

    @Test
    void testParseInvalidOperation() {
        ExpressionParser parser = new ExpressionParser();
        assertThrows(RuntimeException.class, () -> {
            parser.parse("(2&3)");
        });
    }

    @Test
    void testParseMissingClosingParenthesis() {
        ExpressionParser parser = new ExpressionParser();
        assertThrows(RuntimeException.class, () -> {
            parser.parse("(2+3");
        });
    }

    @Test
    void testParseEmptyString() {
        ExpressionParser parser = new ExpressionParser();
        assertThrows(RuntimeException.class, () -> {
            parser.parse("");
        });
    }

    @Test
    void testParseDigitNumber() {
        ExpressionParser parser = new ExpressionParser();
        Expression expr = parser.parse("123");
        assertEquals(123.0, expr.eval(""), 0.001);
    }

    @Test
    void testParseComplexDerivative() {
        ExpressionParser parser = new ExpressionParser();
        Expression expr = parser.parse("((x*x)+(2*x))");
        Expression derivative = expr.derivative("x");
        assertEquals(8.0, derivative.eval("x=3"), 0.001);
    }
}