package ru.nsu.gstubarev.dsl.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.dsl.exceptions.ToolDownloadException;

/**
 * TEST.
 */
public class ToolManagerTest {
    private File dummyJar;
    private File dummyXml;

    /**
     * Test.
     */
    @BeforeEach
    public void setUp() throws Exception {
        File toolsDir = new File("tools");
        if (!toolsDir.exists()) {
            toolsDir.mkdirs();
        }

        dummyJar = new File(toolsDir, "checkstyle-all.jar");
        dummyXml = new File(toolsDir, "checkstyle.xml");

        if (!dummyJar.exists()) {
            dummyJar.createNewFile();
        }
    }

    /**
     * Test.
     */
    @AfterEach
    public void tearDown() {
        if (dummyJar.exists()) {
            dummyJar.delete();
        }
        if (dummyXml.exists()) {
            dummyXml.delete();
        }
    }

    @Test
    public void testGetJarAndXmlBypassDownload() throws Exception {
        if (!dummyXml.exists()) {
            dummyXml.createNewFile();
        }

        ToolManager manager = new ToolManager();

        assertNotNull(manager.getJar());
        assertNotNull(manager.getXml());

        assertTrue(dummyJar.exists());
        assertTrue(dummyXml.exists());
    }

    @Test
    public void testDownloadXmlCoversBranch() {
        if (dummyXml.exists()) {
            dummyXml.delete();
        }

        ToolManager manager = new ToolManager();

        try {
            Path downloadedXml = manager.getXml();

            assertNotNull(downloadedXml);
            assertTrue(Files.exists(downloadedXml));
        } catch (Exception e) {
            assertTrue(e instanceof ToolDownloadException);
        }
    }
}