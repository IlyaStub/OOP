package ru.nsu.gstubarev.dsl.exceptions;

/**
 * For errors in parsing XML test reports.
 */
public class TestParsingException extends RuntimeException {
    /**
     * Processing method.
     */
    public TestParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}