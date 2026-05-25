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
import ru.nsu.gstubarev.dsl.exceptions.GitOperationException;


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
                throw new GitOperationException("Код возврата git clone != 0 для "
                        + repoUrl, null);
            }
        } catch (IOException | InterruptedException e) {
            throw new GitOperationException("Ошибка при клонировании репозитория "
                    + repoUrl, e);
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
        } catch (IOException | InterruptedException e) {
            throw new GitOperationException("Ошибка получения даты коммита", e);
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
        } catch (IOException | InterruptedException e) {
            throw new GitOperationException("Ошибка подсчета активности (git log) в "
                    + repoDir.getAbsolutePath(), e);
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