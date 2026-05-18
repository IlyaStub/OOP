package ru.nsu.gstubarev.snake.model.interfaces;

import ru.nsu.gstubarev.snake.model.Board;

/**
 * Interface for food generation.
 */
public interface FoodGenerator {
    /**
     * Food generate method.
     *
     * @param board for food don't spawn in walls
     * @param snake for food don't spawn in the snake
     * @return new food
     */
    Food generate(Board board, Snake snake);
}
