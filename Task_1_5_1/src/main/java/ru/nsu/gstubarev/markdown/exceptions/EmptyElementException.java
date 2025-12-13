package ru.nsu.gstubarev.markdown.exceptions;

/**
 * Thrown when an element cannot be empty.
 */
public class EmptyElementException extends RuntimeException {
    /**
     * Creates a new exception.
     *
     * @param message error message
     */
    public EmptyElementException(String message) {
        super(message);
    }
}