package ru.nsu.gstubarev.dsl.exceptions;

/**
 * For Checkstyle errors.
 */
public class StyleCheckException extends RuntimeException {
    /**
     * Processing method.
     */
    public StyleCheckException(String message, Throwable cause) {
        super(message, cause);
    }
}