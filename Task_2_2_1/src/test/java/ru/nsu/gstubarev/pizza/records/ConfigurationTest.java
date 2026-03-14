package ru.nsu.gstubarev.pizza.records;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ConfigurationTest {
    @Test
    void testConfigurationCreation() {
        Configuration config = new Configuration(new int[]{123, 12, 2}, new int[]{2, 4, 5}, 100, 1500, 3000);

        assertEquals(3, config.bakerSpeedMs().length);
        assertEquals(3, config.trunkCapacity().length);
        assertEquals(100, config.storageCapacity());
        assertEquals(1500, config.minDeliveryTimeMs());
        assertEquals(3000, config.maxDeliveryTimeMs());
    }
}