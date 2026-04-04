package ru.nsu.gstubarev.snake.model.interfaces;

import ru.nsu.gstubarev.snake.model.GameEngine;
import ru.nsu.gstubarev.snake.model.Point;

/**
 * Just food.
 */
public interface Food {
    /**
     * Gets the position of the food.
     *
     * @return the position point
     */
    Point getPosition();

    /**
     * Applies the consumption effect to the game state.
     *
     * @param engine the game engine to apply effects to
     */
    void consume(GameEngine engine);
}
