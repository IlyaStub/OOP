package ru.nsu.gstubarev.snake.model.foods;

import ru.nsu.gstubarev.snake.model.GameEngine;
import ru.nsu.gstubarev.snake.model.enums.Point;
import ru.nsu.gstubarev.snake.model.interfaces.Food;

/**
 * Represents a standard apple that increases score by 1 and grows the snake.
 */
public class Apple implements Food {
    private final Point position;

    /**
     * Constructs an apple at the given position.
     *
     * @param position the point where the apple is located
     */
    public Apple(Point position) {
        this.position = position;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void consume(GameEngine engine) {
        engine.addScore(1);
        engine.setShouldGrow(true);
    }
}
