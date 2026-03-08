package ru.nsu.gstubarev.pizza.records;

/**
 * Configuration parameters for the pizza delivery system.
 *
 * @param bakersCount number of bakers working in the pizzeria
 * @param deliverymanCount number of couriers available for delivery
 * @param storageCapacity maximum number of orders that can be stored
 * @param bakerSpeedMs time in milliseconds to prepare one pizza
 * @param minDeliveryTimeMs minimum delivery time in milliseconds
 * @param maxDeliveryTimeMs maximum delivery time in milliseconds
 */
public record Configuration(
        int bakersCount,
        int deliverymanCount,
        int storageCapacity,
        int bakerSpeedMs,
        int minDeliveryTimeMs,
        int maxDeliveryTimeMs
) {}