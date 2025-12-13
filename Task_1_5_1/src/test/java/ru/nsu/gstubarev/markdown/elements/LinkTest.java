package ru.nsu.gstubarev.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class LinkTest {
    @Test
    void serialize() {
        Link link1 = new Link("Google", "https://google.com");
        assertEquals("[Google](https://google.com)", link1.serialize());

        Link link2 = new Link("Search", "https://example.com/search?q=test");
        assertEquals("[Search](https://example.com/search?q=test)", link2.serialize());
    }

    @Test
    void testEquals() {
        Link link1 = new Link("Text", "http://example.com");
        Link link2 = new Link("Text", "http://example.com");
        Link link3 = new Link("Different", "http://example.com");
        assertEquals(link1, link2);
        assertNotEquals(link1, link3);
        assertNotEquals(link1, null);
    }
}