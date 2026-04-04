package ru.nsu.gstubarev.snake.controller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import java.util.Objects;
import ru.nsu.gstubarev.snake.model.GameEngine;
import ru.nsu.gstubarev.snake.model.Point;
import ru.nsu.gstubarev.snake.model.Snake;
import ru.nsu.gstubarev.snake.model.enums.Direction;

/**
 * Renders the player's snake entity, including head rotation and body segments.
 */
public class SnakeRenderer implements EntityRenderer {
    private final Image headImg;
    private final Image bodyImg;

    /**
     * Initializes the snake renderer and loads the head and body images.
     */
    public SnakeRenderer() {
        headImg = new Image(Objects.requireNonNull(getClass()
                .getResourceAsStream("/images/head.png")));
        bodyImg = new Image(Objects.requireNonNull(getClass()
                .getResourceAsStream("/images/body.png")));
    }

    @Override
    public void render(GraphicsContext gc, GameEngine engine, double offsetX,
                       double offsetY, double tileSize) {
        Snake snake = engine.getPlayerSnake();
        for (int i = 0; i < snake.getBody().size(); i++) {
            Point p = snake.getBody().get(i);
            double x = offsetX + p.x() * tileSize;
            double y = offsetY + p.y() * tileSize;

            if (i == 0) {
                drawRotatedHead(gc, headImg, x, y, tileSize, snake.getCurrentDirection());
            } else {
                gc.drawImage(bodyImg, x, y, tileSize, tileSize);
            }
        }
    }

    private void drawRotatedHead(GraphicsContext gc, Image image, double x, double y,
                                 double size, Direction dir) {
        gc.save();
        double angle = switch (dir) {
            case UP -> -90;
            case DOWN -> 90;
            case LEFT -> 180;
            case RIGHT -> 0;
        };
        double pivotX = x + size / 2;
        double pivotY = y + size / 2;
        Rotate r = new Rotate(angle, pivotX, pivotY);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
        gc.drawImage(image, x, y, size, size);
        gc.restore();
    }
}