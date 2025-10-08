package ru.nsu.gstubarev.expression.exceptions;

public class InvalidOperationException extends InvalidInputException{
    public InvalidOperationException(String message) {
        super("There is no operation: " + message);
    }
}
