package ru.nsu.gstubarev.dsl.dataclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * TEST.
 */
public class GroupTest {

    @Test
    public void testGroupMethods() {
        Group group = new Group("Group-A");
        assertEquals("Group-A", group.getName());
        assertTrue(group.getStudents().isEmpty());

        Student student = new Student("git", "fio", "link");
        group.addStudent(student);

        assertEquals(1, group.getStudents().size());
        assertEquals(student, group.getStudents().get(0));
    }
}