package ru.nsu.gstubarev.pizza.intefaces;

import ru.nsu.gstubarev.pizza.records.Order;

/**
 * Interface representing a storage for ready pizza orders.
 */
public interface IStorage {
    /**
     * Adds a ready order to the storage. Blocks if storage is full.
     *
     * @param order the order to add
     * @throws InterruptedException if thread is interrupted while waiting
     */
    void addOrder(Order order) throws InterruptedException;

    /**
     * Takes an order from the storage. Blocks if storage is empty.
     *
     * @param trunkCapacity maximum number of pizzas the courier can take
     * @return the order taken from storage
     * @throws InterruptedException if thread is interrupted while waiting
     */
    Order takeOrder(int trunkCapacity) throws InterruptedException;

    /**
     * Checks if the storage is currently empty.
     *
     * @return true if storage is empty, false otherwise
     */
    boolean isEmpty();
}
