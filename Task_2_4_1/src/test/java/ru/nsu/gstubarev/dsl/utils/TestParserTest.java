package ru.nsu.gstubarev.dsl.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;

/**
 * TEST.
 */
public class TestParserTest {

    @Test
    public void testParseValidXml() throws Exception {
        TestParser parser = new TestParser();
        File dir = Files.createTempDirectory("test-results").toFile();

        File xmlFile = new File(dir, "TEST-result.xml");
        String xmlData = "<testsuite tests=\"10\" failures=\"2\" errors=\"1\" skipped=\"1\"></testsuite>";
        Files.writeString(xmlFile.toPath(), xmlData);

        TestParser.TestStats stats = parser.parse(dir);
        assertEquals(6, stats.passed);
        assertEquals(3, stats.failed);
        assertEquals(1, stats.skipped);

        xmlFile.delete();
        dir.delete();
    }

    @Test
    public void testParseDirectoryNotExists() {
        TestParser parser = new TestParser();
        File nonExistentDir = new File("some_random_dir_123");

        TestParser.TestStats stats = parser.parse(nonExistentDir);
        assertEquals(0, stats.passed);
    }
}