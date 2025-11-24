package ru.nsu.gstubarev.poisk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.gstubarev.poisk.exceptions.SearchInFileException;

class SearchSubstringTest {

    @TempDir
    Path tempDir;

    @Test
    void testRussianSearch() {
        File testFile = createTestFile("абракадабра");
        List<Long> result = SearchSubstring.find(testFile.getPath(), "бра");
        assertEquals(List.of(1L, 8L), result);
    }

    @Test
    void testEnglishText() {
        File testFile = createTestFile("abracadabra");
        List<Long> result = SearchSubstring.find(testFile.getPath(), "bra");
        assertEquals(List.of(1L, 8L), result);
    }

    @Test
    void testNoMatches() {
        File testFile = createTestFile("абракадабра");
        List<Long> result = SearchSubstring.find(testFile.getPath(), "xyz");
        assertTrue(result.isEmpty());
    }

    @Test
    void testStrPatternSuffix() {
        File file = createTestFile("ababababab");
        List<Long> result = SearchSubstring.find(file.getPath(), "ababab");
        assertEquals(List.of(0L, 2L, 4L), result);
    }

    @Test
    void testPatternLongerThanBuffer() {
        StringBuilder pattern = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            pattern.append('a');
        }
        pattern.append('b');

        StringBuilder content = new StringBuilder();
        content.append(pattern);
        content.append("tail");

        File file = createTestFile(content.toString());
        List<Long> result = SearchSubstring.find(file.getPath(), pattern.toString());
        assertEquals(List.of(0L), result);
    }

    @Test
    void testEmptyFile() {
        File testFile = createTestFile("");
        List<Long> result = SearchSubstring.find(testFile.getPath(), "gfd");
        assertTrue(result.isEmpty());
    }

    @Test
    void testEmptyPattern() {
        File testFile = createTestFile("абракадабра");
        List<Long> result = SearchSubstring.find(testFile.getPath(), "");
        assertTrue(result.isEmpty());
    }

    @Test
    void testSingleCharacterSearch() {
        File testFile = createTestFile("hello world");
        List<Long> result = SearchSubstring.find(testFile.getPath(), "o");
        assertEquals(List.of(4L, 7L), result);
    }

    @Test
    void testManySingleA() {
        File testFile = createTestFile("aaaaaaa");
        List<Long> result = SearchSubstring.find(testFile.getPath(), "a");
        assertEquals(List.of(0L, 1L, 2L, 3L, 4L, 5L, 6L), result);
    }

    @Test
    void testManyOverlappingA() {
        File testFile = createTestFile("aaaaaaa");
        List<Long> result = SearchSubstring.find(testFile.getPath(), "aa");
        assertEquals(List.of(0L, 1L, 2L, 3L, 4L, 5L), result);
    }

    @Test
    void testEmptyFilename() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> SearchSubstring.find("", "test"));
        assertTrue(exception.getMessage().contains("Filename cannot be empty"));
    }

    @Test
    void testPatternOnBufferBoundary() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8190; i++) {
            sb.append('a');
        }
        sb.append("bra");
        sb.append("xyz");
        File file = createTestFile(sb.toString());
        List<Long> result = SearchSubstring.find(file.getPath(), "bra");
        assertEquals(List.of(8190L), result);
    }

    @Test
    void testLargeFile() throws IOException {
        File testFile = tempDir.resolve("large.txt").toFile();

        String largeContent = "x".repeat(1024 * 1024);
        long tarPos = 15L * 1024 * 1024 * 1024;

        try (FileOutputStream fos = new FileOutputStream(testFile, true)) {
            for (int i = 0; i < 15 * 1024; i++) {
                fos.write(largeContent.getBytes(StandardCharsets.UTF_8));
            }
            fos.write("target".getBytes(StandardCharsets.UTF_8));
        }

        List<Long> result = SearchSubstring.find(testFile.getPath(), "target");
        assertEquals(List.of(tarPos), result);
    }

    @Test
    void testFileNotFound() {
        Exception exception = assertThrows(SearchInFileException.class,
                () -> SearchSubstring.find("nonexistent.txt", "test"));
        assertTrue(exception.getMessage().contains("nonexistent.txt"));
    }

    @Test
    void testMainWithValidArgs() throws Exception {
        File testFile = createTestFile("abracadabra");
        String[] args = {
                testFile.getPath(), "bra"
        };

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            SearchSubstring.main(args);
            String output = outContent.toString(StandardCharsets.UTF_8.name());
            assertTrue(output.contains("Result: [1, 8]"), "Output should contain result");
        } finally {
            System.setOut(originalOut);
        }
    }

    private File createTestFile(String content) {
        try {
            File testFile = tempDir.resolve("test.txt").toFile();
            try (OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(testFile), StandardCharsets.UTF_8)) {
                writer.write(content);
            }
            return testFile;
        } catch (Exception e) {
            throw new RuntimeException("Test file creation failed", e);
        }
    }
}