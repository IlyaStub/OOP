package ru.nsu.gstubarev.snake.model.interfaces;

import ru.nsu.gstubarev.snake.model.GameEngine;
import ru.nsu.gstubarev.snake.model.Point;

public interface Food {
    Point getPosition();
    void consume(GameEngine engine);
}
