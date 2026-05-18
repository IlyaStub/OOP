package ru.nsu.gstubarev.snake.model.foods;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.snake.model.Board;
import ru.nsu.gstubarev.snake.model.FoodGeneratorClassic;
import ru.nsu.gstubarev.snake.model.GameEngine;
import ru.nsu.gstubarev.snake.model.SnakeClassic;
import ru.nsu.gstubarev.snake.model.enums.Direction;
import ru.nsu.gstubarev.snake.model.enums.Point;
import ru.nsu.gstubarev.snake.model.interfaces.FoodGenerator;
import ru.nsu.gstubarev.snake.model.interfaces.Snake;

/**
 * Test.
 */
public class FoodTest {
    @Test
    public void testApple() {
        Point position = new Point(2, 2);
        Apple apple = new Apple(position);

        assertEquals(position, apple.getPosition());

        Board board = new Board(10, 10);
        Snake snake = new SnakeClassic(new Point(0, 0), Direction.RIGHT, 1);
        FoodGenerator generator = new FoodGeneratorClassic();

        GameEngine engine = new GameEngine(board, snake, generator, 2);

        int initialScore = engine.getScore();
        apple.consume(engine);

        assertEquals(initialScore + 1, engine.getScore());
    }

    @Test
    public void testGoldApple() {
        Point position = new Point(3, 3);
        GoldApple goldApple = new GoldApple(position);

        assertEquals(position, goldApple.getPosition());

        Board board = new Board(10, 10);
        Snake snake = new SnakeClassic(new Point(0, 0), Direction.RIGHT, 1);
        FoodGenerator generator = new FoodGeneratorClassic();
        GameEngine engine = new GameEngine(board, snake, generator, 2);

        int initialScore = engine.getScore();
        goldApple.consume(engine);

        assertEquals(initialScore + 2, engine.getScore());
    }
}