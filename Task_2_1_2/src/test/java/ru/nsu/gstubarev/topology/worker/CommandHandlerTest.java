package ru.nsu.gstubarev.topology.worker;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.junit.jupiter.api.Test;

/**
 * Test.
 */
class CommandHandlerTest {
    @Test
    void testHandlePing() {
        CommandHandler handler = new CommandHandler();
        StringWriter stringWriter = new StringWriter();
        PrintWriter out = new PrintWriter(stringWriter, true);

        handler.handle("PING", out);

        assertTrue(stringWriter.toString().contains("PONG"));
    }

    @Test
    void testHandleCheckWithCompositeNumber() {
        CommandHandler handler = new CommandHandler();
        StringWriter stringWriter = new StringWriter();
        PrintWriter out = new PrintWriter(stringWriter, true);

        handler.handle("CHECK 4,7,11", out);

        assertTrue(stringWriter.toString().contains("RESULT true"));
    }

    @Test
    void testHandleCheckWithAllPrimes() {
        CommandHandler handler = new CommandHandler();
        StringWriter stringWriter = new StringWriter();
        PrintWriter out = new PrintWriter(stringWriter, true);

        handler.handle("CHECK 2,3,5,7,11,13", out);

        assertTrue(stringWriter.toString().contains("RESULT false"));
    }

    @Test
    void testHandleUnknownCommand() {
        CommandHandler handler = new CommandHandler();
        StringWriter stringWriter = new StringWriter();
        PrintWriter out = new PrintWriter(stringWriter, true);

        handler.handle("UNKNOWN", out);

        assertTrue(stringWriter.toString().isEmpty());
    }
}