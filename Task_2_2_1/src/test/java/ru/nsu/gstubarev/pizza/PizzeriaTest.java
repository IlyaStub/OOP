package ru.nsu.gstubarev.pizza;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class PizzeriaTest {
    @Test
    void testMainExecution() {
        assertDoesNotThrow(() -> {
            Pizzeria.main(new String[]{});
        });
    }
}