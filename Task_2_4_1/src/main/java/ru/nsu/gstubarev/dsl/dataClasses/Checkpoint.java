package ru.nsu.gstubarev.dsl.dataClasses;

import java.time.LocalDate;

public class Checkpoint {
    private final String name;
    private final LocalDate date;

    public Checkpoint(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }
}
