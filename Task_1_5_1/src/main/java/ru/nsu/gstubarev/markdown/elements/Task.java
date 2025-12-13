package ru.nsu.gstubarev.markdown.elements;

import ru.nsu.gstubarev.markdown.Element;
import ru.nsu.gstubarev.markdown.exceptions.EmptyElementException;

/**
 * Represents a task (checkbox) in Markdown.
 */
public class Task extends Element {
    private final String text;
    private final boolean isCompleted;

    /**
     * Creates a new task.
     *
     * @param text        task description
     * @param isCompleted completion status
     */
    public Task(String text, boolean isCompleted) {
        if (text == null || text.trim().isEmpty()) {
            throw new EmptyElementException("Task text cannot be empty");
        }
        this.text = text.trim();
        this.isCompleted = isCompleted;
    }

    @Override
    public String serialize() {
        String checkbox = isCompleted ? "[x]" : "[ ]";
        return checkbox + " " + text;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Task)) {
            return false;
        }
        Task other = (Task) obj;
        return text.equals(other.text) && isCompleted == other.isCompleted;
    }

    /**
     * Creates a builder for Task.
     *
     * @param text task description
     * @return new builder instance
     */
    public static TaskBuilder builder(String text) {
        return new TaskBuilder(text);
    }

    /**
     * Builder for Task.
     */
    public static class TaskBuilder {
        private final String text;
        private boolean isCompleted = false;

        /**
         * Creates a new TaskBuilder.
         *
         * @param text task description
         */
        public TaskBuilder(String text) {
            this.text = text;
        }

        /**
         * Marks the task as completed.
         *
         * @return this builder
         */
        public TaskBuilder completed() {
            this.isCompleted = true;
            return this;
        }

        /**
         * Builds the Task.
         *
         * @return new Task instance
         */
        public Task build() {
            return new Task(text, isCompleted);
        }
    }
}