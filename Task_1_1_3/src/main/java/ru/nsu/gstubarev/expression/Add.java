package ru.nsu.gstubarev.expression;

/**
 * Represents an addition operation between two expressions.
 * Implements the mathematical operation: left + right
 */
public class Add extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Construct an addition expression represent left + right.
     *
     * @param left  the left operand of the addition
     * @param right the right operand of the addition
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Evaluates the addition expression by summing the values of left and right operands.
     *
     * @param varEqValue string format "var1=value1; var2=value2;..."
     * @return the sum of left and right operand evaluations
     */
    @Override
    public double eval(String varEqValue) {
        return left.eval(varEqValue) + right.eval(varEqValue);
    }

    @Override
    public Expression simplify() {
        Expression simplLeft = left.simplify();
        Expression simplRight = right.simplify();

        if (simplLeft instanceof Number && simplRight instanceof Number) {
            double result = simplLeft.eval("") + simplRight.eval("");
            return new Number(result);
        }

        if (simplLeft instanceof Number && simplLeft.eval("") == 0) {
            return simplRight;
        }
        if (simplRight instanceof Number && simplRight.eval("") == 0) {
            return simplLeft;
        }

        return new Add(simplLeft, simplRight);
    }

    /**
     * Computes the derivative of the addition expression.
     * The derivative of a sum is the sum of the derivatives.
     *
     * @param variable the variable with respect to which to differentiate
     * @return new expression representing the derivative
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(left.derivative(variable), right.derivative(variable));
    }

    /**
     * Returns string representation of the addition expression in format "(left+right)".
     *
     * @return string representation of the addition
     */
    @Override
    public String toString() {
        return String.format("(%s+%s)", left.toString(), right.toString());
    }
}
