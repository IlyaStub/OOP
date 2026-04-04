package ru.nsu.gstubarev.snake.controller;

import javafx.scene.canvas.GraphicsContext;
import ru.nsu.gstubarev.snake.model.GameEngine;

/**
 * Interface for renderer entity.
 */
public interface EntityRenderer {
    /**
     * The render method.
     *
     * @param gc the graphics context to draw on
     * @param engine the current game state
     * @param offsetX the horizontal drawing offset
     * @param offsetY the vertical drawing offset
     * @param tileSize the size of a single grid tile
     */
    void render(GraphicsContext gc, GameEngine engine, double offsetX,
                double offsetY, double tileSize);
}
