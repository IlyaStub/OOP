package ru.nsu.gstubarev.dsl.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.dsl.exceptions.CommandExecutionException;

/**
 * TEST.
 */
public class CommandExecutorTest {

    @Test
    public void testExecuteSuccess() throws Exception {
        CommandExecutor executor = new CommandExecutor();
        File tempDir = Files.createTempDirectory("cmd").toFile();

        List<String> cmd = Arrays.asList("java", "-version");
        AtomicBoolean hasOutput = new AtomicBoolean(false);

        boolean res = executor.execute(tempDir, cmd, line -> hasOutput.set(true));
        assertTrue(res);
        assertTrue(hasOutput.get());

        tempDir.delete();
    }

    @Test
    public void testExecuteFailureInvalidCommand() throws Exception {
        CommandExecutor executor = new CommandExecutor();
        File tempDir = Files.createTempDirectory("cmd_fail").toFile();

        List<String> cmd = Arrays.asList("non_existent_command_12345");

        assertThrows(CommandExecutionException.class, () -> {
            executor.execute(tempDir, cmd, null);
        });

        tempDir.delete();
    }
}