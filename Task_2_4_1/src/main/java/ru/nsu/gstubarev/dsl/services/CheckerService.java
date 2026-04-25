package ru.nsu.gstubarev.dsl.services;

import ru.nsu.gstubarev.dsl.dataClasses.Config;
import ru.nsu.gstubarev.dsl.dataClasses.Group;
import ru.nsu.gstubarev.dsl.dataClasses.Student;

import java.io.File;
import java.util.List;

public class CheckerService {
    private final GitService gitService;
    private final File workDir;

    public CheckerService() {
        this.gitService = new GitService();
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
                    // билдим, тестим, находим review доги и по-новой)

                    System.out.println("репо готов.");
                } else {
                    System.out.println("ошибка");
                }
            }
        }
    }
}