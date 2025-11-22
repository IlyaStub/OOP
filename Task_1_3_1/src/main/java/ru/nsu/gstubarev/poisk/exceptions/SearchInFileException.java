package ru.nsu.gstubarev.poisk.exceptions;

import java.io.IOException;

/**
 * Exception for file-related errors during substring search.
 */
public class SearchInFileException extends RuntimeException {
    /**
     * Constructs exception with message and cause.
     *
     * @param message the detail message
     * @param cause the IOException cause
     */
    public SearchInFileException(String message, IOException cause) {
        super(message, cause);
    }
}
