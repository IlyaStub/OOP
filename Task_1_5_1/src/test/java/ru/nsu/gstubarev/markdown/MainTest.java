package ru.nsu.gstubarev.markdown;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void main() {
        assertDoesNotThrow(() -> Main.main(new String[]{}));
        assertDoesNotThrow(() -> Main.main(null));
    }
}