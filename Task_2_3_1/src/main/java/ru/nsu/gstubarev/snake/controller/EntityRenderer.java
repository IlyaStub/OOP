package ru.nsu.gstubarev.snake.controller;

import javafx.scene.canvas.GraphicsContext;
import ru.nsu.gstubarev.snake.model.GameEngine;

public interface EntityRenderer {
    void render(GraphicsContext gc, GameEngine engine, double offsetX,
                double offsetY, double tileSize);
}
