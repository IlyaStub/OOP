package ru.nsu.gstubarev.dsl.outputs;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.dsl.dataclasses.CheckResult;
import ru.nsu.gstubarev.dsl.dataclasses.Checkpoint;
import ru.nsu.gstubarev.dsl.dataclasses.Config;
import ru.nsu.gstubarev.dsl.dataclasses.Group;
import ru.nsu.gstubarev.dsl.dataclasses.Student;
import ru.nsu.gstubarev.dsl.dataclasses.Task;

/**
 * TEST.
 */
public class HtmlGeneratorTest {

    @Test
    public void testGenFullCoverage() throws Exception {
        Config config = new Config();
        Group group = new Group("CoverageGroup");
        config.addGroup(group);

        Student student = new Student("gitUser", "Иванов Иван", "link");
        group.addStudent(student);

        LocalDate now = LocalDate.now();
        Task task = new Task(1, "Task1", 10, now, now.plusDays(1));
        config.addTask(task);
        config.addCheck("CoverageGroup", 1L);

        CheckResult result = new CheckResult();
        result.compiled = true;
        result.docsGen = true;
        result.withoutReviewDogs = true;
        result.testsPassed = 5;
        result.finalScore = 10;
        student.addResult(1L, result);

        Checkpoint checkpoint = new Checkpoint("Milestone1", now.plusDays(2));
        config.addCheckpoint(checkpoint);

        File outFile = File.createTempFile("report_full", ".html");
        String outPath = outFile.getAbsolutePath();
        HtmlGenerator generator = new HtmlGenerator();
        generator.gen(config, outPath);
        assertTrue(outFile.exists());

        String content = Files.readString(outFile.toPath());
        assertTrue(content.contains("oop-checker"));
        assertTrue(content.contains("CoverageGroup"));
        assertTrue(content.contains("Иванов Иван"));
        assertTrue(content.contains("Task1"));
        assertTrue(content.contains("Milestone1"));

        outFile.delete();
    }
}