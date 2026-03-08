package ru.nsu.gstubarev.pizza.impl;

import ru.nsu.gstubarev.pizza.intefaces.IgeneratorTime;
import java.util.Random;

public class GeneratorTime implements IgeneratorTime {
    private final int minTime;
    private final int maxTime;
    private final Random random = new Random();

    /**
     * This is constructor.
     *
     * @param minTime min time for delivery
     * @param maxTime max time for delivery
     */
    public GeneratorTime(int minTime, int maxTime) {
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    @Override
    public int generateDeliveryTime() {
        return random.nextInt((maxTime - minTime) + 1) + minTime;
    }
}