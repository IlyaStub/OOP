package ru.nsu.gstubarev.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    void serialize() {
        Task todo = new Task("Buy milk", false);
        assertEquals("[ ] Buy milk", todo.serialize());

        Task done = new Task("Buy milk", true);
        assertEquals("[x] Buy milk", done.serialize());

        Task builtTodo = Task.builder("Write tests").build();
        assertEquals("[ ] Write tests", builtTodo.serialize());

        Task builtDone = Task.builder("Write tests").completed().build();
        assertEquals("[x] Write tests", builtDone.serialize());
    }

    @Test
    void testEquals() {
        Task task1 = new Task("Task", false);
        Task task2 = new Task("Task", false);
        Task task3 = new Task("Task", true);
        Task task4 = new Task("Different", false);

        assertEquals(task1, task2);
        assertNotEquals(task1, task3);
        assertNotEquals(task1, task4);
        assertNotEquals(task1, null);
    }

    @Test
    void builder() {
        Task.TaskBuilder builder = Task.builder("Test task");
        assertNotNull(builder);

        Task task = builder.completed().build();
        assertNotNull(task);
        assertEquals("[x] Test task", task.serialize());
    }
}