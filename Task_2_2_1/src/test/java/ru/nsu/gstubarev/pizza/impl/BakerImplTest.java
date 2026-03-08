package ru.nsu.gstubarev.pizza.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.pizza.enums.OrderState;
import ru.nsu.gstubarev.pizza.enums.PizzaType;
import ru.nsu.gstubarev.pizza.intefaces.Istorage;
import ru.nsu.gstubarev.pizza.records.Order;
import ru.nsu.gstubarev.pizza.records.Pizza;

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

    @Test
    void testRunInterruptedException() throws InterruptedException {
        Istorage mockStorage = mock(Istorage.class);
        BlockingQueue<Order> queue = new LinkedBlockingQueue<>();
        BakerImpl baker = new BakerImpl(1, 1000, queue, mockStorage);

        Thread thread = new Thread(baker);
        thread.start();

        Thread.sleep(50);

        thread.interrupt();

        thread.join(1000);

        assertFalse(thread.isAlive());
    }

    @Test
    void testTakeOrderInterruptedException() throws InterruptedException {
        Istorage mockStorage = mock(Istorage.class);
        BlockingQueue<Order> queue = new LinkedBlockingQueue<>();

        BakerImpl baker = new BakerImpl(1, 5000, queue, mockStorage);

        LinkedList<Pizza> pizzas = new LinkedList<>();
        pizzas.add(new Pizza(PizzaType.MARGHERITA, 30, 400));
        Order order = new Order(pizzas);

        Thread thread = new Thread(() -> baker.takeOrder(order));
        thread.start();

        Thread.sleep(100);

        thread.interrupt();
        thread.join(1000);

        verify(mockStorage, never()).addOrder(any());
    }
}