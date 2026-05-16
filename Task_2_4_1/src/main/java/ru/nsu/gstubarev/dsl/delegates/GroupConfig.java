package ru.nsu.gstubarev.dsl.delegates;

import ru.nsu.gstubarev.dsl.dataClasses.Group;
import ru.nsu.gstubarev.dsl.dataClasses.Student;
import java.util.Map;

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