package ru.nsu.gstubarev.snake.controller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import ru.nsu.gstubarev.snake.model.GameEngine;

/**
 * Renders UI overlays, such as the "Game Over" text message.
 */
public class OverlayRenderer implements EntityRenderer{
    @Override
    public void render(GraphicsContext gc, GameEngine engine, double offsetX,
                       double offsetY, double tileSize) {
        if (engine.isGameOver()) {
            double boardWidthPx = tileSize * engine.getBoard().getWidth();
            double boardHeightPx = tileSize * engine.getBoard().getHeight();

            gc.setFill(Color.RED);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 36));
            gc.fillText("GAME OVER!", offsetX + boardWidthPx / 2 - 110,
                    offsetY + boardHeightPx / 2);
        }
    }
}
