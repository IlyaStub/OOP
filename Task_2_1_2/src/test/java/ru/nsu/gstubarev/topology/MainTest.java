package ru.nsu.gstubarev.topology;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test.
 */
class MainTest {

    @BeforeEach
    void setUp() {
        Main.setStartupWaitMs(100);
    }

    @Test
    void testMainWithNoArgs() {
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }

    @Test
    void testMainWithInvalidCommand() {
        assertDoesNotThrow(() -> Main.main(new String[]{"invalid"}));
    }

    @Test
    void testMainWithMasterMissingPort() {
        assertDoesNotThrow(() -> Main.main(new String[]{"master"}));
    }

    @Test
    void testMainWithWorkerMissingArgs() {
        assertDoesNotThrow(() -> Main.main(new String[]{"worker"}));
    }

    @Test
    void testMainWithWorkerValidPort() {
        assertDoesNotThrow(() -> Main.main(new String[]{"worker", "19401"}));
    }

    @Test
    void testRunMasterNoWorkersLogsError() {
        assertDoesNotThrow(() -> Main.main(new String[]{"master", "19400"}));
    }
}