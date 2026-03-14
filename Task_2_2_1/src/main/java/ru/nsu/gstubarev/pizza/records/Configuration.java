package ru.nsu.gstubarev.pizza.records;

/**
 * Configuration parameters for the pizza delivery system.
 *
 * @param bakerSpeedMs time in milliseconds to prepare one pizza for every baker
 * @param trunkCapacity capacity trunk for every deliveryman
 * @param storageCapacity maximum number of orders that can be stored
 * @param minDeliveryTimeMs minimum delivery time in milliseconds
 * @param maxDeliveryTimeMs maximum delivery time in milliseconds
 */
public record Configuration(
        int[] bakerSpeedMs,
        int[] trunkCapacity,
        int storageCapacity,
        int minDeliveryTimeMs,
        int maxDeliveryTimeMs
) {}