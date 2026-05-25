package ru.nsu.gstubarev.dsl.exceptions;

/**
 * For HTML report generation errors.
 */
public class ReportGenerationException extends RuntimeException {
    /**
     * Processing method.
     */
    public ReportGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}