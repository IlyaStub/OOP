package ru.nsu.gstubarev.dsl.delegates;

import ru.nsu.gstubarev.dsl.dataClasses.Group;
import ru.nsu.gstubarev.dsl.dataClasses.Student;
import java.util.Map;

public class GroupConfig {
    private final Group group;

    public GroupConfig(Group group) {
        this.group = group;
    }

    public void addStudent(Map<String, String> params) {
        Student student = new Student(
                params.get("nameGit"),
                params.get("fio"),
                params.get("repoLink")
        );
        group.addStudent(student);
    }
}
