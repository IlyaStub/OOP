package ru.nsu.gstubarev.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TableTest {

    @Test
    void serialize() {
        Table table1 = Table.builder()
                .addRow("Header1", "Header2")
                .addRow("Data1", "Data2")
                .build();
        String expected1 = "| Header1 | Header2 |\n| :--- | :--- |\n| Data1 | Data2 |\n";
        assertEquals(expected1, table1.serialize());

        Table table2 = Table.builder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_CENTER)
                .addRow("ID", "Name")
                .addRow("1", "John")
                .addRow("2", "Jane")
                .build();
        String result2 = table2.serialize();
        assertTrue(result2.contains("| ---: | :---: |"));
        assertTrue(result2.contains("| 1 | John |"));
        assertTrue(result2.contains("| 2 | Jane |"));
    }

    @Test
    void serializeWithRowLimit() {
        Table table = Table.builder()
                .withRowLimit(2)
                .addRow("H1", "H2", "H3")
                .addRow("D1", "D2", "D3")
                .addRow("D4", "D5", "D6")
                .addRow("D7", "D8", "D9")
                .build();

        String result = table.serialize();
        long lineCount = result.lines().count();
        assertEquals(4, lineCount);
        assertTrue(result.contains("| D1 | D2 | D3 |"));
        assertTrue(result.contains("| D4 | D5 | D6 |"));
    }

    @Test
    void testEquals() {
        Table table1 = Table.builder()
                .addRow("A", "B").addRow("C", "D").build();
        Table table2 = Table.builder().addRow("A", "B").addRow("C", "D").build();
        assertEquals(table1, table2);

        Table table3 = Table.builder()
                .withAlignments(Table.ALIGN_CENTER, Table.ALIGN_CENTER)
                .addRow("A", "B")
                .addRow("C", "D")
                .build();

        Table table4 = Table.builder()
                .withRowLimit(1)
                .addRow("A", "B")
                .addRow("C", "D")
                .build();

        assertNotEquals(table1, table3);
        assertNotEquals(table1, table4);
        assertNotEquals(table1, null);
    }

    @Test
    void builder() {
        Table.TableBuilder builder = Table.builder();
        assertNotNull(builder);

        Table table = builder
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_RIGHT, Table.ALIGN_CENTER)
                .withRowLimit(2)
                .addRow("H1", "H2", "H3")
                .addRow("D1", "D2", "D3")
                .addRow("D4", "D5", "D6")
                .build();

        assertNotNull(table);
        assertTrue(table.serialize().contains("| H1 | H2 | H3 |"));
    }

    @Test
    void rowLimitRespected() {
        Table table = Table.builder()
                .withRowLimit(1)
                .addRow("H1", "H2")
                .addRow("D1", "D2")
                .addRow("D3", "D4")
                .addRow("D5", "D6")
                .build();

        long rowCount = table.serialize().lines().count();
        assertEquals(3, rowCount);
    }
}