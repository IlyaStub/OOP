package ru.nsu.gstubarev.dsl.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Consumer;
import ru.nsu.gstubarev.dsl.exceptions.CommandExecutionException;

/**
 * Execute commands.
 */
public class CommandExecutor {

    /**
     * Method for executing command.
     */
    public boolean execute(File dir, List<String> command, Consumer<String> outputConsumer) {
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(dir);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            try (BufferedReader reader =
                         new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (outputConsumer != null) {
                        outputConsumer.accept(line);
                    }
                }
            }
            return process.waitFor() == 0;
        } catch (IOException | InterruptedException e) {
            throw new CommandExecutionException("Ошибка выполнения команды: "
                    + String.join(" ", command), e);
        }
    }
}