package ru.nsu.gstubarev.topology.master;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Test.
 */
class MasterTest {
    @Test
    void testConstructorAndStartListener() {
        Master master = new Master(9999);

        assertDoesNotThrow(master::startRegistrationListener);
    }

    @Test
    void testHasCompositeWithNoWorkersThrowsException() {
        Master master = new Master(9999);
        master.startRegistrationListener();
        long[] array = {1, 2, 3, 4, 5};

        assertThrows(IllegalStateException.class, () -> {
            master.hasComposite(array);
        });
    }

    @Test
    void testStop() {
        Master master = new Master(9999);

        assertDoesNotThrow(master::stop);
    }

    @Test
    void testStartRegistrationListenerMultipleTimes() {
        Master master = new Master(8888);

        assertDoesNotThrow(() -> {
            master.startRegistrationListener();
            master.startRegistrationListener();
        });
    }
}