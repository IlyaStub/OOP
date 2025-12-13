package ru.nsu.gstubarev.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class QuoteBlockTest {

    @Test
    void serialize() {
        QuoteBlock quote1 = new QuoteBlock("Single line quote");
        assertEquals("> Single line quote", quote1.serialize());

        QuoteBlock quote2 = new QuoteBlock("First line\nSecond line\nThird line");
        assertEquals("> First line\n> Second line\n> Third line", quote2.serialize());

        QuoteBlock quote3 = QuoteBlock.builder()
                .addLine("Line 1")
                .addLine("Line 2")
                .addLine("Line 3")
                .build();
        assertEquals("> Line 1\n> Line 2\n> Line 3", quote3.serialize());
    }

    @Test
    void testEquals() {
        QuoteBlock quote1 = new QuoteBlock("Test quote");
        QuoteBlock quote2 = new QuoteBlock("Test quote");
        QuoteBlock quote3 = new QuoteBlock("Different quote");
        List<String> lines = Arrays.asList("Line 1", "Line 2");
        QuoteBlock quote4 = new QuoteBlock(lines);

        assertEquals(quote1, quote2);
        assertNotEquals(quote1, quote3);
        assertEquals(quote4, new QuoteBlock(lines));
        assertNotEquals(quote1, null);
    }

    @Test
    void builder() {
        QuoteBlock.QuoteBuilder builder = QuoteBlock.builder();
        assertNotNull(builder);

        QuoteBlock quote = builder
                .addLine("First")
                .addLine("Second")
                .addLine("Third")
                .build();

        assertNotNull(quote);
        assertEquals("> First\n> Second\n> Third", quote.serialize());
    }
}