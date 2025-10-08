package ru.nsu.gstubarev.expression;

/**
 * Represents a multiplication operation between two expressions.
 * Implements the mathematical operation: left * right
 */
public class Mul extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Constructs a multiplication expression representing left * right.
     *
     * @param left  the left operand of the multiplication
     * @param right the right operand of the multiplication
     */
    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Evaluates the multiplication expression by multiplying left and right operands.
     *
     * @param varEqValue string in format "var1=value1; var2=value2;..."
     * @return the product of left and right operand evaluations
     */
    @Override
    public double eval(String varEqValue) {
        return left.eval(varEqValue) * right.eval(varEqValue);
    }

    /**
     * Method to simplify the mul.
     *
     * @return simple expression
     */
    @Override
    public Expression simplify() {
        Expression simplLeft = left.simplify();
        Expression simplRight = right.simplify();

        if (simplLeft instanceof Number && simplRight instanceof Number) {
            double result = simplLeft.eval("") * simplRight.eval("");
            return new Number(result);
        }

        if ((simplLeft instanceof Number && simplLeft.eval("") == 0) ||
                (simplRight instanceof Number && simplRight.eval("") == 0)) {
            return new Number(0);
        }

        if (simplLeft instanceof Number && simplLeft.eval("") == 1) {
            return simplRight;
        }
        if (simplRight instanceof Number && simplRight.eval("") == 1) {
            return simplLeft;
        }

        return new Mul(simplLeft, simplRight);
    }

    /**
     * Computes the derivative of the multiplication expression using product rule.
     * The product rule: (fg)' = f'g + fg'
     *
     * @param variable the variable with respect to which to differentiate
     * @return new expression representing the derivative
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable)));
    }

    /**
     * Returns string representation of the multiplication expression in format "(left*right)".
     *
     * @return string representation of the multiplication
     */
    @Override
    public String toString() {
        return String.format("(%s*%s)", left.toString(), right.toString());
    }
}
