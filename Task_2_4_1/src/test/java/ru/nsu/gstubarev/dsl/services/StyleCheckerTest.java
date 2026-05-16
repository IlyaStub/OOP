package ru.nsu.gstubarev.dsl.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.dsl.utils.CommandExecutor;

/**
 * Test.
 */
public class StyleCheckerTest {

    @Test
    public void testCheckSuccessNoIssues() throws Exception {
        ToolManager toolManager = mock(ToolManager.class);
        CommandExecutor executor = mock(CommandExecutor.class);

        when(toolManager.getJar()).thenReturn(Paths.get("dummy.jar"));
        when(toolManager.getXml()).thenReturn(Paths.get("dummy.xml"));

        StyleChecker checker = new StyleChecker(toolManager, executor);

        File tempDir = Files.createTempDirectory("styleCheck").toFile();
        boolean result = checker.check(tempDir);

        assertTrue(result);
        tempDir.delete();
    }

    @Test
    public void testCheckWithSrcDirectoryExists() throws Exception {
        ToolManager toolManager = mock(ToolManager.class);
        CommandExecutor executor = mock(CommandExecutor.class);

        when(toolManager.getJar()).thenReturn(Paths.get("dummy.jar"));
        when(toolManager.getXml()).thenReturn(Paths.get("dummy.xml"));

        StyleChecker checker = new StyleChecker(toolManager, executor);

        File tempDir = Files.createTempDirectory("styleCheckWithSrc").toFile();
        File srcDir = new File(tempDir, "src/main/java");
        srcDir.mkdirs();

        boolean result = checker.check(tempDir);

        assertTrue(result);
        srcDir.delete();
        tempDir.delete();
    }

    @Test
    public void testCheckHasWarnIssues() throws Exception {
        ToolManager toolManager = mock(ToolManager.class);
        CommandExecutor executor = mock(CommandExecutor.class);

        when(toolManager.getJar()).thenReturn(Paths.get("dummy.jar"));
        when(toolManager.getXml()).thenReturn(Paths.get("dummy.xml"));

        doAnswer(invocation -> {
            Consumer<String> consumer = invocation.getArgument(2);
            consumer.accept("Some code issue [WARN] at line 10");
            return true;
        }).when(executor).execute(any(File.class), any(), any());

        StyleChecker checker = new StyleChecker(toolManager, executor);
        File tempDir = Files.createTempDirectory("styleCheckWarn").toFile();

        boolean result = checker.check(tempDir);

        assertFalse(result);
        tempDir.delete();
    }

    @Test
    public void testCheckException() throws Exception {
        ToolManager toolManager = mock(ToolManager.class);
        CommandExecutor executor = mock(CommandExecutor.class);

        when(toolManager.getJar()).thenThrow(new RuntimeException("Simulated checkstyle error"));

        StyleChecker checker = new StyleChecker(toolManager, executor);
        boolean result = checker.check(new File("dummy"));

        assertFalse(result);
    }
}