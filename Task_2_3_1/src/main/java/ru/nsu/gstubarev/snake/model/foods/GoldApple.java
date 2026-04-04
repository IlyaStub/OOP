package ru.nsu.gstubarev.snake.model.foods;

import ru.nsu.gstubarev.snake.model.GameEngine;
import ru.nsu.gstubarev.snake.model.Point;
import ru.nsu.gstubarev.snake.model.interfaces.Food;

public class GoldApple implements Food {
    private final Point position;

    public GoldApple(Point position) {
        this.position = position;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void consume(GameEngine engine) {
        engine.addScore(2);
        engine.setShouldGrow(true);
    }
}