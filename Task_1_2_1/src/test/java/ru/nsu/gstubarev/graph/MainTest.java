package ru.nsu.gstubarev.graph;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void testMain() {
        PrintStream originalOut = System.out;

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            Main.main(new String[]{});

            String output = outputStream.toString();

            assertTrue(output.contains("Adjacency Matrix Graph"));
            assertTrue(output.contains("Incidence Matrix Graph"));
            assertTrue(output.contains("Adjacency List Graph"));
            assertTrue(output.contains("Vertices:"));
            assertTrue(output.contains("Edges:"));

        } finally {
            System.setOut(originalOut);
        }
    }
}