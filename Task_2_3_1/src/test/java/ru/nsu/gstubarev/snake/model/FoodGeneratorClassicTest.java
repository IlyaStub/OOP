package ru.nsu.gstubarev.snake.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.snake.model.enums.Direction;
import ru.nsu.gstubarev.snake.model.enums.Point;
import ru.nsu.gstubarev.snake.model.foods.Apple;
import ru.nsu.gstubarev.snake.model.foods.GoldApple;
import ru.nsu.gstubarev.snake.model.interfaces.Food;
import ru.nsu.gstubarev.snake.model.interfaces.FoodGenerator;
import ru.nsu.gstubarev.snake.model.interfaces.Snake;

public class FoodGeneratorClassicTest
{
    @Test
    public void testGenerateReturnsFood()
    {
        Board board = new Board(10, 10);
        Snake snake = new SnakeClassic(new Point(5, 5), Direction.RIGHT, 1);
        FoodGenerator generator = new FoodGeneratorClassic();

        Food food = generator.generate(board, snake);

        assertNotNull(food);
        assertTrue(food instanceof Apple || food instanceof GoldApple);
    }

    @Test
    public void testGenerateReturnsNullWhenNoEmptyPoints()
    {
        Board board = new Board(1, 1);
        Snake snake = new SnakeClassic(new Point(0, 0), Direction.RIGHT, 1);
        FoodGenerator generator = new FoodGeneratorClassic();

        Food food = generator.generate(board, snake);

        assertNull(food);
    }
}