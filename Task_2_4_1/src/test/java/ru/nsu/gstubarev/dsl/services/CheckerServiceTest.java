package ru.nsu.gstubarev.dsl.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.dsl.dataclasses.CheckResult;
import ru.nsu.gstubarev.dsl.dataclasses.Config;
import ru.nsu.gstubarev.dsl.dataclasses.Group;
import ru.nsu.gstubarev.dsl.dataclasses.Student;
import ru.nsu.gstubarev.dsl.dataclasses.Task;

public class CheckerServiceTest {

    @Test
    public void testRunChecks() {
        GitService gitService = mock(GitService.class);
        BuildService buildService = mock(BuildService.class);
        CheckerService checkerService = new CheckerService(gitService, buildService);

        Config config = new Config();
        Group group = new Group("20201");
        Student student = new Student("userGit", "Иванов Иван", "link");
        group.addStudent(student);
        config.addGroup(group);

        Task task = new Task(1, "Task1", 10,
                LocalDate.now(), LocalDate.now().plusDays(1));
        config.addTask(task);
        config.addCheck("20201", 1L);

        when(gitService.cloneRepository(anyString(), any())).thenReturn(true);
        when(gitService.getUniqueActiveWeeksCount(any())).thenReturn(2);
        when(gitService.getLatestCommitDate(any(), anyString())).thenReturn(LocalDate.now());

        CheckResult cr = new CheckResult();
        cr.compiled = true;
        cr.withoutReviewDogs = true;
        cr.testsFailed = 0;
        when(buildService.checkTask(any(), anyString())).thenReturn(cr);

        checkerService.runChecks(config);

        CheckResult finalRes = student.getResult(1L);
        assertNotNull(finalRes);
        assertEquals(10, finalRes.finalScore);
    }
}