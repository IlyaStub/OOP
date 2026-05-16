package ru.nsu.gstubarev.dsl.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class GitServiceTest {

    @Test
    public void testGitServiceBasicCoverage() {
        GitService gitService = new GitService();
        File dummyDir = new File("dummy_git_dir_123");

        boolean cloned = gitService.cloneRepository("blabla", dummyDir);
        assertFalse(cloned);

        LocalDate date = gitService.getLatestCommitDate(dummyDir, "task1");
        assertNull(date);

        int weeks = gitService.getUniqueActiveWeeksCount(dummyDir);
        assertEquals(0, weeks);
    }
}