package ru.nsu.gstubarev.dsl.services;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import ru.nsu.gstubarev.dsl.dataClasses.CheckResult;
import ru.nsu.gstubarev.dsl.dataClasses.Config;
import ru.nsu.gstubarev.dsl.dataClasses.Group;
import ru.nsu.gstubarev.dsl.dataClasses.Student;
import ru.nsu.gstubarev.dsl.dataClasses.Task;

/**
 * Orchestrates the full checking pipeline for students.
 */
public class CheckerService {
    private final GitService gitService;
    private final BuildService buildService;
    private final File workDir;

    /**
     * Creates service with default work directory.
     */
    public CheckerService(GitService gitService, BuildService buildService) {
        this.gitService = gitService;
        this.buildService = buildService;
        this.workDir = new File("studRepo");
        if (!workDir.exists()) {
            workDir.mkdir();
        }
    }

    /**
     * Runs all configured checks for every student.
     */
    public void runChecks(Config config) {
        for (Group group : config.getGroups()) {
            List<Long> tasksToCheck = config.getChecks().get(group.getName());
            if (tasksToCheck == null || tasksToCheck.isEmpty()) {
                continue;
            }

            for (Student student : group.getStudents()) {
                File studentRepoDir = new File(workDir, student.getNameGit());

                boolean isCloned =
                        gitService.cloneRepository(student.getRepoLink(), studentRepoDir);

                if (isCloned) {
                    int activeWeeks = gitService.getUniqueActiveWeeksCount(studentRepoDir);

                    for (Long taskId : tasksToCheck) {
                        Task task = config.getTaskById(taskId);
                        if (task == null) {
                            continue;
                        }

                        CheckResult result = buildService
                                .checkTask(studentRepoDir, task.getName());

                        result.activeWeeks = activeWeeks;

                        LocalDate commitDate =
                                gitService.getLatestCommitDate(studentRepoDir, task.getName());
                        result.commitDate = commitDate;

                        if (result.compiled
                                && result.withoutReviewDogs && result.testsFailed == 0) {
                            if (commitDate == null) {
                                result.finalScore = 0;
                            } else {
                                LocalDate soft =
                                        LocalDate.parse(task.getSoftDeadline().toString());
                                LocalDate hard =
                                        LocalDate.parse(task.getHardDeadline().toString());

                                if (!commitDate.isAfter(soft)) {
                                    result.finalScore = task.getMaxScores();
                                } else if (!commitDate.isAfter(hard)) {
                                    result.finalScore =
                                            (int) Math.ceil(task.getMaxScores() / 2.0);
                                    System.out.println("Студент " + student.getFio()
                                            + " сдал " + task.getName()
                                            + " после мягкого дедлайна");
                                } else {
                                    result.finalScore = 0;
                                    System.out.println("Студент " + student.getFio()
                                            + " опоздал к жесткому дедлайну " + task.getName());
                                }
                            }
                        } else {
                            result.finalScore = 0;
                        }

                        result.finalScore += config.getBonus(student.getNameGit(), taskId);
                        student.addResult(taskId, result);
                    }
                } else {
                    System.out.println("ошибка загрузки");
                }
            }
        }
    }
}