package ru.nsu.gstubarev.pizza.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.gstubarev.pizza.records.Configuration;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class JsonParserConfTest {
    @Test
    void testParseValidJson(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("test_config.json");
        String jsonContent = "{\"bakersCount\":5, \"couriersCount\":4, \"storageCapacity\":20, \"bakerSpeedMs\":100, \"minDeliveryTimeMs\":200, \"maxDeliveryTimeMs\":300}";
        Files.writeString(file, jsonContent);

        JsonParserConf parser = new JsonParserConf();
        Configuration config = parser.parse(file.toString());

        assertNotNull(config);
        assertEquals(5, config.bakersCount());
        assertEquals(20, config.storageCapacity());
    }

    @Test
    void testParseMissingFile() {
        JsonParserConf parser = new JsonParserConf();
        Configuration config = parser.parse("non_existent_file.json");

        assertNotNull(config);
        assertEquals(3, config.bakersCount());
    }
}