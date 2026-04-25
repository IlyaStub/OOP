package ru.nsu.gstubarev.dsl.dataClasses;

import java.util.LinkedList;
import java.util.List;

public class Config {
    private List<Task> tasks = new LinkedList<>();

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return "Config{" +
                "tasks=" + tasks +
                '}';
    }
}
