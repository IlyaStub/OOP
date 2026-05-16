package ru.nsu.gstubarev.dsl.dataClasses;

import java.time.LocalDate;

/**
 * Represents a control point.
 */
public class Checkpoint {
    private final String name;
    private final LocalDate date;

    /**
     * Creates checkpoint with name.
     */
    public Checkpoint(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }

    /**
     * Returns checkpoint name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns checkpoint date.
     */
    public LocalDate getDate() {
        return date;
    }
}