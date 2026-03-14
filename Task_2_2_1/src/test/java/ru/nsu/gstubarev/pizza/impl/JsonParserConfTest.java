package ru.nsu.gstubarev.pizza.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.gstubarev.pizza.records.Configuration;

class JsonParserConfTest {
    @Test
    void testParseValidJson(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("test_config.json");
        String jsonContent = "{\"bakerSpeedMs\": [100, 200, 300], "
                + "\"trunkCapacity\": [2, 3], \"storageCapacity\":20, "
                + "\"minDeliveryTimeMs\":200, \"maxDeliveryTimeMs\":300}";
        Files.writeString(file, jsonContent);

        JsonParserConf parser = new JsonParserConf();
        Configuration config = parser.parse(file.toString());

        assertNotNull(config);
        assertEquals(3, config.bakerSpeedMs().length);
        assertEquals(20, config.storageCapacity());
    }

    @Test
    void testParseMissingFile() {
        JsonParserConf parser = new JsonParserConf();
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            parser.parse("non_existent_file.json");
        });
        assertNotNull(exception.getMessage());
    }
}