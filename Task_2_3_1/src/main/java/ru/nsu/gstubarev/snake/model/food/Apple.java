package ru.nsu.gstubarev.snake.model.food;

import ru.nsu.gstubarev.snake.model.GameEngine;
import ru.nsu.gstubarev.snake.model.Point;
import ru.nsu.gstubarev.snake.model.interfaces.Food;

public class Apple implements Food {

    private final Point position;

    public Apple(Point position) {
        this.position = position;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void consume(GameEngine engine) {
        engine.addScore(10);
        engine.setShouldGrow(true);
    }
}
