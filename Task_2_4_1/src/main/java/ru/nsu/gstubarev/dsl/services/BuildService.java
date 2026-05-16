package ru.nsu.gstubarev.dsl.services;

import ru.nsu.gstubarev.dsl.dataClasses.CheckResult;
import ru.nsu.gstubarev.dsl.utils.CommandExecutor;
import ru.nsu.gstubarev.dsl.utils.TestParser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class with methods for building student's project.
 */
public class BuildService {
    private final CommandExecutor executor;
    private final StyleChecker styleChecker;
    private final TestParser testParser;

    /**
     * Constructor for BuildService.
     */
    public BuildService(CommandExecutor executor,
                        StyleChecker styleChecker,
                        TestParser testParser) {
        this.executor = executor;
        this.styleChecker = styleChecker;
        this.testParser = testParser;
    }

    /**
     * Method for check all Task.
     */
    public CheckResult checkTask(File studentRepoDir, String taskName) {
        CheckResult res = new CheckResult();
        File taskDir = new File(studentRepoDir, taskName);
        if (!taskDir.exists()) {
            return res;
        }

        boolean isWin = System.getProperty("os.name").toLowerCase().contains("win");

        res.compiled = runGradle(taskDir, isWin, "classes");
        if (!res.compiled) {
            return res;
        }

        res.withoutReviewDogs = styleChecker.check(taskDir);

        res.docsGen = runGradle(taskDir, isWin, "javadoc");

        runGradle(taskDir, isWin, "test");

        TestParser.TestStats stats =
                testParser.parse(new File(taskDir, "build/test-results/test"));
        res.testsPassed = stats.passed;
        res.testsFailed = stats.failed;
        res.testsSkipped = stats.skipped;

        return res;
    }

    private boolean runGradle(File dir, boolean isWin, String task) {
        List<String> cmd = new ArrayList<>();
        if (isWin) {
            cmd.addAll(List.of("cmd.exe", "/c", "gradlew.bat"));
        } else {
            cmd.addAll(List.of("sh", "./gradlew"));
        }
        cmd.add(task);
        return executor.execute(dir, cmd, System.out::println);
    }
}