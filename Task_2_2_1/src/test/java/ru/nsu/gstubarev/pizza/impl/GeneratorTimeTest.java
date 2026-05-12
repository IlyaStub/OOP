package ru.nsu.gstubarev.pizza.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GeneratorTimeTest {

    @Test
    void testGenerateDeliveryTime() {
        GeneratorTime timeGen = new GeneratorTime(1000, 3000);

        for (int i = 0; i < 50; i++) {
            int time = timeGen.generateDeliveryTime();
            assertTrue(time >= 1000 && time <= 3000);
        }
    }
}