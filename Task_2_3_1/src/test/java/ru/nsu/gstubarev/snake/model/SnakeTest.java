package ru.nsu.gstubarev.snake.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.snake.model.enums.Direction;
import ru.nsu.gstubarev.snake.model.enums.Point;

/**
 * Test.
 */
public class SnakeTest {
    @Test
    public void testSnakeInitialization() {
        Point start = new Point(5, 5);
        SnakeClassic snake = new SnakeClassic(start, Direction.RIGHT, 2);

        assertEquals(1, snake.getLength());
        assertEquals(start, snake.getHead());
        assertEquals(Direction.RIGHT, snake.getCurrentDirection());
        assertEquals(2, snake.getSpeed());
        assertEquals(1, snake.getBody().size());
    }

    @Test
    public void testSnakeSetters() {
        SnakeClassic snake = new SnakeClassic(new Point(0, 0), Direction.UP, 1);

        snake.setSpeed(5);
        assertEquals(5, snake.getSpeed());

        snake.setCurrentDirection(Direction.DOWN);
        assertEquals(Direction.DOWN, snake.getCurrentDirection());
    }

    @Test
    public void testGetNextHead()
    {
        SnakeClassic snakeRight = new SnakeClassic(new Point(5, 5), Direction.RIGHT, 1);
        assertEquals(new Point(6, 5), snakeRight.getNextHead());

        SnakeClassic snakeLeft = new SnakeClassic(new Point(5, 5), Direction.LEFT, 1);
        assertEquals(new Point(4, 5), snakeLeft.getNextHead());

        SnakeClassic snakeUp = new SnakeClassic(new Point(5, 5), Direction.UP, 1);
        assertEquals(new Point(5, 4), snakeUp.getNextHead());

        SnakeClassic snakeDown = new SnakeClassic(new Point(5, 5), Direction.DOWN, 1);
        assertEquals(new Point(5, 6), snakeDown.getNextHead());
    }

    @Test
    public void testMoveWithGrowth()
    {
        SnakeClassic snake = new SnakeClassic(new Point(5, 5), Direction.RIGHT, 1);
        snake.move(new Point(6, 5), true);

        assertEquals(2, snake.getLength());
        assertEquals(new Point(6, 5), snake.getHead());
        assertEquals(new Point(5, 5), snake.getBody().get(1));
    }

    @Test
    public void testMoveWithoutGrowth()
    {
        SnakeClassic snake = new SnakeClassic(new Point(5, 5), Direction.RIGHT, 1);
        snake.move(new Point(6, 5), true);

        snake.move(new Point(7, 5), false);

        assertEquals(2, snake.getLength());
        assertEquals(new Point(7, 5), snake.getHead());
        assertEquals(new Point(6, 5), snake.getBody().get(1));
        assertNotEquals(new Point(5, 5), snake.getBody().get(1));
    }
}
