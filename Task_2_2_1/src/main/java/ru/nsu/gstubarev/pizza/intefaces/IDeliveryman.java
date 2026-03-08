package ru.nsu.gstubarev.pizza.intefaces;

/**
 * Interface representing a deliveryman who delivers pizza orders.
 */
public interface IDeliveryman {
    /**
     * Takes pizzas from the storage to deliver.
     *
     * @param storage the storage to take pizzas from
     * @throws InterruptedException if thread is interrupted
     */
    void takePizzas(IStorage storage) throws InterruptedException;

    /**
     * Signals the deliveryman to stop working.
     */
    void stopWork();
}
