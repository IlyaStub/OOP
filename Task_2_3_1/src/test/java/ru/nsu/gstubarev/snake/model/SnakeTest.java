package ru.nsu.gstubarev.snake.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.snake.model.enums.Direction;

public class SnakeTest {
    @Test
    public void testSnakeInitialization() {
        Point start = new Point(5, 5);
        Snake snake = new Snake(start, Direction.RIGHT, 2);

        assertEquals(1, snake.getLength());
        assertEquals(start, snake.getHead());
        assertEquals(Direction.RIGHT, snake.getCurrentDirection());
        assertEquals(2, snake.getSpeed());
        assertEquals(1, snake.getBody().size());
    }

    @Test
    public void testSnakeSetters() {
        Snake snake = new Snake(new Point(0, 0), Direction.UP, 1);

        snake.setSpeed(5);
        assertEquals(5, snake.getSpeed());

        snake.setCurrentDirection(Direction.DOWN);
        assertEquals(Direction.DOWN, snake.getCurrentDirection());
    }
}