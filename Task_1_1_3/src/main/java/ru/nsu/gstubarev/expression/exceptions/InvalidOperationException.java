package ru.nsu.gstubarev.expression.exceptions;

/**
 * Thrown when mathematical operation is invalid or unsupported.
 */
public class InvalidOperationException extends InvalidInputException {
    /**
     * Constructor of exception about invalid operation.
     *
     * @param message the invalid operation symbol
     */
    public InvalidOperationException(String message) {
        super("There is no operation: " + message);
    }
}