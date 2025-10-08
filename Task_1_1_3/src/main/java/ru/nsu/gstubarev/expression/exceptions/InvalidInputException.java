package ru.nsu.gstubarev.expression.exceptions;

/**
 * Thrown when expression input is invalid.
 */
public class InvalidInputException extends RuntimeException {
    /**
     * @param message description of the input error
     */
    public InvalidInputException(String message) {
        super("Invalid input: " + message);
    }
}
