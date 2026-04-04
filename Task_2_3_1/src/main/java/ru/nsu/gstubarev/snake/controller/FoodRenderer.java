package ru.nsu.gstubarev.snake.controller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Objects;
import ru.nsu.gstubarev.snake.model.GameEngine;
import ru.nsu.gstubarev.snake.model.Point;
import ru.nsu.gstubarev.snake.model.foods.GoldApple;
import ru.nsu.gstubarev.snake.model.interfaces.Food;

/**
 * Renderer responsible for drawing food items (apples, golden apples) onto the canvas.
 */
public class FoodRenderer implements EntityRenderer {
    private final Image appleImg;
    private final Image goldAppleImg;

    /**
     * Constructor for FoodRenderer.
     */
    public FoodRenderer() {
        this.goldAppleImg = new Image(Objects.requireNonNull(getClass()
                .getResourceAsStream("/images/goldApple.png")));
        this.appleImg = new Image(Objects.requireNonNull(getClass()
                .getResourceAsStream("/images/apple.png")));
    }

    @Override
    public void render(GraphicsContext gc, GameEngine engine, double offsetX,
                       double offsetY, double tileSize) {
        for (Food food : engine.getFoods()) {
            Point p = food.getPosition();

            Image imgToDraw = (food instanceof GoldApple) ? goldAppleImg : appleImg;

            gc.drawImage(imgToDraw, offsetX + p.x() * tileSize,
                    offsetY + p.y() * tileSize, tileSize, tileSize);
        }
    }
}