package ru.nsu.gstubarev.topology;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/**
 * Test.
 */
class MainTest {

    @Test
    void testMainWithNoArgs() {
        assertDoesNotThrow(() -> {
            Main.main(new String[]{});
        });
    }

    @Test
    void testMainWithInvalidCommand() {
        assertDoesNotThrow(() -> {
            Main.main(new String[]{"invalid"});
        });
    }

    @Test
    void testMainWithMasterMissingPort() {
        assertDoesNotThrow(() -> {
            Main.main(new String[]{"master"});
        });
    }

    @Test
    void testMainWithWorkerMissingArgs() {
        assertDoesNotThrow(() -> {
            Main.main(new String[]{"worker"});
        });
    }

    @Test
    void testMainWithWorkerPartialArgs() {
        assertDoesNotThrow(() -> {
            Main.main(new String[]{"worker", "localhost"});
        });
    }
}