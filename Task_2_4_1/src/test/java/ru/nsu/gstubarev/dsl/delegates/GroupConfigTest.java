package ru.nsu.gstubarev.dsl.delegates;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.dsl.dataclasses.Group;
import ru.nsu.gstubarev.dsl.dataclasses.Student;

/**
 * TEST.
 */
public class GroupConfigTest {

    @Test
    public void testAddStudent() {
        Group group = new Group("20201");

        Map<String, String> params = new HashMap<>();
        params.put("nameGit", "user123");
        params.put("fio", "Ivanov Ivan");
        params.put("repoLink", "https://github.com/user123");
        GroupConfig config = new GroupConfig(group);
        config.addStudent(params);
        assertEquals(1, group.getStudents().size());

        Student student = group.getStudents().get(0);
        assertEquals("user123", student.getNameGit());
    }
}