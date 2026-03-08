package ru.nsu.gstubarev.pizza.records;

import ru.nsu.gstubarev.pizza.enums.PizzaType;

/**
 * Represents a pizza with its type, size, and cost.
 *
 * @param type  the type/flavor of the pizza
 * @param size  the size of the pizza in arbitrary units
 * @param cost  the price of the pizza in currency units
 */
public record Pizza(PizzaType type, int size, int cost) {
}
