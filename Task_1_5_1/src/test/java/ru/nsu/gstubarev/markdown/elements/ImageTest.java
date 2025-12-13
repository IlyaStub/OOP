package ru.nsu.gstubarev.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ImageTest {

    @Test
    void serialize() {
        Image img1 = new Image("Cat", "cat.jpg");
        assertEquals("![Cat](cat.jpg)", img1.serialize());

        Image img2 = new Image("Cat", "cat.jpg", "A cute cat");
        assertEquals("![Cat](cat.jpg \"A cute cat\")", img2.serialize());

        Image img3 = new Image(null, "cat.jpg");
        assertEquals("![One second](cat.jpg)", img3.serialize());
    }

    @Test
    void testEquals() {
        Image img1 = new Image("Cat", "cat.jpg", "Title");
        Image img2 = new Image("Cat", "cat.jpg", "Title");
        Image img3 = new Image("Dog", "cat.jpg", "Title");
        assertEquals(img1, img2);
        assertNotEquals(img1, img3);
        assertNotEquals(img1, null);
    }

    @Test
    void constructorEmptyUrlShouldThrowException() {
        assertThrows(ru.nsu.gstubarev.markdown.exceptions.EmptyElementException.class,
                () -> new Image("Alt", null));
    }
}