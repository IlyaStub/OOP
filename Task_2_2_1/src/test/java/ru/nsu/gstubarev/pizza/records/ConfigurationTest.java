package ru.nsu.gstubarev.pizza.records;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ConfigurationTest {
    @Test
    void testConfigurationCreation() {
        Configuration config = new Configuration(3, 2, 10, 1500, 1000, 3000);

        assertEquals(3, config.bakersCount());
        assertEquals(2, config.deliverymanCount());
        assertEquals(10, config.storageCapacity());
        assertEquals(1500, config.bakerSpeedMs());
        assertEquals(1000, config.minDeliveryTimeMs());
        assertEquals(3000, config.maxDeliveryTimeMs());
    }
}