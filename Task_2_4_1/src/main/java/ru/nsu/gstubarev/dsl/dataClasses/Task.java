package ru.nsu.gstubarev.dsl.dataClasses;

import java.time.LocalDate;

/**
 * Represents a course task.
 */
public class Task {
    private final int id;
    private final String name;
    private final int maxScores;
    private final LocalDate softDeadline;
    private final LocalDate hardDeadline;

    /**
     * Constructs a task definition.
     */
    public Task(int id, String name, int maxScores,
                LocalDate softDeadline, LocalDate hardDeadline) {
        this.id = id;
        this.name = name;
        this.maxScores = maxScores;
        this.softDeadline = softDeadline;
        this.hardDeadline = hardDeadline;
    }

    /**
     * Returns task identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns task name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns maximum scores.
     */
    public int getMaxScores() {
        return maxScores;
    }

    /**
     * Returns soft deadline date.
     */
    public LocalDate getSoftDeadline() {
        return softDeadline;
    }

    /**
     * Returns hard deadline date.
     */
    public LocalDate getHardDeadline() {
        return hardDeadline;
    }

    /**
     * Returns task string representation.
     */
    @Override
    public String toString() {
        return "\n\tTask{" + "id=" + id + ", name='"
                + name + '\'' + ", maxScores="
                + maxScores + ", softDeadline="
                + softDeadline + ", hardDeadline=" + hardDeadline
                + "}";
    }
}