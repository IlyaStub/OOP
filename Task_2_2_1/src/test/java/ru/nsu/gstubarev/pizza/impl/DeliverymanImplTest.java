package ru.nsu.gstubarev.pizza.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.pizza.enums.OrderState;
import ru.nsu.gstubarev.pizza.enums.PizzaType;
import ru.nsu.gstubarev.pizza.intefaces.IgeneratorTime;
import ru.nsu.gstubarev.pizza.intefaces.Istorage;
import ru.nsu.gstubarev.pizza.records.Order;
import ru.nsu.gstubarev.pizza.records.Pizza;

class DeliverymanImplTest {
    @Test
    void testTakePizzas() throws InterruptedException {
        Istorage mockStorage = mock(Istorage.class);
        IgeneratorTime mockGen = mock(IgeneratorTime.class);

        DeliverymanImpl courier = new DeliverymanImpl(1, 3, mockGen, mockStorage);

        LinkedList<Pizza> pizzas = new LinkedList<>();
        pizzas.add(new Pizza(PizzaType.SEAFOOD, 30, 600));
        Order order = new Order(pizzas);

        when(mockStorage.takeOrder(3)).thenReturn(order);
        when(mockGen.generateDeliveryTime()).thenReturn(10);

        courier.takePizzas(mockStorage);

        assertEquals(OrderState.DELIVERED, order.getState());

        verify(mockStorage, times(1)).takeOrder(3);
        verify(mockGen, times(1)).generateDeliveryTime();
    }

    @Test
    void testRunAndStop() throws InterruptedException {
        Istorage mockStorage = mock(Istorage.class);
        when(mockStorage.isEmpty()).thenReturn(true);

        IgeneratorTime mockGen = mock(IgeneratorTime.class);

        DeliverymanImpl courier = new DeliverymanImpl(1, 3, mockGen, mockStorage);
        courier.stopWork();

        Thread thread = new Thread(courier);
        thread.start();
        thread.join(1000);

        assertFalse(thread.isAlive());
    }
}