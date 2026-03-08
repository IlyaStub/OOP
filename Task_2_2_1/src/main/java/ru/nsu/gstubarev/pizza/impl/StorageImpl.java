package ru.nsu.gstubarev.pizza.impl;

import ru.nsu.gstubarev.pizza.enums.OrderState;
import ru.nsu.gstubarev.pizza.intefaces.IStorage;
import ru.nsu.gstubarev.pizza.records.Order;
import ru.nsu.gstubarev.pizza.records.Pizza;

import java.util.LinkedList;

public class StorageImpl implements IStorage {
    private final int maxCapacity;
    private int currentPizzasCount = 0;
    private final LinkedList<Order> orders = new LinkedList<>();

    /**
     * The constructor.
     *
     * @param maxCapacity max capacity of storage
     */
    public StorageImpl(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public synchronized void addOrder(Order order) throws InterruptedException {
        int pizzasInOrder = order.getPizzas().size();

        while (currentPizzasCount + pizzasInOrder > maxCapacity) {
            this.wait();
        }

        orders.add(order);
        currentPizzasCount += pizzasInOrder;

        order.changeState(OrderState.IN_STORAGE);

        this.notifyAll();
    }

    @Override
    public synchronized Order takeOrder(int trunkCapacity) throws InterruptedException {
        while (orders.isEmpty()) {
            this.wait();
        }

        Order currentOrder = orders.peek();
        int pizzasInOrder = currentOrder.getPizzas().size();

        if (pizzasInOrder <= trunkCapacity) {
            orders.poll();
            currentPizzasCount -= pizzasInOrder;
            this.notifyAll();
            return currentOrder;
        } else {
            LinkedList<Pizza> partialPizzas = new LinkedList<>();
            for (int i = 0; i < trunkCapacity; i++) {
                partialPizzas.add(currentOrder.getPizzas().poll());
            }

            currentPizzasCount -= trunkCapacity;
            this.notifyAll();

            return new Order(currentOrder.getId(), currentOrder.getState(), partialPizzas);
        }
    }

    @Override
    public synchronized boolean isEmpty() {
        return orders.isEmpty();
    }
}