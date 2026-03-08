package ru.nsu.gstubarev.pizza.impl;

import ru.nsu.gstubarev.pizza.enums.OrderState;
import ru.nsu.gstubarev.pizza.intefaces.IDeliveryman;
import ru.nsu.gstubarev.pizza.intefaces.IGeneratorTime;
import ru.nsu.gstubarev.pizza.intefaces.IStorage;
import ru.nsu.gstubarev.pizza.records.Order;

public class DeliverymanImpl implements IDeliveryman, Runnable {
    private final int id;
    private final int trunkCapacity;
    private final IGeneratorTime generatorTime;
    private final IStorage storage;
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
                           IGeneratorTime generatorTime, IStorage storage) {
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
    public void takePizzas(IStorage storage) throws InterruptedException {
        Order order = storage.takeOrder(trunkCapacity);
        if (order != null) {
            order.changeState(OrderState.IN_DELIVERY);
            Thread.sleep(generatorTime.generateDeliveryTime());
            order.changeState(OrderState.DELIVERED);
        }
    }

    @Override
    public void stopWork() {
        this.isWorking = false;
    }
}
