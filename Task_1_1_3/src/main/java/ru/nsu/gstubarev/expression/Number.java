package ru.nsu.gstubarev.expression;

/**
 * Represents a constant numerical value in expressions.
 * Immutable class storing a double precision floating-point number.
 */
public class Number extends Expression{
    private final double number;

    /**
     * Constructs a constant number expression.
     *
     * @param number the constant value
     */
    public Number(double number) {
        this.number = number;
    }

    /**
     * Returns the constant value regardless of variable assignments.
     *
     * @param varEqValue string containing variable assignments (ignored for constants)
     * @return the constant value
     */
    @Override
    public double eval(String varEqValue){
        return number;
    }

    /**
     * The derivative of a constant is zero.
     *
     * @param variable the variable with respect to which to differentiate (ignored for constants)
     * @return a Number expression with value 0
     */
    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    /**
     * Returns string representation of the number.
     *
     * @return the number as a string
     */
    @Override
    public String toString(){
        return String.valueOf(number);
    }
}
