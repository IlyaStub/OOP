package ru.nsu.gstubarev.dsl.dataClasses;

import java.util.LinkedList;
import java.util.List;

public class Group {
    private final String name;
    private final List<Student> students = new LinkedList<>();

    public Group(String name) {
        this.name = name;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public String getName() {
        return name;
    }

    public List<Student> getStudents() {
        return students;
    }
}