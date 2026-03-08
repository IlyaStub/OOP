package ru.nsu.gstubarev.pizza.intefaces;

import ru.nsu.gstubarev.pizza.records.Order;
import ru.nsu.gstubarev.pizza.records.Pizza;

import java.util.LinkedList;

/**
 * Interface representing a client that generates new orders.
 */
public interface IClient {
    /**
     * Creates a new random pizza order.
     *
     * @return the newly created order
     */
    Order createOrder();

    /**
     * Stops the client from generating new orders.
     */
    void stopOrdering();
}
