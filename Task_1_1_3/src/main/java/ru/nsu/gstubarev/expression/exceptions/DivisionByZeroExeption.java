package ru.nsu.gstubarev.expression.exceptions;

public class DivisionByZeroExeption extends RuntimeException{
    /**
     * Constructor of exception about division by zero.
     *
     * @param message the invalid operation symbol
     */
    public DivisionByZeroExeption(String message) {

        super(message);
    }
}
