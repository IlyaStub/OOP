package ru.nsu.gstubarev.topology;

/**
 * Text protocol constants for Master-Worker communication.
 */
public enum Command {
    REGISTER("REGISTER"),
    OK("OK"),
    CHECK("CHECK"),
    RESULT("RESULT"),
    PING("PING"),
    PONG("PONG");

    private final String text;

    /**
     * .
     */
    Command(String text) {
        this.text = text;
    }

    /**
     * Returns the string representation of this command.
     */
    public String getText() {
        return text;
    }
}