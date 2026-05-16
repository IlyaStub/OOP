package ru.nsu.gstubarev.dsl.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

/**
 * TEST.
 */
public class ToolManagerTest {

    @Test
    public void testGetJarAndXmlBypassDownload() throws Exception {
        File toolsDir = new File("tools");
        toolsDir.mkdirs();

        File dummyJar = new File(toolsDir, "checkstyle-all.jar");
        dummyJar.createNewFile();

        File dummyXml = new File(toolsDir, "checkstyle.xml");
        dummyXml.createNewFile();

        ToolManager manager = new ToolManager();

        assertNotNull(manager.getJar());
        assertNotNull(manager.getXml());

        dummyJar.delete();
        dummyXml.delete();
        toolsDir.delete();
    }
}