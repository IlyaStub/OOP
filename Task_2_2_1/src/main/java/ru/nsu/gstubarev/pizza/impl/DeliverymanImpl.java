package ru.nsu.gstubarev.pizza.impl;

import ru.nsu.gstubarev.pizza.enums.OrderState;
import ru.nsu.gstubarev.pizza.intefaces.Ideliveryman;
import ru.nsu.gstubarev.pizza.intefaces.IgeneratorTime;
import ru.nsu.gstubarev.pizza.intefaces.Istorage;
import ru.nsu.gstubarev.pizza.records.Order;

/**
 * Implementation of a deliveryman that takes orders from storage and delivers them.
 */
public class DeliverymanImpl implements Ideliveryman, Runnable {
    private final int id;
    private final int trunkCapacity;
    private final IgeneratorTime generatorTime;
    private final Istorage storage;
    private volatile boolean isWorking = true;

    /**
     * This is constructor.
     *
     * @param id id
     * @param trunkCapacity trunk's capacity
     * @param generatorTime time gen
     * @param storage storage of pizza
     */
    public DeliverymanImpl(int id, int trunkCapacity,
                           IgeneratorTime generatorTime, Istorage storage) {
        this.id = id;
        this.trunkCapacity = trunkCapacity;
        this.generatorTime = generatorTime;
        this.storage = storage;
    }

    @Override
    public void run() {
        while (isWorking || !storage.isEmpty()) {
            try {
                takePizzas(storage);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Deliveryman " + this.id + " is finished");
    }

    @Override
    public void takePizzas(Istorage storage) throws InterruptedException {
        Order order = storage.takeOrder(trunkCapacity);
        if (order != null) {
            order.changeState(OrderState.IN_DELIVERY);
            try {
                Thread.sleep(generatorTime.generateDeliveryTime());
                order.changeState(OrderState.DELIVERED);
            } catch (InterruptedException e) {
                System.err.println("Deliveryman " + this.id + " was interrupted! Order " + order.getId() + " is lost.");
                order.changeState(OrderState.IN_STORAGE);
                throw e;
            }
        }
    }

    @Override
    public void stopWork() {
        this.isWorking = false;
    }
}
