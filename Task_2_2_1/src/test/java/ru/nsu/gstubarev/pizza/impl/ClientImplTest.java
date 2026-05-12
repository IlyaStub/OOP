package ru.nsu.gstubarev.pizza.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.pizza.records.Order;

class ClientImplTest {
    @Test
    void testCreateOrder() {
        BlockingQueue<Order> queue = new LinkedBlockingQueue<>();
        ClientImpl client = new ClientImpl(queue);
        Order order = client.createOrder();
        assertNotNull(order);
        int pizzasCount = order.getPizzas().size();
        assertTrue(pizzasCount >= 1 && pizzasCount <= 3);
    }

    @Test
    void testRunAndStop() throws InterruptedException {
        BlockingQueue<Order> queue = new LinkedBlockingQueue<>();
        ClientImpl client = new ClientImpl(queue);

        Thread thread = new Thread(client);
        thread.start();

        Thread.sleep(100);
        client.stopOrdering();

        thread.interrupt();

        thread.join(1000);
        assertFalse(thread.isAlive());
    }
}