package ru.nsu.gstubarev.dsl.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.dsl.dataclasses.CheckResult;
import ru.nsu.gstubarev.dsl.dataclasses.Checkpoint;
import ru.nsu.gstubarev.dsl.dataclasses.Config;
import ru.nsu.gstubarev.dsl.dataclasses.Group;
import ru.nsu.gstubarev.dsl.dataclasses.ReportData;
import ru.nsu.gstubarev.dsl.dataclasses.Student;
import ru.nsu.gstubarev.dsl.dataclasses.Task;

public class ReportDataServiceTest {

    @Test
    public void testCollectReportData() {
        Config config = new Config();
        Group group = new Group("20201");
        Student student = new Student("user", "Ivanov", "link");
        group.addStudent(student);
        config.addGroup(group);

        Task task = new Task(1, "Task1",
                10, LocalDate.now(), LocalDate.now().plusDays(1));
        config.addTask(task);
        config.addCheck("20201", 1L);

        CheckResult cr = new CheckResult();
        cr.compiled = true;
        cr.docsGen = true;
        cr.withoutReviewDogs = true;
        cr.finalScore = 10;
        cr.activeWeeks = 2;
        student.addResult(1L, cr);

        Checkpoint cp = new Checkpoint("CP1", LocalDate.now().plusDays(5));
        config.addCheckpoint(cp);

        ReportDataService service = new ReportDataService();
        ReportData report = service.collect(config);

        assertNotNull(report);
        assertFalse(report.groups().isEmpty());
        assertEquals("20201", report.groups().get(0).groupName());
        assertFalse(report.groups().get(0).summaries().isEmpty());
    }
}