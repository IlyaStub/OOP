package ru.nsu.gstubarev.topology.master;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for MasterInfo.
 */
class MasterInfoTest {

    @Test
    void testGetHost() {
        MasterInfo info = new MasterInfo("192.168.1.1", 9000);
        assertEquals("192.168.1.1", info.getHost());
    }

    @Test
    void testGetPort() {
        MasterInfo info = new MasterInfo("192.168.1.1", 9000);
        assertEquals(9000, info.getPort());
    }

    @Test
    void testToString() {
        MasterInfo info = new MasterInfo("192.168.1.1", 9000);
        assertEquals("192.168.1.1:9000", info.toString());
    }
}