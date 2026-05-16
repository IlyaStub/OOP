package ru.nsu.gstubarev.dsl.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Class for work with git.
 */
public class GitService {
    /**
     * This method clone repository from git.
     */
    public boolean cloneRepository(String repoUrl, File targetDir) {
        if (targetDir.exists()) {
            deleteDirectory(targetDir);
        }

        ProcessBuilder processBuilder =
                new ProcessBuilder("git", "clone", repoUrl, targetDir.getAbsolutePath());

        processBuilder.environment().put("GIT_TERMINAL_PROMPT", "0");

        try {
            System.out.println("Клонируем репу: " + repoUrl);
            Process process = processBuilder.start();

            int ec = process.waitFor();

            if (ec == 0) {
                System.out.println("Склонировали");
                return true;
            } else {
                System.err.println("ошибка клонирования");
                return false;
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("ошибка " + e.getMessage());
            return false;
        }
    }

    /**
     * Method for get latest commit.
     */
    public LocalDate getLatestCommitDate(File repoDir, String taskName) {
        ProcessBuilder pb =
                new ProcessBuilder("git", "log", "-1", "--format=%cI", "--", taskName);
        pb.directory(repoDir);
        try {
            Process p = pb.start();
            try (BufferedReader r =
                         new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line = r.readLine();
                if (line != null && !line.trim().isEmpty()) {
                    return LocalDate.parse(line.trim().substring(0, 10));
                }
            }
            p.waitFor();
        } catch (Exception e) {
            System.err.println("Ошибка получения даты коммита для "
                    + taskName + ": " + e.getMessage());
        }
        return null;
    }

    /**
     * Method for getting unique active weeks count.
     */
    public int getUniqueActiveWeeksCount(File repoDir) {
        ProcessBuilder pb = new ProcessBuilder("git", "log", "--format=%cI");
        pb.directory(repoDir);
        Set<String> uniqueWeeks = new HashSet<>();
        try {
            Process p = pb.start();
            try (BufferedReader r =
                         new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                WeekFields weekFields = WeekFields.of(Locale.getDefault());
                while ((line = r.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        LocalDate date = LocalDate.parse(line.trim().substring(0, 10));
                        int week = date.get(weekFields.weekOfWeekBasedYear());
                        int year = date.getYear();
                        uniqueWeeks.add(year + "-W" + week);
                    }
                }
            }
            p.waitFor();
        } catch (Exception e) {
            System.err.println("Ошибка подсчета активности: " + e.getMessage());
        }
        return uniqueWeeks.size();
    }

    private void deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        directoryToBeDeleted.delete();
    }
}