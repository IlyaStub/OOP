package ru.nsu.gstubarev.dsl.exceptions;

/**
 * For error downloading utils.
 */
public class ToolDownloadException extends RuntimeException {
    /**
     * Processing method.
     */
    public ToolDownloadException(String message, Throwable cause) {
        super(message, cause);
    }
}