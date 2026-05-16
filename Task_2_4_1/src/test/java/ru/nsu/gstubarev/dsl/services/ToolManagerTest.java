package ru.nsu.gstubarev.dsl.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import org.junit.jupiter.api.Test;

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