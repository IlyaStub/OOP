package ru.nsu.gstubarev.expression;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SimplifyTest {

    @Test
    void testNumberSimplification() {
        Number num = new Number(5.5);
        Expression simplified = num.simplify();
        assertEquals(5.5, simplified.eval(""), 0.001);
    }

    @Test
    void testVariableSimplification() {
        Variable var = new Variable("x");
        Expression simplified = var.simplify();
        assertEquals("x", simplified.toString());
    }

    @Test
    void testAddWithConstants() {
        Expression add = new Add(new Number(3), new Number(4));
        Expression simplified = add.simplify();
        assertEquals(7.0, simplified.eval(""), 0.001);
    }

    @Test
    void testAddWithZero() {
        Expression add = new Add(new Number(0), new Variable("x"));
        Expression simplified = add.simplify();
        Expression add2 = new Add(new Add(new Variable("x"), new Number(2)), new Number(0));
        Expression simplified2 = add2.simplify();
        assertAll(
                () -> assertEquals("x", simplified.toString()),
                () -> assertEquals("(x+2.0)", simplified2.toString())
        );
    }

    @Test
    void testMulWithConstants() {
        Expression mul = new Mul(new Number(3), new Number(4));
        Expression simplified = mul.simplify();
        assertEquals(12.0, simplified.eval(""), 0.001);
    }

    @Test
    void testMulWithZero() {
        Expression mul = new Mul(new Number(0), new Variable("x"));
        Expression simplified = mul.simplify();
        Expression mul2 = new Mul(new Variable("x"), new Number(0));
        Expression simplified2 = mul2.simplify();
        assertAll(
                () -> assertEquals(0.0, simplified.eval(""), 0.001),
                () -> assertEquals(0.0, simplified2.eval(""), 0.001)
        );
    }

    @Test
    void testMulWithOne() {
        Expression mul = new Mul(new Number(1), new Variable("x"));
        Expression simplified = mul.simplify();
        Expression mul2 = new Mul(new Variable("x"), new Number(1));
        Expression simplified2 = mul2.simplify();

        assertAll(
                () -> assertEquals("x", simplified.toString()),
                () -> assertEquals("x", simplified2.toString())
        );
    }

    @Test
    void testSubWithConstants() {
        Expression sub = new Sub(new Number(5), new Number(2));
        Expression simplified = sub.simplify();
        assertEquals(3.0, simplified.eval(""), 0.001);
    }

    @Test
    void testSubSameExpressions() {
        Expression sub = new Sub(new Variable("x"), new Variable("x"));
        Expression simplified = sub.simplify();
        Expression nested = new Sub(
                new Add(new Variable("x"), new Number(1)),
                new Add(new Variable("x"), new Number(1))
        );
        Expression simplifiedNested = nested.simplify();
        assertAll(
                () -> assertEquals(0.0, simplified.eval(""), 0.001),
                () -> assertEquals(0.0, simplifiedNested.eval(""), 0.001)
        );
    }

    @Test
    void testSubWithZero() {
        Expression sub = new Sub(new Variable("x"), new Number(0));
        Expression simplified = sub.simplify();
        assertEquals("x", simplified.toString());
    }

    @Test
    void testDivWithConstants() {
        Expression div = new Div(new Number(10), new Number(2));
        Expression simplified = div.simplify();
        assertEquals(5.0, simplified.eval(""), 0.001);
    }

    @Test
    void testDivWithOne() {
        Expression div = new Div(new Variable("x"), new Number(1));
        Expression simplified = div.simplify();
        assertEquals("x", simplified.toString());
    }

    @Test
    void testDivSameExpressions() {
        Expression div = new Div(new Variable("x"), new Variable("x"));
        Expression simplified = div.simplify();
        assertEquals(1.0, simplified.eval(""), 0.001);
    }

    @Test
    void testDivZeroBySomething() {
        Expression div = new Div(new Number(0), new Variable("x"));
        Expression simplified = div.simplify();
        assertEquals(0.0, simplified.eval(""), 0.001);
    }

    @Test
    void testComplexSimplification() {
        Expression complex = new Add(
                new Mul(new Number(2), new Number(0)),
                new Sub(new Variable("x"), new Variable("x"))
        );
        Expression simplified = complex.simplify();
        assertEquals(0.0, simplified.eval(""), 0.001);
    }
}