package ru.nsu.gstubarev.dsl.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * TEST.
 */
public class ExceptionsTest {
    @Test
    public void testCommandExecutionException() {
        Throwable cause = new RuntimeException("Process failed");
        CommandExecutionException e = new CommandExecutionException("Error cmd", cause);

        assertEquals("Error cmd", e.getMessage());
        assertSame(cause, e.getCause());
    }

    @Test
    public void testGitOperationException() {
        Throwable cause = new RuntimeException("Git clone failed");
        GitOperationException e = new GitOperationException("Error git", cause);

        assertEquals("Error git", e.getMessage());
        assertSame(cause, e.getCause());
    }

    @Test
    public void testReportGenerationException() {
        Throwable cause = new RuntimeException("HTML save failed");
        ReportGenerationException e = new ReportGenerationException("Error report", cause);

        assertEquals("Error report", e.getMessage());
        assertSame(cause, e.getCause());
    }

    @Test
    public void testStyleCheckException() {
        Throwable cause = new RuntimeException("Checkstyle run failed");
        StyleCheckException e = new StyleCheckException("Error style", cause);

        assertEquals("Error style", e.getMessage());
        assertSame(cause, e.getCause());
    }

    @Test
    public void testTestParsingException() {
        Throwable cause = new RuntimeException("XML parse error");
        TestParsingException e = new TestParsingException("Error XML", cause);

        assertEquals("Error XML", e.getMessage());
        assertSame(cause, e.getCause());
    }

    @Test
    public void testToolDownloadException() {
        Throwable cause = new RuntimeException("Network unreachable");
        ToolDownloadException e = new ToolDownloadException("Error download", cause);

        assertEquals("Error download", e.getMessage());
        assertSame(cause, e.getCause());
    }
}