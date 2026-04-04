package ru.nsu.gstubarev.snake.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.snake.model.enums.Direction;
import ru.nsu.gstubarev.snake.model.foods.Apple;

public class GameEngineTest {

    @Test
    public void testInitialization() {
        Board board = new Board(10, 10);
        Snake snake = new Snake(new Point(5, 5), Direction.RIGHT, 1);
        GameEngine engine = new GameEngine(board, snake);

        assertFalse(engine.isGameOver());
        assertEquals(0, engine.getScore());
        assertEquals(snake, engine.getPlayerSnake());
        assertEquals(board, engine.getBoard());
        assertEquals(1, engine.getFoods().size());
    }

    @Test
    public void testMovementWithoutCollision() {
        Board board = new Board(10, 10);
        Snake snake = new Snake(new Point(5, 5), Direction.RIGHT, 1);
        GameEngine engine = new GameEngine(board, snake);

        engine.update();
        assertEquals(new Point(6, 5), snake.getHead());

        snake.setCurrentDirection(Direction.DOWN);
        engine.update();
        assertEquals(new Point(6, 6), snake.getHead());

        snake.setCurrentDirection(Direction.LEFT);
        engine.update();
        assertEquals(new Point(5, 6), snake.getHead());

        snake.setCurrentDirection(Direction.UP);
        engine.update();
        assertEquals(new Point(5, 5), snake.getHead());
    }

    @Test
    public void testCollisionWithWall() {
        Board board = new Board(10, 10);
        board.addWall(new Point(6, 5));
        Snake snake = new Snake(new Point(5, 5), Direction.RIGHT, 1);
        GameEngine engine = new GameEngine(board, snake);

        engine.update();
        assertTrue(engine.isGameOver());
    }

    @Test
    public void testCollisionOutOfBounds() {
        Board board = new Board(10, 10);
        Snake snake = new Snake(new Point(9, 5), Direction.RIGHT, 1);
        GameEngine engine = new GameEngine(board, snake);

        engine.update();
        assertTrue(engine.isGameOver());
    }

    @Test
    public void testCollisionWithSelf() {
        Board board = new Board(10, 10);
        Snake snake = new Snake(new Point(5, 5), Direction.RIGHT, 1);
        GameEngine engine = new GameEngine(board, snake);

        engine.setShouldGrow(true);
        engine.update();
        engine.setShouldGrow(true);

        snake.setCurrentDirection(Direction.DOWN);
        engine.update();
        engine.setShouldGrow(true);

        snake.setCurrentDirection(Direction.LEFT);
        engine.update();

        snake.setCurrentDirection(Direction.UP);
        engine.update();

        assertTrue(engine.isGameOver());
    }

    @Test
    public void testEatingFood() {
        Board board = new Board(10, 10);
        Snake snake = new Snake(new Point(5, 5), Direction.RIGHT, 1);
        GameEngine engine = new GameEngine(board, snake);

        engine.getFoods().clear();
        engine.getFoods().add(new Apple(new Point(6, 5)));

        engine.update();

        assertEquals(2, snake.getLength());
        assertEquals(1, engine.getScore());
        assertEquals(1, engine.getFoods().size());
    }

    @Test
    public void testUpdateWhenGameOver() {
        Board board = new Board(10, 10);
        Snake snake = new Snake(new Point(9, 5), Direction.RIGHT, 1);
        GameEngine engine = new GameEngine(board, snake);

        engine.update();
        assertTrue(engine.isGameOver());

        Point headAfterGameOver = snake.getHead();
        engine.update();

        assertEquals(headAfterGameOver, snake.getHead());
    }
}