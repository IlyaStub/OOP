package ru.nsu.gstubarev.expression;

import ru.nsu.gstubarev.expression.exceptions.DivisionByZeroExeption;

/**
 * Represents a division operation between two expressions.
 * Implements the mathematical operation: left / right
 */
public class Div extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Constructs a division expression representing left / right.
     *
     * @param left  the numerator expression
     * @param right the denominator expression
     */
    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Evaluates the division expression by dividing left operand by right operand.
     *
     * @param varEqValue string in format "var1=value1; var2=value2; ..."
     * @return the quotient of left and right operand evaluations
     */
    @Override
    public double eval(String varEqValue) {
        if (right.eval(varEqValue) == 0) {
            throw new DivisionByZeroExeption("You can't divide by zero.");
        }
        return left.eval(varEqValue) / right.eval(varEqValue);
    }

    /**
     * Method to simplify the div.
     *
     * @return simple expression
     */
    @Override
    public Expression simplify() {
        Expression simplLeft = left.simplify();
        Expression simplRight = right.simplify();

        if (simplLeft instanceof Number && simplRight instanceof Number) {
            try {
                double result = simplLeft.eval("") / simplRight.eval("");
                return new Number(result);
            } catch (ArithmeticException e) {
                System.out.println(e);
            }
        }
        if (simplLeft instanceof Number && simplLeft.eval("") == 0) {
            return new Number(0);
        }

        if (simplLeft.toString().equals(simplRight.toString())) {
            return new Number(1);
        }

        if (simplRight instanceof Number && simplRight.eval("") == 1) {
            return simplLeft;
        }

        return new Div(simplLeft, simplRight);
    }

    /**
     * Computes the derivative of the division expression using quotient rule.
     * The quotient rule: (f/g)' = (f'g - fg') / (g²)
     *
     * @param variable the variable with respect to which to differentiate
     * @return new expression representing the derivative
     */
    @Override
    public Expression derivative(String variable) {
        return new Div(new Sub(new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable))), new Mul(right, right));
    }

    /**
     * Returns string representation of the division expression in format "(left/right)".
     *
     * @return string representation of the division
     */
    @Override
    public String toString() {
        return String.format("(%s/%s)", left.toString(), right.toString());
    }
}
