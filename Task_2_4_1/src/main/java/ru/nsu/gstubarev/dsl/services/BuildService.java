package ru.nsu.gstubarev.dsl.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.nsu.gstubarev.dsl.dataClasses.CheckResult;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for building and testing student tasks via Gradle.
 */
public class BuildService {
    private boolean runCommand(File targetDir, String... command) {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.directory(targetDir);
        pb.inheritIO();

        try {
            Process process = pb.start();
            int ec = process.waitFor();
            return ec == 0;
        } catch (Exception e) {
            System.err.println("ошибка при: " + String.join(" ", command));
            return false;
        }
    }

    public CheckResult checkTask(File studentRepoDir, String taskName) {
        CheckResult res = new CheckResult();
        boolean isWin = System.getProperty("os.name").toLowerCase().contains("win");

        File taskDir = new File(studentRepoDir, taskName);

        if (!taskDir.exists() || !taskDir.isDirectory()) {
            System.out.println("не найдена папка " + taskDir.getAbsolutePath());
            return res;
        }

        System.out.println("Компиляция " + taskName);
        res.compiled = runGradle(taskDir, isWin, "classes");

        if (!res.compiled) {
            System.out.println("Ошибка компиляции");
            return res;
        }
        System.out.println("Успех компиляции");

        System.out.println("РЕВЬЮ ДОГИ))))");
        res.withoutReviewDogs = runGradle(taskDir, isWin, "checkstyleMain");

        if (!res.withoutReviewDogs) {
            System.out.println("ХАХАХАХАХА");
            return res;
        }

        System.out.println("дока");
        res.docsGen = runGradle(taskDir, isWin, "javadoc");

        System.out.println("Тесты");
        runGradle(taskDir, isWin, "test");

        parseTestResults(taskDir, res);

        return res;
    }

    private boolean runGradle(File dir, boolean isWin, String task) {
        List<String> command = new ArrayList<>();
        if (isWin) {
            command.add("cmd.exe");
            command.add("/c");
            command.add("gradlew.bat");
        } else {
            command.add("sh");
            command.add("./gradlew");
        }
        command.add(task);

        return runCommand(dir, command.toArray(new String[0]));
    }

    private void parseTestResults(File taskDir, CheckResult res) {
        File resultsDir = new File(taskDir, "build/test-results/test");

        if (!resultsDir.exists() || !resultsDir.isDirectory()) return;

        File[] xmlFiles = resultsDir.listFiles((dir, name) -> name.endsWith(".xml"));
        if (xmlFiles == null || xmlFiles.length == 0) return;

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            for (File xml : xmlFiles) {
                Document doc = dBuilder.parse(xml);
                doc.getDocumentElement().normalize();

                Element testSuite = (Element) doc.getElementsByTagName("testsuite").item(0);
                if (testSuite != null) {
                    int tests = Integer.parseInt(testSuite.getAttribute("tests"));
                    int failures = Integer.parseInt(testSuite.getAttribute("failures"));
                    int skipped = Integer.parseInt(testSuite.getAttribute("skipped"));

                    res.testsFailed += failures;
                    res.testsSkipped += skipped;
                    res.testsPassed += (tests - failures - skipped);
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка " + e.getMessage());
        }
    }
}