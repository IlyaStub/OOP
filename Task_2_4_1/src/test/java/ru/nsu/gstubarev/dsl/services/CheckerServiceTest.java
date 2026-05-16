package ru.nsu.gstubarev.dsl.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

/**
 * TEST.
 */
public class CheckerServiceTest {

    private Config createBaseConfig(Student student, LocalDate soft, LocalDate hard) {
        Config config = new Config();
        Group group = new Group("20201");
        group.addStudent(student);
        config.addGroup(group);

        Task task = new Task(1, "Task1", 10, soft, hard);
        config.addTask(task);
        config.addCheck("20201", 1L);
        return config;
    }

    @Test
    public void testRunChecksEmptyTasks() {
        Config config = new Config();
        config.addGroup(new Group("20201"));
        CheckerService checkerService =
                new CheckerService(mock(GitService.class), mock(BuildService.class));
        checkerService.runChecks(config);
    }

    @Test
    public void testRunChecksCloneFails() {
        Student student = new Student("userGit", "Иванов", "link");
        Config config = createBaseConfig(student, LocalDate.now(), LocalDate.now());

        GitService gitService = mock(GitService.class);
        when(gitService.cloneRepository(anyString(), any())).thenReturn(false);

        CheckerService checkerService = new CheckerService(gitService, mock(BuildService.class));
        checkerService.runChecks(config);

        assertNull(student.getResult(1L));
    }

    @Test
    public void testRunChecksAfterSoftDeadline() {
        LocalDate now = LocalDate.now();

        GitService gitService = mock(GitService.class);
        when(gitService.cloneRepository(anyString(), any())).thenReturn(true);
        when(gitService.getLatestCommitDate(any(), anyString())).thenReturn(now);

        CheckResult cr = new CheckResult();
        cr.compiled = true;
        cr.withoutReviewDogs = true;
        cr.testsFailed = 0;
        BuildService buildService = mock(BuildService.class);
        when(buildService.checkTask(any(), anyString())).thenReturn(cr);
        Student student = new Student("userGit", "Иванов", "link");
        Config config = createBaseConfig(student,
                now.minusDays(2), now.plusDays(2));
        CheckerService checkerService = new CheckerService(gitService, buildService);
        checkerService.runChecks(config);

        assertEquals(5, student.getResult(1L).finalScore);
    }

    @Test
    public void testRunChecksAfterHardDeadline() {
        LocalDate now = LocalDate.now();

        GitService gitService = mock(GitService.class);
        when(gitService.cloneRepository(anyString(), any())).thenReturn(true);
        when(gitService.getLatestCommitDate(any(), anyString())).thenReturn(now);

        CheckResult cr = new CheckResult();
        cr.compiled = true;
        cr.withoutReviewDogs = true;
        cr.testsFailed = 0;
        BuildService buildService = mock(BuildService.class);
        when(buildService.checkTask(any(), anyString())).thenReturn(cr);
        Student student = new Student("userGit", "Иванов", "link");
        Config config = createBaseConfig(student,
                now.minusDays(5), now.minusDays(2));
        CheckerService checkerService = new CheckerService(gitService, buildService);
        checkerService.runChecks(config);

        assertEquals(0, student.getResult(1L).finalScore);
    }

    @Test
    public void testRunChecksWithBonus() {
        Student student = new Student("userGit", "Иванов", "link");
        LocalDate now = LocalDate.now();
        Config config = createBaseConfig(student, now.plusDays(1), now.plusDays(2));
        config.addBonus("userGit", 1L, 3);

        GitService gitService = mock(GitService.class);
        when(gitService.cloneRepository(anyString(), any())).thenReturn(true);
        when(gitService.getLatestCommitDate(any(), anyString())).thenReturn(now);

        CheckResult cr = new CheckResult();
        cr.compiled = true;
        cr.withoutReviewDogs = true;
        cr.testsFailed = 0;
        BuildService buildService = mock(BuildService.class);
        when(buildService.checkTask(any(), anyString())).thenReturn(cr);

        CheckerService checkerService = new CheckerService(gitService, buildService);
        checkerService.runChecks(config);

        assertEquals(13, student.getResult(1L).finalScore);
    }
}