package ru.nsu.gstubarev.dsl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;

/**
 * TEST.
 */
public class MainTest {
    @Test
    public void testMainExecutesSuccessfully() throws Exception {
        File confFile = new File("conf.groovy");
        String groovyScript = "";
        Files.writeString(confFile.toPath(), groovyScript);

        String[] args = new String[0];
        Main.main(args);

        assertTrue(confFile.exists());

        File reportFile = new File("report.html");
        assertTrue(reportFile.exists());

        confFile.delete();
        reportFile.delete();
    }

    @Test
    public void testMainHandlesException() {
        File confFile = new File("conf.groovy");
        if (confFile.exists()) {
            confFile.delete();
        }

        String[] args = new String[0];
        Main.main(args);

        assertFalse(confFile.exists());
    }
}