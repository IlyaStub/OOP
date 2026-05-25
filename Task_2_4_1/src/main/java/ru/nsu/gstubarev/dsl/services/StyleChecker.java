package ru.nsu.gstubarev.dsl.services;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import ru.nsu.gstubarev.dsl.exceptions.StyleCheckException;
import ru.nsu.gstubarev.dsl.utils.CommandExecutor;

/**
 * Checks code style using Checkstyle.
 */
public class StyleChecker {
    private final ToolManager toolManager;
    private final CommandExecutor executor;

    /**
     * Constructor for StyleChecker.
     */
    public StyleChecker(ToolManager toolManager, CommandExecutor executor) {
        this.toolManager = toolManager;
        this.executor = executor;
    }

    /**
     * Runs style check on the given task directory.
     */
    public boolean check(File taskDir) {
        try {
            String jar = toolManager.getJar().toString();
            String xml = toolManager.getXml().toString();
            File src = new File(taskDir, "src/main/java");
            String target = src.exists() ? src.getAbsolutePath() : taskDir.getAbsolutePath();

            AtomicBoolean hasIssues = new AtomicBoolean(false);
            executor.execute(taskDir, List.of("java", "-jar", jar, "-c", xml, target), line -> {
                if (line.contains("[WARN]") || line.contains("[ERROR]")) {
                    hasIssues.set(true);
                }
            });
            return !hasIssues.get();
        } catch (Exception e) {
            throw new StyleCheckException("Ошибка при запуске Checkstyle для директории: "
                    + taskDir, e);
        }
    }
}