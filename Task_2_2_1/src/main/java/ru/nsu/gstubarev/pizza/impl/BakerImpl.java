package ru.nsu.gstubarev.pizza.impl;

import static ru.nsu.gstubarev.pizza.enums.OrderState.IN_PROGRESS;

import ru.nsu.gstubarev.pizza.intefaces.Ibaker;
import ru.nsu.gstubarev.pizza.intefaces.Istorage;
import ru.nsu.gstubarev.pizza.records.Order;
import java.util.concurrent.BlockingQueue;

public class BakerImpl implements Ibaker, Runnable {
    private final int id;
    private final int cookingSpeedMs;
    private volatile boolean isWorking = true;
    private final BlockingQueue<Order> orderQueue;
    private final Istorage storage;

    /**
     * This is constructor.
     *
     * @param id id
     * @param cookingSpeedMs time for one pizza
     * @param orderQueue queue of order
     * @param storage storage of order
     */
    public BakerImpl(int id, int cookingSpeedMs, BlockingQueue<Order> orderQueue, Istorage storage) {
        this.id = id;
        this.cookingSpeedMs = cookingSpeedMs;
        this.orderQueue = orderQueue;
        this.storage = storage;
    }

    @Override
    public void takeOrder(Order order) {
        try {
            order.changeState(IN_PROGRESS);

            int workTime = cookingSpeedMs * order.getPizzas().size();
            Thread.sleep(workTime);

            storage.addOrder(order);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void stopWork() {
        this.isWorking = false;
    }

    @Override
    public void run() {
        while (isWorking || !orderQueue.isEmpty()) {
            try {
                Order order = orderQueue.poll(500, java.util.concurrent.TimeUnit.MILLISECONDS);
                if (order != null) {
                    takeOrder(order);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Baker " + id + " is finished.");
    }
}
