package ru.nsu.gstubarev.dsl.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * TEST.
 */
public class ToolManagerTest {

    private File toolsDir;
    private File dummyJar;
    private File dummyXml;

    @BeforeEach
    public void setUp() throws Exception {
        toolsDir = new File("tools");
        if (!toolsDir.exists()) {
            toolsDir.mkdirs();
        }

        dummyJar = new File(toolsDir, "checkstyle-all.jar");
        dummyXml = new File(toolsDir, "checkstyle.xml");
        dummyJar.createNewFile();
        dummyXml.createNewFile();
    }

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
        ToolManager manager = new ToolManager();

        assertNotNull(manager.getJar());
        assertNotNull(manager.getXml());

        assertTrue(dummyJar.exists());
        assertTrue(dummyXml.exists());
    }
}