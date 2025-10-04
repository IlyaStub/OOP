package ru.nsu.gstubarev.expression;

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
        return left.eval(varEqValue) / right.eval(varEqValue);
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
