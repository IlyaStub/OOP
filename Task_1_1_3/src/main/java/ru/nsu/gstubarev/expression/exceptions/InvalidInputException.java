package ru.nsu.gstubarev.expression.exceptions;

public class InvalidInputException extends RuntimeException{
    public InvalidInputException(String message) {
        super("Invalid input" + message);
    }
}
