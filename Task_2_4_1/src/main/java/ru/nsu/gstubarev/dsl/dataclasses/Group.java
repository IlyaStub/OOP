package ru.nsu.gstubarev.dsl.dataclasses;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a student group.
 */
public class Group {
    private final String name;
    private final List<Student> students = new LinkedList<>();

    /**
     * Constructs a named group.
     */
    public Group(String name) {
        this.name = name;
    }

    /**
     * Adds a new student.
     */
    public void addStudent(Student student) {
        this.students.add(student);
    }

    /**
     * Returns group name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns all students.
     */
    public List<Student> getStudents() {
        return students;
    }
}