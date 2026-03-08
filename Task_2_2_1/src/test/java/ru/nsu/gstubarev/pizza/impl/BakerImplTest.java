package ru.nsu.gstubarev.pizza.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.pizza.enums.OrderState;
import ru.nsu.gstubarev.pizza.enums.PizzaType;
import ru.nsu.gstubarev.pizza.intefaces.Istorage;
import ru.nsu.gstubarev.pizza.records.Order;
import ru.nsu.gstubarev.pizza.records.Pizza;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class BakerImplTest {
    @Test
    void testTakeOrder() throws InterruptedException {
        Istorage mockStorage = mock(Istorage.class);
        BlockingQueue<Order> queue = new LinkedBlockingQueue<>();

        BakerImpl baker = new BakerImpl(1, 10, queue, mockStorage);

        LinkedList<Pizza> pizzas = new LinkedList<>();
        pizzas.add(new Pizza(PizzaType.HAWAIIAN, 30, 450));
        Order order = new Order(pizzas);

        baker.takeOrder(order);

        assertEquals(OrderState.IN_PROGRESS, order.getState());

        verify(mockStorage, times(1)).addOrder(order);
    }

    @Test
    void testRunAndStop() throws InterruptedException {
        Istorage mockStorage = mock(Istorage.class);
        BlockingQueue<Order> queue = new LinkedBlockingQueue<>();

        BakerImpl baker = new BakerImpl(1, 10, queue, mockStorage);
        baker.stopWork();

        Thread thread = new Thread(baker);
        thread.start();
        thread.join(1000);

        assertFalse(thread.isAlive());
    }
}