package ru.nsu.gstubarev.dsl.dataclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class StudentTest {
    @Test
    public void testStudentMethods() {
        Student student = new Student("githubUser", "Ivanov Ivan", "https://github.com/user");

        assertEquals("githubUser", student.getNameGit());
        assertEquals("Ivanov Ivan", student.getFio());
        assertEquals("https://github.com/user", student.getRepoLink());

        CheckResult result = new CheckResult();
        student.addResult(10L, result);

        assertEquals(result, student.getResult(10L));
        assertNotNull(student.toString());
    }
}