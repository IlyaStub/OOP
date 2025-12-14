package ru.nsu.gstubarev.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class LinkTest {
    @Test
    void serializeSimpleLink() {
        Link link = new Link("Google", "https://google.com");
        assertEquals("[Google](https://google.com)", link.serialize());
    }

    @Test
    void serializeLinkWithInjectionText() {
        Link link = new Link("a](lya)\n[", "https://google.com");
        assertEquals("[a\\](lya)\\n\\[](https://google.com)", link.serialize());
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