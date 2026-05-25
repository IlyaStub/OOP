package ru.nsu.gstubarev.dsl.delegates;

import java.util.Map;
import ru.nsu.gstubarev.dsl.dataclasses.Group;
import ru.nsu.gstubarev.dsl.dataclasses.Student;

/**
 * Configures single group contents.
 */
public class GroupConfig {
    private final Group group;

    /**
     * Wraps group for configuration.
     */
    public GroupConfig(Group group) {
        this.group = group;
    }

    /**
     * Adds student from parameters.
     */
    public void addStudent(Map<String, String> params) {
        Student student = new Student(
                params.get("nameGit"),
                params.get("fio"),
                params.get("repoLink")
        );
        group.addStudent(student);
    }
}