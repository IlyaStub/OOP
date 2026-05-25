package ru.nsu.gstubarev.dsl.exceptions;

/**
 * For any errors when working with Git.
 */
public class GitOperationException extends RuntimeException {
    /**
     * Processing method.
     */
    public GitOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}