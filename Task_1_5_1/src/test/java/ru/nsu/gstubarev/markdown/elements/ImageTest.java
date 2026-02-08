package ru.nsu.gstubarev.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.markdown.exceptions.EmptyElementException;

class ImageTest {

    @Test
    void serializeImgWithoutTitle() {
        Image img = new Image("Cat", "cat.jpg");
        assertEquals("![Cat](cat.jpg)", img.serialize());
    }

    @Test
    void serializeFoolImg() {
        Image img = new Image("Cat", "cat.jpg", "A cute cat");
        assertEquals("![Cat](cat.jpg \"A cute cat\")", img.serialize());
    }

    @Test
    void serializeOnlyImgUrl() {
        Image img = new Image(null, "cat.jpg");
        assertEquals("![One second](cat.jpg)", img.serialize());
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
        assertThrows(EmptyElementException.class,
                () -> new Image("Alt", null));
    }
}