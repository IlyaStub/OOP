package ru.nsu.gstubarev.topology.worker;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class WorkerRegistrarTest {

    @Test
    void testConstructor() {
        assertDoesNotThrow(() -> {
            WorkerRegistrar registrar = new WorkerRegistrar("localhost", 9999, 8080);
        });
    }

    @Test
    void testRegisterThrowsWithInvalidMaster() {
        WorkerRegistrar registrar = new WorkerRegistrar("unknown-host", 9999, 8080);
        assertThrows(Exception.class, registrar::register);
    }
}