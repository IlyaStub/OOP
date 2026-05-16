package ru.nsu.gstubarev.dsl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.dsl.dataclasses.Student;
import ru.nsu.gstubarev.dsl.dataclasses.Task;

class MainTest {

    @Test
    void testTaskCreation() {
        LocalDate soft = LocalDate.parse("2024-05-10");
        LocalDate hard = LocalDate.parse("2024-05-20");

        Task task = new Task(1, "Task_2_4_1", 10, soft, hard);

        assertNotNull(task);

        assertEquals(1L, task.getId());
        assertEquals("Task_2_4_1", task.getName());
    }

    @Test
    void testStudentCreation() {
        Student student = new Student("IlyaStub", "Stubarev Ilya", "https://github.com/IlyaStub/repo");

        assertNotNull(student);
    }
}