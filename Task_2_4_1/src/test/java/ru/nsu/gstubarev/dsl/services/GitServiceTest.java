package ru.nsu.gstubarev.dsl.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.dsl.exceptions.GitOperationException;

/**
 * TEST.
 */
public class GitServiceTest {
    @Test
    public void testCloneRepositoryInvalidUrl() {
        File dummyDir = new File("dummy_git_dir_invalid");
        GitService gitService = new GitService();
        assertThrows(GitOperationException.class, () -> {
            gitService.cloneRepository(
                    "http://invalid-url-that-does-not-exist", dummyDir);
        });
    }

    @Test
    public void testLatestCommitDateAndActiveWeeksEmptyDir() {
        File dummyDir = new File("dummy_git_dir_empty");
        GitService gitService = new GitService();

        assertThrows(GitOperationException.class, () -> {
            gitService.getLatestCommitDate(dummyDir, ".");
        });

        assertThrows(GitOperationException.class, () -> {
            gitService.getUniqueActiveWeeksCount(dummyDir);
        });
    }

    @Test
    public void testDeleteDirectoryRecursive() throws Exception {
        File parentDir = Files.createTempDirectory("parentDir").toFile();
        File childDir = new File(parentDir, "child");
        childDir.mkdir();
        File file = new File(childDir, "file.txt");
        file.createNewFile();
        GitService gitService = new GitService();
        assertThrows(GitOperationException.class, () -> {
            gitService.cloneRepository("invalid_url", parentDir);
        });

        assertFalse(parentDir.exists()); // Удаление папки всё равно должно было произойти до ошибки
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