package ru.nsu.gstubarev.dsl.outputs;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.dsl.dataclasses.Config;
import ru.nsu.gstubarev.dsl.dataclasses.Group;

/**
 * TEST.
 */
public class HtmlGeneratorTest {

    @Test
    public void testGen() throws Exception {
        Config config = new Config();
        Group group = new Group("TestGroup2020");

        config.addGroup(group);

        HtmlGenerator generator = new HtmlGenerator();
        File outFile = File.createTempFile("report", ".html");
        String outPath = outFile.getAbsolutePath();

        generator.gen(config, outPath);
        assertTrue(outFile.exists());


        String content = Files.readString(outFile.toPath());
        assertTrue(content.contains("oop-checker"));

        outFile.delete();
    }
}