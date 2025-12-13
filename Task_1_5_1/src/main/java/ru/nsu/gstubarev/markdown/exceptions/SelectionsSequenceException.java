package ru.nsu.gstubarev.markdown.exceptions;

/**
 * Thrown when element parameters are invalid.
 */
public class SelectionsSequenceException extends RuntimeException {
    /**
     * Creates a new exception.
     *
     * @param message error message
     */
    public SelectionsSequenceException(String message) {
        super(message);
    }
}