package ru.nsu.gstubarev.snake.controller;

import java.util.Objects;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import ru.nsu.gstubarev.snake.model.GameEngine;
import ru.nsu.gstubarev.snake.model.Point;

/**
 * Renders the game board, including the background, grid pattern, and wall obstacles.
 */
public class BoardRenderer implements EntityRenderer {
    private final Image wallImg;
    private final Color darkTileOverlay = Color.rgb(0, 0, 0, 0.15);

    /**
     * Initializes the board renderer and loads wall image assets.
     */
    public BoardRenderer() {
        wallImg = new Image(Objects.requireNonNull(getClass()
                .getResourceAsStream("/images/wall.png")));
    }

    @Override
    public void render(GraphicsContext gc, GameEngine engine, double offsetX,
                       double offsetY, double tileSize) {

        gc.setFill(Color.web("#121212"));
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        double boardWidthPx = tileSize * engine.getBoard().getWidth();
        double boardHeightPx = tileSize * engine.getBoard().getHeight();
        gc.setFill(Color.web("#1fb714"));
        gc.fillRect(offsetX, offsetY, boardWidthPx, boardHeightPx);

        int columns = engine.getBoard().getWidth();
        int rows = engine.getBoard().getHeight();
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                double px = offsetX + x * tileSize;
                double py = offsetY + y * tileSize;

                if ((x + y) % 2 != 0) {
                    gc.setFill(darkTileOverlay);
                    gc.fillRect(px, py, tileSize, tileSize);
                }

                if (engine.getBoard().isWall(new Point(x, y))) {
                    gc.drawImage(wallImg, px, py, tileSize, tileSize);
                }
            }
        }

        gc.setStroke(Color.web("#333333"));
        gc.setLineWidth(1);
        gc.strokeRect(offsetX, offsetY, columns * tileSize, rows * tileSize);
    }
}