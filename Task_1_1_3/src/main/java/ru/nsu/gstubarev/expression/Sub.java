package ru.nsu.gstubarev.expression;

/**
 * Represents a subtraction operation between two expressions.
 * Implements the mathematical operation: left - right
 */
public class Sub extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Constructs a subtraction expression representing left - right.
     *
     * @param left  the left operand of the subtraction
     * @param right the right operand of the subtraction
     */
    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Evaluates the subtraction expression by subtracting right operand from left operand.
     *
     * @param varEqValue string in format "var1=value1; var2=value2;..."
     * @return the difference of left and right operand evaluations
     */
    @Override
    public double eval(String varEqValue) {
        return left.eval(varEqValue) - right.eval(varEqValue);
    }

    /**
     * Method to simplify the sub.
     *
     * @return simple expression
     */
    @Override
    public Expression simplify() {
        Expression simplLeft = left.simplify();
        Expression simplRight = right.simplify();

        if (simplLeft instanceof Number && simplRight instanceof Number) {
            double result = simplLeft.eval("") - simplRight.eval("");
            return new Number(result);
        }

        if (simplLeft.toString().equals(simplRight.toString())) {
            return new Number(0);
        }

        if (simplRight instanceof Number && simplRight.eval("") == 0) {
            return simplLeft;
        }

        return new Sub(simplLeft, simplRight);
    }

    /**
     * Computes the derivative of the subtraction expression.
     * The derivative of a difference is the difference of the derivatives.
     *
     * @param variable the variable with respect to which to differentiate
     * @return new expression representing the derivative
     */
    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    /**
     * Returns string representation of the subtraction expression in format "(left-right)".
     *
     * @return string representation of the subtraction
     */
    @Override
    public String toString() {
        return String.format("(%s-%s)", left.toString(), right.toString());
    }
}
