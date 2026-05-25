package ru.nsu.gstubarev.dsl.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.dsl.dataclasses.CheckResult;
import ru.nsu.gstubarev.dsl.utils.CommandExecutor;
import ru.nsu.gstubarev.dsl.utils.TestParser;

/**
 * TEST.
 */
public class BuildServiceTest {

    @Test
    public void testCheckTaskNotExists() {
        CommandExecutor executor = mock(CommandExecutor.class);
        StyleChecker styleChecker = mock(StyleChecker.class);
        TestParser testParser = mock(TestParser.class);
        BuildService service = new BuildService(executor, styleChecker, testParser);

        File dir = new File("non_existent_dir_12345");
        CheckResult result = service.checkTask(dir, "task1");

        assertFalse(result.compiled);
    }

    @Test
    public void testCheckTaskExists() throws Exception {
        CommandExecutor executor = mock(CommandExecutor.class);
        StyleChecker styleChecker = mock(StyleChecker.class);
        TestParser testParser = mock(TestParser.class);

        when(executor.execute(any(), any(), any())).thenReturn(true);
        when(styleChecker.check(any())).thenReturn(true);

        TestParser.TestStats stats = mock(TestParser.TestStats.class);
        when(testParser.parse(any())).thenReturn(stats);

        BuildService service = new BuildService(executor, styleChecker, testParser);

        File tempDir = Files.createTempDirectory("studentRepo").toFile();
        File taskDir = new File(tempDir, "task1");
        taskDir.mkdir();

        CheckResult result = service.checkTask(tempDir, "task1");

        assertTrue(result.compiled);
        assertTrue(result.withoutReviewDogs);
        assertTrue(result.docsGen);
        assertEquals(0, result.testsPassed);

        taskDir.delete();
        tempDir.delete();
    }
}