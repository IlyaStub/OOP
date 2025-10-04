package ru.nsu.gstubarev.expression;

/**
 * Abstract base class for all mathematical expressions.
 * Defines the common interface for expression evaluation, differentiation,
 * and string representation.
 */
public abstract class Expression {
    /**
     * Evaluates the expression with given variable values.
     *
     * @param varEqValue string  in format "var1=value1; var2=value2;..."
     * @return the numerical result of the expression evaluation
     */
    public abstract double eval(String varEqValue);

    /**
     * Computes the derivative of the expression with respect to the given variable.
     *
     * @param variable the variable with respect to which to differentiate
     * @return new expression representing the derivative
     */
    public abstract Expression derivative(String variable);

    /**
     * Returns string representation of the expression.
     *
     * @return string representation of the expression
     */
    public abstract String toString();

    /**
     * Prints the string representation of the expression to standard output.
     */
    public void print() {
        System.out.println(this.toString());
    }
}
