package ru.nsu.gstubarev.pizza.intefaces;

/**
 * This interface describes a class for generation time for delivering order or cooking of pizza.
 */
public interface IGeneratorTime {
    /**
     * Generates a random time duration for delivery.
     *
     * @return delivery time in milliseconds
     */
    int generateDeliveryTime();
}
