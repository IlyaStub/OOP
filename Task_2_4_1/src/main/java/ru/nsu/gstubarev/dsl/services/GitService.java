package ru.nsu.gstubarev.dsl.services;

import java.io.File;
import java.io.IOException;

public class GitService {
    public boolean cloneRepository(String repoUrl, File targetDir) {
        if (targetDir.exists()) {
            deleteDirectory(targetDir);
        }

        ProcessBuilder processBuilder = new ProcessBuilder("git", "clone", repoUrl, targetDir.getAbsolutePath());

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
            System.err.println("jib,rf " + e.getMessage());
            return false;
        }
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