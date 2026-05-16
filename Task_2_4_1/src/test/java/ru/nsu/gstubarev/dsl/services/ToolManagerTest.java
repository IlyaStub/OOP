package ru.nsu.gstubarev.dsl.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.dsl.exceptions.ToolDownloadException;

/**
 * TEST.
 */
public class ToolManagerTest {
    private File toolsDir;

    /**
     * TEST.
     */
    @BeforeEach
    public void setUp() {
        toolsDir = new File("tools");
        cleanUp();
    }

    /**
     * TEST.
     */
    @AfterEach
    public void tearDown() {
        if (toolsDir.exists()) {
            toolsDir.setWritable(true);
        }
        cleanUp();
    }

    private void cleanUp() {
        if (toolsDir.exists()) {
            try {
                Files.walk(toolsDir.toPath())
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException e) {
                System.err.println("Не удалось очистить директорию: " + e.getMessage());
            }
        }
    }

    @Test
    public void testGetJarAndXmlBypassDownload() throws Exception {
        assertTrue(toolsDir.mkdirs());
        new File(toolsDir, "checkstyle-all.jar").createNewFile();
        new File(toolsDir, "checkstyle.xml").createNewFile();

        ToolManager manager = new ToolManager();

        assertNotNull(manager.getJar());
        assertNotNull(manager.getXml());
    }

    @Test
    public void testDirectoryCreationAndDownload() throws Exception {
        ToolManager manager = new ToolManager();

        Path xmlPath = manager.getXml();

        assertNotNull(xmlPath);
        assertTrue(Files.exists(xmlPath));
    }

    @Test
    public void testToolDownloadExceptionIsThrown() throws Exception {
        cleanUp();

        File toolsFile = new File("tools");
        assertTrue(toolsFile.createNewFile());

        ToolManager manager = new ToolManager();

        assertThrows(ToolDownloadException.class, () -> {
            manager.getJar();
        });

        toolsFile.delete();
    }
}