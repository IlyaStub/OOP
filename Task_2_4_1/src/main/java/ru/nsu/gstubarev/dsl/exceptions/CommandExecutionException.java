package ru.nsu.gstubarev.dsl.exceptions;

/**
 * For errors when executing console commands.
 */
public class CommandExecutionException extends RuntimeException {
    /**
     * Processing method.
     */
    public CommandExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}