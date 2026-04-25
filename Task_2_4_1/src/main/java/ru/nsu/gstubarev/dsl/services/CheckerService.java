package ru.nsu.gstubarev.dsl.services;

import ru.nsu.gstubarev.dsl.dataClasses.*;

import java.io.File;
import java.util.List;

public class CheckerService {
    private final GitService gitService;
    private final BuildService buildService;
    private final File workDir;

    public CheckerService() {
        this.gitService = new GitService();
        this.buildService = new BuildService();
        this.workDir = new File("studRepo");
        if (!workDir.exists()) {
            workDir.mkdir();
        }
    }

    public void runChecks(Config config) {
        for (Group group : config.getGroups()) {
            List<Long> tasksToCheck = config.getChecks().get(group.getName());
            if (tasksToCheck == null || tasksToCheck.isEmpty()) {
                continue;
            }

            for (Student student : group.getStudents()) {
                File studentRepoDir = new File(workDir, student.getNameGit());

                boolean isCloned = gitService.cloneRepository(student.getRepoLink(), studentRepoDir);

                if (isCloned) {
                    for (Long taskId : tasksToCheck) {
                        Task task = config.getTaskById(taskId);
                        if (task == null) continue;

                        CheckResult result = buildService.checkTask(studentRepoDir, task.getName());

                        if (result.compiled && result.withoutReviewDogs && result.testsFailed == 0) {
                            result.finalScore = task.getMaxScores();
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