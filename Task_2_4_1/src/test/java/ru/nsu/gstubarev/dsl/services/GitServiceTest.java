package ru.nsu.gstubarev.dsl.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/**
 * TEST.
 */
public class GitServiceTest {
    @Test
    public void testCloneRepositoryInvalidUrl() {
        GitService gitService = new GitService();
        File dummyDir = new File("dummy_git_dir_invalid");

        boolean cloned = gitService.cloneRepository("http://invalid-url-that-does-not-exist", dummyDir);
        assertFalse(cloned);
    }

    @Test
    public void testLatestCommitDateAndActiveWeeksEmptyDir() {
        GitService gitService = new GitService();
        File dummyDir = new File("dummy_git_dir_empty");

        LocalDate date = gitService.getLatestCommitDate(dummyDir, ".");
        assertNull(date);

        int weeks = gitService.getUniqueActiveWeeksCount(dummyDir);
        assertTrue(weeks == 0);
    }

    @Test
    public void testDeleteDirectoryRecursive() throws Exception {
        GitService gitService = new GitService();

        File parentDir = Files.createTempDirectory("parentDir").toFile();
        File childDir = new File(parentDir, "child");
        childDir.mkdir();
        File file = new File(childDir, "file.txt");
        file.createNewFile();

        gitService.cloneRepository("invalid_url", parentDir);

        assertFalse(parentDir.exists());
    }

    @Test
    public void testGitOperationsWithRealLocalRepo() throws Exception {
        File sourceRepo = Files.createTempDirectory("sourceRepo").toFile();
        new ProcessBuilder("git", "init")
                .directory(sourceRepo).start().waitFor();
        new ProcessBuilder("git", "config", "user.email", "test@test.com")
                .directory(sourceRepo).start().waitFor();
        new ProcessBuilder("git", "config", "user.name", "test")
                .directory(sourceRepo).start().waitFor();
        new ProcessBuilder("git", "commit", "--allow-empty", "-m", "Init")
                .directory(sourceRepo).start().waitFor();

        GitService gitService = new GitService();
        File targetRepo = Files.createTempDirectory("targetRepo").toFile();

        boolean cloned = gitService.cloneRepository(sourceRepo.getAbsolutePath(), targetRepo);
        assertTrue(cloned);

        int weeks = gitService.getUniqueActiveWeeksCount(targetRepo);
        assertTrue(weeks >= 1);
    }
}