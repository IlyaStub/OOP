package ru.nsu.gstubarev.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.markdown.exceptions.SelectionsSequenceException;

class HeadingTest {
    @Test
    void serializeH1() {
        Heading h1 = new Heading(1, "Main Title");
        assertEquals("# Main Title", h1.serialize());
    }

    @Test
    void serializeH2() {
        Heading h2 = new Heading(2, "Subtitle");
        assertEquals("## Subtitle", h2.serialize());
    }

    @Test
    void serializeH6() {
        Heading h6 = new Heading(6, "Smallest");
        assertEquals("###### Smallest", h6.serialize());
    }

    @Test
    void testEquals() {
        Heading h1 = new Heading(1, "Title");
        Heading h2 = new Heading(1, "Title");
        Heading h3 = new Heading(2, "Title");
        Heading h4 = new Heading(1, "Different");

        assertEquals(h1, h2);
        assertNotEquals(h1, h3);
        assertNotEquals(h1, h4);
        assertNotEquals(h1, null);
    }

    @Test
    void constructorInvalidLevelShouldThrowException() {
        assertThrows(SelectionsSequenceException.class,
                () -> new Heading(0, "Invalid"));
    }
}