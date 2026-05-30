package ru.nsu.gstubarev.topology;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test.
 */
class CommandTest {
    @Test
    void testRegisterCommandText() {
        assertEquals("REGISTER", Command.REGISTER.getText());
    }

    @Test
    void testOkCommandText() {
        assertEquals("OK", Command.OK.getText());
    }

    @Test
    void testCheckCommandText() {
        assertEquals("CHECK", Command.CHECK.getText());
    }

    @Test
    void testResultCommandText() {
        assertEquals("RESULT", Command.RESULT.getText());
    }

    @Test
    void testPingCommandText() {
        assertEquals("PING", Command.PING.getText());
    }

    @Test
    void testPongCommandText() {
        assertEquals("PONG", Command.PONG.getText());
    }
}