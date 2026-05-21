package ru.nsu.gstubarev.topology.worker;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Test.
 */
class WorkerRegistrarTest {

    @Test
    void testConstructor() {
        WorkerRegistrar registrar = new WorkerRegistrar("localhost", 9999, 8080);
        assertThrows(Exception.class, registrar::register);
    }

    @Test
    void testRegisterWithInvalidMaster() {
        WorkerRegistrar registrar = new WorkerRegistrar("unknown-host", 9999, 8080);
        assertThrows(Exception.class, registrar::register);
    }
}