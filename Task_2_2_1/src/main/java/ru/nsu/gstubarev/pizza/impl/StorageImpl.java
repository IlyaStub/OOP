package ru.nsu.gstubarev.pizza.impl;

import java.util.LinkedList;
import ru.nsu.gstubarev.pizza.enums.OrderState;
import ru.nsu.gstubarev.pizza.intefaces.Istorage;
import ru.nsu.gstubarev.pizza.records.Order;
import ru.nsu.gstubarev.pizza.records.Pizza;

/**
 * Implementation of pizza storage with capacity limit and order management.
 */
public class StorageImpl implements Istorage {
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

        if (pizzasInOrder > maxCapacity) {
            System.err.println("Order " + order.getId() + " is too big.");
            order.changeState(OrderState.CANCELED);
            return;
        }

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
            this.wait(1000);
            if (orders.isEmpty()) {
                return null;
            }
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