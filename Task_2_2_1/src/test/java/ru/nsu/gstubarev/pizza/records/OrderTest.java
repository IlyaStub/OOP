package ru.nsu.gstubarev.pizza.records;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.pizza.enums.OrderState;
import ru.nsu.gstubarev.pizza.enums.PizzaType;

class OrderTest {
    @Test
    void testOrderCreationAndStateChange() {
        LinkedList<Pizza> pizzas = new LinkedList<>();
        pizzas.add(new Pizza(PizzaType.PEPPERONI, 30, 67));

        Order order = new Order(pizzas);

        assertEquals(OrderState.CREATED, order.getState());
        assertEquals(1, order.getPizzas().size());
        assertTrue(order.getId() > 0);

        order.changeState(OrderState.IN_PROGRESS);
        assertEquals(OrderState.IN_PROGRESS, order.getState());
    }
}