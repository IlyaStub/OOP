package ru.nsu.gstubarev.pizza.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.pizza.enums.PizzaType;
import ru.nsu.gstubarev.pizza.records.Order;
import ru.nsu.gstubarev.pizza.records.Pizza;

class StorageImplTest {
    private StorageImpl storage;

    @BeforeEach
    void setUp() {
        storage = new StorageImpl(5);
    }

    @Test
    void testAddAndTakeFullOrder() throws InterruptedException {
        assertTrue(storage.isEmpty());

        LinkedList<Pizza> pizzas = new LinkedList<>();
        pizzas.add(new Pizza(PizzaType.MARGHERITA, 30, 400));
        Order order = new Order(pizzas);

        storage.addOrder(order);
        assertFalse(storage.isEmpty());

        Order takenOrder = storage.takeOrder(2);
        assertNotNull(takenOrder);
        assertEquals(order.getId(), takenOrder.getId());
        assertTrue(storage.isEmpty());
    }

    @Test
    void testTakePartialOrder() throws InterruptedException {
        LinkedList<Pizza> pizzas = new LinkedList<>();
        pizzas.add(new Pizza(PizzaType.BBQ, 30, 400));
        pizzas.add(new Pizza(PizzaType.BBQ, 30, 400));
        pizzas.add(new Pizza(PizzaType.BBQ, 30, 400));
        Order order = new Order(pizzas);

        storage.addOrder(order);

        Order takenOrder = storage.takeOrder(2);

        assertNotNull(takenOrder);
        assertEquals(2, takenOrder.getPizzas().size());
        assertFalse(storage.isEmpty());
    }
}