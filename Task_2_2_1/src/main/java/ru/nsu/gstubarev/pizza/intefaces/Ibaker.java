package ru.nsu.gstubarev.pizza.intefaces;

import ru.nsu.gstubarev.pizza.records.Order;

/**
 * Interface representing a baker who prepares pizza orders.
 */
public interface Ibaker {
    /**
     * Takes an order and simulates the cooking process.
     *
     * @param order the order to prepare
     */
    void takeOrder(Order order);

    /**
     * Signals the baker to stop working after finishing current tasks.
     */
    void stopWork();
}
