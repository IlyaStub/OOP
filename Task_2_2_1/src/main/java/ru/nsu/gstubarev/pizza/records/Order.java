package ru.nsu.gstubarev.pizza.records;

import static ru.nsu.gstubarev.pizza.enums.OrderState.CREATED;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import ru.nsu.gstubarev.pizza.enums.OrderState;

/**
 * Class representing a customer's order containing pizzas.
 */
public class Order {
    private static final AtomicInteger counter = new AtomicInteger(0);
    private final long id;
    private OrderState state;
    private LinkedList<Pizza> pizzas;

    /**
     * Creates a new order with the specified list of pizzas.
     * Assigns a unique ID and sets initial state to CREATED.
     *
     * @param pizzas the list of pizzas in this order
     */
    public Order(LinkedList<Pizza> pizzas) {
        this.id = counter.incrementAndGet();
        this.state = CREATED;
        System.out.println("[" + this.id + "] [" + this.state + "]");
        this.pizzas = pizzas;
    }

    /**
     * This is the second constructor.
     *
     * @param id id
     * @param state state of order
     * @param pizzas pizzas in order
     */
    public Order(long id, OrderState state, LinkedList<Pizza> pizzas) {
        this.id = id;
        this.state = state;
        this.pizzas = pizzas;
    }

    /**
     * Returns the unique identifier of this order.
     *
     * @return the order ID
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the current state of this order.
     *
     * @return the order state
     */
    public OrderState getState() {
        return state;
    }

    /**
     * Returns the list of pizzas in this order.
     *
     * @return the list of pizzas
     */
    public LinkedList<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * Changes the current state of the order and logs it.
     *
     * @param state the new state of the order
     */
    public synchronized void changeState(OrderState state) {
        this.state = state;
        System.out.println("[" + this.id + "] [" + this.state + "]");
    }
}
