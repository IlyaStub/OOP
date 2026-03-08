package ru.nsu.gstubarev.pizza.impl;

import ru.nsu.gstubarev.pizza.enums.PizzaType;
import ru.nsu.gstubarev.pizza.intefaces.IClient;
import ru.nsu.gstubarev.pizza.records.Order;
import ru.nsu.gstubarev.pizza.records.Pizza;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class ClientImpl implements IClient, Runnable {
    private final BlockingQueue<Order> orderQueue;
    private volatile boolean isOrdering = true;
    private final Random random = new Random();

    /**
     * This is constructor.
     *
     * @param orderQueue queue of order
     */
    public ClientImpl(BlockingQueue<Order> orderQueue) {
        this.orderQueue = orderQueue;
    }

    @Override
    public void run() {
        while (this.isOrdering) {
            Order order = createOrder();
            try {
                orderQueue.put(order);
                Thread.sleep(1000 + random.nextInt(2000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

        }
    }

    @Override
    public Order createOrder() {
        LinkedList<Pizza> pizzas = new LinkedList<>();
        int pizzasCount = 1 + random.nextInt(3);

        PizzaType[] types = PizzaType.values();
        for (int i = 0; i < pizzasCount; i++) {
            PizzaType randomType = types[random.nextInt(types.length)];
            pizzas.add(new Pizza(randomType, 30, 500));
        }

        return new Order(pizzas);
    }

    @Override
    public void stopOrdering() {
        this.isOrdering = false;
    }
}
