package ru.nsu.gstubarev.expression.exceptions;

/**
 * Thrown when expression input is invalid.
 */
public class InvalidInputException extends RuntimeException {
    /**
     * Constructor of exception about invalid input.
     *
     * @param message description of the input error
     */
    public InvalidInputException(String message) {
        super("Invalid input: " + message);
    }
}
