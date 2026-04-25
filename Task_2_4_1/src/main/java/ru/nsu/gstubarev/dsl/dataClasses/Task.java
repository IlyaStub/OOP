package ru.nsu.gstubarev.dsl.dataClasses;

import java.time.LocalDate;

public class Task {
    private final int id;
    private final String name;
    private final int maxScores;
    private final LocalDate softDeadline;
    private final LocalDate hardDeadline;

    public Task(int id, String name, int maxScores,
                LocalDate softDeadline, LocalDate hardDeadline) {
        this.id = id;
        this.name = name;
        this.maxScores = maxScores;
        this.softDeadline = softDeadline;
        this.hardDeadline = hardDeadline;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxScores() {
        return maxScores;
    }

    public LocalDate getSoftDeadline() {
        return softDeadline;
    }

    public LocalDate getHardDeadline() {
        return hardDeadline;
    }

    @Override
    public String toString() {
        return "\n\tTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxScores=" + maxScores +
                ", softDeadline=" + softDeadline +
                ", hardDeadline=" + hardDeadline +
                "}";
    }
}
