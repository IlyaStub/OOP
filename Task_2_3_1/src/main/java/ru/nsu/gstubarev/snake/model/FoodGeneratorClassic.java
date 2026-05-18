package ru.nsu.gstubarev.snake.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ru.nsu.gstubarev.snake.model.enums.Point;
import ru.nsu.gstubarev.snake.model.foods.Apple;
import ru.nsu.gstubarev.snake.model.foods.GoldApple;
import ru.nsu.gstubarev.snake.model.interfaces.Food;
import ru.nsu.gstubarev.snake.model.interfaces.FoodGenerator;
import ru.nsu.gstubarev.snake.model.interfaces.Snake;

/**
 * Classic food generator implementation.
 */
public class FoodGeneratorClassic implements FoodGenerator {
    private final Random random = new Random();

    @Override
    public Food generate(Board board, Snake snake) {
        List<Point> emptyPoints = new ArrayList<>();

        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                Point p = new Point(x, y);
                if (!board.isWall(p) && !snake.getBody().contains(p)) {
                    emptyPoints.add(p);
                }
            }
        }

        if (emptyPoints.isEmpty()) {
            return null;
        }

        Point pointFood = emptyPoints.get(random.nextInt(emptyPoints.size()));

        if (random.nextDouble() < 0.2) {
            return new GoldApple(pointFood);
        } else {
            return new Apple(pointFood);
        }
    }
}
