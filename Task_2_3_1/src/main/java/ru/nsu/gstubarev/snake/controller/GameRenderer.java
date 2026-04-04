package ru.nsu.gstubarev.snake.controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.List;
import ru.nsu.gstubarev.snake.model.GameEngine;

/**
 * Main rendering manager that delegates drawing tasks to specific entity renderers.
 */
public class GameRenderer {
    private final Canvas gameCanvas;
    private final List<EntityRenderer> renderers;

    /**
     * Constructs a GameRenderer attached to the given canvas.
     *
     * @param gameCanvas the JavaFX canvas to draw on
     */
    public GameRenderer(Canvas gameCanvas) {
        this.gameCanvas = gameCanvas;
        this.renderers = new ArrayList<>();

        renderers.add(new BoardRenderer());
        renderers.add(new FoodRenderer());
        renderers.add(new SnakeRenderer());
        renderers.add(new OverlayRenderer());
    }

    public void draw(GameEngine engine) {
        if (engine == null) return;

        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        double width = gameCanvas.getWidth();
        double height = gameCanvas.getHeight();

        double tileSizeX = width / engine.getBoard().getWidth();
        double tileSizeY = height / engine.getBoard().getHeight();
        double tileSize = Math.min(tileSizeX, tileSizeY);

        double boardWidthPx = tileSize * engine.getBoard().getWidth();
        double boardHeightPx = tileSize * engine.getBoard().getHeight();

        double offsetX = (width - boardWidthPx) / 2;
        double offsetY = (height - boardHeightPx) / 2;

        gc.clearRect(0, 0, width, height);

        for (EntityRenderer renderer : renderers) {
            renderer.render(gc, engine, offsetX, offsetY, tileSize);
        }
    }
}