package ru.nsu.gstubarev.pizza.enums;

/**
 * State of order.
 */
public enum OrderState {
    CREATED,
    IN_PROGRESS,
    IN_STORAGE,
    IN_DELIVERY,
    DELIVERED,
    CANCELED
}
